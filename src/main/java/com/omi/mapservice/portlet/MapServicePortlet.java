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
//import com.omi.mapservice.api.*;
//import com.omi.mapservice.tacbrd.*;

/**
 * Portlet implementation class MapServicePortlet
 */
public class MapServicePortlet extends MVCPortlet {
	// TODO: we will want to inject this with Spring

	private static MapServiceImpl mapService = new MapServiceImpl();
//
//	public static MapService getMapService() {
//		return mapService;
//	}

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

		if (page == null || page.equals("/mapRestTest.jsp")) {
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

//	public void addLayer(ActionRequest request, ActionResponse response)
//			throws IOException, SystemException {
//		UploadPortletRequest uploadRequest = PortalUtil
//				.getUploadPortletRequest(request);
//		String type = ParamUtil.getString(uploadRequest, "typeL", "ERROR");
//		User user = (User) request.getAttribute(WebKeys.USER);
//		String userId = String.valueOf(user.getUserId());
//		// ThemeDisplay themeDisplay = (ThemeDisplay)
//		// request.getAttribute(WebKeys.THEME_DISPLAY);
//		// String portletGroupId =
//		// String.valueOf(themeDisplay.getScopeGroupId());
//		String portletGroupId = "0";
//
//		// Handle KML
//		if (type.equals("KML")) {
//			String name = ParamUtil.getString(uploadRequest, "kmlName", "");
//			System.out.println("addLayer KML name " + name + " User: " + userId
//					+ " Community: " + portletGroupId);
//			File file = uploadRequest.getFile("fileName");
//			String kmlBody = readFile(file);
//			sendKmlLayerToServer(name, kmlBody, userId, portletGroupId);
//		} else if (type.equals("WMS")) {
//			String name = ParamUtil.getString(uploadRequest, "wmsName", "");
//			String wmsUrl = ParamUtil.getString(uploadRequest, "wmsUrl", "");
//			String wmsString = ParamUtil.getString(uploadRequest, "wmsString",
//					"");
//			sendWmsLayerToServer(name, wmsUrl, wmsString, userId,
//					portletGroupId);
//		} else if (type.equals("GML")) {
//			String name = ParamUtil.getString(uploadRequest, "gmlName", "");
//			System.out.println("addLayer GML name " + name);
//			File file = uploadRequest.getFile("gmlfileName");
//			String gmlBody = readFile(file);
//			sendGmlLayerToServer(name, gmlBody, userId, portletGroupId);
//		} else if (type.equals("GEORSS")) {
//			String name = ParamUtil.getString(uploadRequest, "georssName", "");
//			String georssUrl = ParamUtil.getString(uploadRequest, "georssUrl",
//					"");
//			sendGeorssLayerToServer(name, georssUrl, userId, portletGroupId);
//		}
//
//		// Return to main jsp page
//		setNextPageToHome(request, response);
//	}
//
//	public void deleteLayer(ActionRequest request, ActionResponse response)
//			throws IOException {
//		String id = ParamUtil.getString(request, "deleteId", "-1");
//		String type = ParamUtil.getString(request, "deleteType", "KML");
//		mapService.deleteLayer(id, type);
//
//		// Return to main jsp page
//		setNextPageToHome(request, response);
//	}
//
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
//
//	public void shareLayer(ActionRequest request, ActionResponse response)
//			throws IOException, SystemException {
//		String id = ParamUtil.getString(request, "shareId", "");
//		String format = ParamUtil.getString(request, "format", "");
//		ThemeDisplay themeDisplay = (ThemeDisplay) request
//				.getAttribute(WebKeys.THEME_DISPLAY);
//		String communityId = String.valueOf(themeDisplay.getScopeGroupId());
//		changeLayerGroupId(id, communityId, format);
//		setNextPageToHome(request, response);
//	}
//
//	public void unShareLayer(ActionRequest request, ActionResponse response)
//			throws IOException, SystemException {
//		String id = ParamUtil.getString(request, "unShareId", "");
//		String format = ParamUtil.getString(request, "unShareFormat", "");
//		String communityId = "0";
//		changeLayerGroupId(id, communityId, format);
//		setNextPageToHome(request, response);
//	}
//
//	/**
//	 * Update protlet preferences.
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	public void edit(ActionRequest request, ActionResponse response) {
//		try {
//			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
//			PortletPreferences prefs = request.getPreferences();
//			String url = uploadRequest.getParameter("url");
//			if (url != null) {
//				prefs.setValue("url", url);
//			}
//            String OLurl = uploadRequest.getParameter("olurl");
//			if (OLurl != null) {
//				prefs.setValue("olurl", OLurl);
//			}
//            String useTileCache = uploadRequest.getParameter("useTileCache");
//			if (useTileCache != null) {
//				prefs.setValue("useTileCache", useTileCache);
//			} else {
//				prefs.setValue("useTileCache", "");
//			}
//			String table = uploadRequest.getParameter("table");
//			if (table != null) {
//				prefs.setValue("table", table);
//			}
//			String limit = uploadRequest.getParameter("limit");
//			if (limit != null) {
//				prefs.setValue("limit", limit);
//			}
//			prefs.store();
//			// handle base layers file
//			File file = uploadRequest.getFile("fileName");
//			MapLayerLoader.loadFile(file, url);	// Fails quietly if file not found.  Is that cool or should we change it?
//			// return
//			response.setPortletMode(PortletMode.VIEW);
//		} catch (PortletModeException ex) {
//			Logger.getLogger(MapServicePortlet.class.getName()).log(
//					Level.SEVERE, null, ex);
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} catch (ValidatorException ex) {
//			ex.printStackTrace();
//		} catch (ReadOnlyException ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	/**
//	 * This is the passthrough that servers kml and gml map data. The browser
//	 * does not hit the IM/IT server directly, but instead goes through here.
//	 * This checks to make sure a user is logged in so that our layers are not
//	 * public.
//	 */
//	public void serveResource(ResourceRequest arequest,
//			ResourceResponse response) {
//		HttpServletRequest request = PortalUtil.getHttpServletRequest(arequest);
//
//		try {
//			// verify that the user is logged in
//			ThemeDisplay themeDisplay = (ThemeDisplay) request
//					.getAttribute(WebKeys.THEME_DISPLAY);
//			User user = (User) arequest.getAttribute(WebKeys.USER);
//			user.getScreenName();
//			String userID = String.valueOf(user.getUserId());
//			// get map data
//			String id = request.getParameter("layerId");
//			String type = request.getParameter("layerType");
//			String userId = request.getParameter("userId");
//			String resourceMode = request.getParameter("mode");
//			String layerBody = "";
//			if (resourceMode!=null && resourceMode.equals("TREE_JSON")) {
//				com.omi.mapservice.api.MapService mapService = com.omi.mapservice.portlet.MapServicePortlet
//						.getMapService();
//				layerBody = mapService.getMapsAsJson();
//				response.setContentType("application/json");
//			} else if (type.equals("KML")) {
//				com.omi.mapservice.api.MapService mapService = com.omi.mapservice.portlet.MapServicePortlet
//						.getMapService();
//                                String prepend = "http://" + request.getServerName() + ":" +request.getServerPort() + "/tacbrd-symbols/icons/";
//                                layerBody = fixKML(mapService.getKmlLayerBodyById(id), prepend);
//				response.setContentType("application/vnd.google-earth.kml+xml");
//			} else if (type.equals("GML")) {
//				com.omi.mapservice.api.MapService mapService = com.omi.mapservice.portlet.MapServicePortlet
//						.getMapService();
//				layerBody = mapService.getGmlLayerBodyById(id);
//				response.setContentType("application/vnd.google-earth.gml+xml");
//			} else if (type.equals("WKT")) {
//				com.omi.mapservice.api.MapService mapService = com.omi.mapservice.portlet.MapServicePortlet
//						.getMapService();
//				layerBody = mapService.getWktLayerBodyById(id);
//				//response.setContentType("application/vnd.google-earth.gml+xml");
//			} 
//			response.getPortletOutputStream().write(
//					layerBody.getBytes(Charset.forName("UTF-8")));
//		} catch (IOException ioe) {
//			// some kind of issue with the REST stream
//		} catch (Exception e) {
//			// e.printStackTrace();
//			// authentication issue
//		}
//	}
//
	private void sendKmlLayerToServer(String name, String kmlBody, String user,
			String portletGroupId) {
		mapService.addKmlLayer(name, kmlBody, user, portletGroupId);
	}
//
//	private void sendGmlLayerToServer(String name, String kmlBody, String user,
//			String portletGroupId) {
//		mapService.addGmlLayer(name, kmlBody, user, portletGroupId);
//	}
//
//	private void sendWmsLayerToServer(String name, String url, String layers,
//			String user, String portletGroupId) {
//		mapService.addWmsLayer(name, url, layers, user, portletGroupId);
//	}
//
//	private void sendGeorssLayerToServer(String name, String url, String user,
//			String portletGroupId) {
//		mapService.addGeorssLayer(name, url, user, portletGroupId);
//	}
//
//	private void changeLayerGroupId(String Id, String communityId, String format) {
//		mapService.shareLayerById(Id, communityId, format);
//	}
//
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
//
//	private static String readFile(File file) throws IOException {
//		FileInputStream stream = new FileInputStream(file);
//		try {
//			FileChannel fc = stream.getChannel();
//			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
//					fc.size());
//			/* Instead of using default, pass in a decoder. */
//			return Charset.defaultCharset().decode(bb).toString();
//		} finally {
//			stream.close();
//		}
//	}
//        private String fixKML(String kml, String prepend){
//            int hrefLoc = kml.indexOf("<href>");
//            if(hrefLoc > 0 && !(kml.substring(hrefLoc + 6, hrefLoc+10).equals("http"))){
//                return kml.substring(0, hrefLoc + 6) + prepend + kml.substring(hrefLoc + 6);
//            }
//            else {
//                return kml;
//            }
//    }
}
