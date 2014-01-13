package com.omi.mapservice.portlet;

import com.liferay.portal.kernel.exception.SystemException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class MapServicePortlet
 */
public class MapServicePortlet extends MVCPortlet {
	// TODO: we will want to inject this with Spring

	private static MapServiceImpl mapService = new MapServiceImpl();

	@Override
	public void render(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		String page = request.getParameter("jspPage");
		// override the base url if available in the portlet config (from
		// edit.jsp)
		PortletPreferences prefs = request.getPreferences();
		String mapServiceUrl = prefs.getValue("url", mapService.getBaseUrl());
		// add trailing slash
		if (!mapServiceUrl.endsWith("/"))
			mapServiceUrl += "/";
		//mapService.setBaseUrl(mapServiceUrl);

		if (page == null || page.equals("/view.jsp")) {
			//request.setAttribute("maps", mapService.getMaps());
			//request.setAttribute("mapsJSON", mapService.getMapsAsJson());
		}

		// Pass the resource URL as a bean called resourceUrl to the view JSP
		ResourceURL layerUrl = response.createResourceURL();
		layerUrl.setResourceID("layer-data");
		String resourceUrl = layerUrl.toString();
		request.setAttribute("resourceUrl", resourceUrl);

		super.render(request, response);
	}

	/**
	 * Store drawing layer as KML.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void saveDrawing(ActionRequest request, ActionResponse response)
			throws IOException, SystemException {
		// process form fields
		String name = ParamUtil.getString(request, "drawingName", "");
		String kmlBody = ParamUtil.getString(request, "drawingKML", "");
		User user = (User) request.getAttribute(WebKeys.USER);
		String userId = String.valueOf(user.getUserId());
		// ThemeDisplay themeDisplay = (ThemeDisplay)
		// request.getAttribute(WebKeys.THEME_DISPLAY);
		// String portletGroupId =
		// String.valueOf(themeDisplay.getScopeGroupId());
		String portletGroupId = "0";
		System.out.println("addLayer KML name " + name + " User: " + userId
				+ " Community: " + portletGroupId);
		sendKmlLayerToServer(name, kmlBody, userId, portletGroupId);
		// redirect
		setNextPageToHome(request, response);
	}

	private void sendKmlLayerToServer(String name, String kmlBody, String user,
			String portletGroupId) {
		mapService.addKmlLayer(name, kmlBody, user, portletGroupId);
	}

	private void setNextPageToHome(ActionRequest request,
			ActionResponse response) throws IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
				.getAttribute(WebKeys.THEME_DISPLAY);
		String portletName = (String) request.getAttribute(WebKeys.PORTLET_ID);
		PortletURL redirectURL = PortletURLFactoryUtil
				.create(PortalUtil.getHttpServletRequest(request), portletName,
						themeDisplay.getLayout().getPlid(),
						PortletRequest.RENDER_PHASE);
		redirectURL.setParameter("jspPage", "/mapRestTest.jsp");
		response.sendRedirect(redirectURL.toString());
	}

}
