package com.omi.mapservice.portlet;

//import com.omi.mapservice.api.CompositeLayer;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.client.utils.URIBuilder;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.ObjectWriter;
//import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
//
import mil.jpeocbd.enterprise.osgi.core.xml.jaxb.map.MapMarshallingServiceImpl;
//import mil.jpeocbd.enterprise.osgi.map.GmlResourceLayer;
import mil.jpeocbd.enterprise.osgi.map.KmlDocumentLayer;
//import mil.jpeocbd.enterprise.osgi.map.LineStyle;
//import mil.jpeocbd.enterprise.osgi.map.PolygonStyle;
//import mil.jpeocbd.enterprise.osgi.map.WmsResourceLayer;
import mil.jpeocbd.enterprise.osgi.xml.XmlDocument;
//import mil.jpeocbd.enterprise.osgi.map.GmlDocumentLayer;
//import mil.jpeocbd.enterprise.osgi.xml.jaxb.atom.Link;
//import mil.jpeocbd.enterprise.osgi.xml.jaxb.geometry.FeaturedGeometryListType;
//import mil.jpeocbd.enterprise.osgi.xml.jaxb.geometry.FeaturedGeometryType;
import mil.jpeocbd.enterprise.osgi.xml.jaxb.map.AbstractLayerType;
//import mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GeometricLayer;
import mil.jpeocbd.enterprise.osgi.xml.jaxb.map.MapMarshallingService;
import mil.jpeocbd.enterprise.osgi.xml.jaxb.map.ReferenceList;
//
//import com.omi.mapservice.api.GeorssLayer;
//import com.omi.mapservice.api.LayerFeature;
//import com.omi.mapservice.api.Map;
//import com.omi.mapservice.api.MapImpl;
//import com.omi.mapservice.api.MapLayer;
//import com.omi.mapservice.api.MapService;
//import com.omi.mapservice.api.MapServiceException;
//import com.omi.mapservice.api.VectorLayer;
//import com.omi.mapservice.api.WKTLayer;
//import com.omi.mapservice.api.WmsLayer;
//import com.vividsolutions.jts.util.Assert;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import mil.jpeocbd.enterprise.osgi.map.DefaultMap;
//import mil.jpeocbd.enterprise.osgi.map.FeaturedGeometry;
import mil.jpeocbd.enterprise.osgi.map.Layer;
//import org.springframework.http.ResponseEntity;

/**
 * Implementation of the MapService interface. This is a wrapper around the
 * IM/IT mapservice REST calls.
 * 
 * @author gdonarum
 */
public class MapServiceImpl {// implements MapService {

	// default location of IM/IT base service
	private String baseUrl = "http://localhost/enterprise/map/rest/";
	// list of ref calls
	public static final String REF_KML_DOCUMENTS = "layers-kml-document";
	public static final String REF_GML_RESOURCES = "layers-gml-resource";
	public static final String REF_GML_DOCUMENTS = "layers-gml-document";
	public static final String REF_GEOMETRIC = "layers-geometric";
	public static final String REF_COMPOSITE = "layers-composite";
	public static final String REF_MAPS = "maps";
	// properties
	public static final String PROPERTY_FORMAT = "LAYER-FORMAT";
	public static final String PROPERTY_USER_ID = "USER-ID";
	public static final String PROPERTY_COMMUNITY_ID = "COMMUNITY_ID";
	public static final String DEFAULT_MAP_NAME = "Rest Test Layers";

//	// IM/IT OSGi connection
	RestTemplate restTemplate = new RestTemplate();
	private static MapMarshallingService marshaller = new MapMarshallingServiceImpl();
//
//	public List<Map> getMaps() {
//		List<Map> maps = new ArrayList<Map>();
//		try {
//			ReferenceList list = restTemplate.getForObject(
//					getResource(REF_MAPS), ReferenceList.class);
//			for (Link link : list.getLinks()) {
//				DefaultMap def = getMap(link.getHref());
//				Map map = buildMap(def);
//				map.setMapLayers(getMapLayers(def));
//				maps.add(map);
//			}
//		} catch (MapServiceException ex) {
//		}
//		
//		return maps;
//	}
//	
//	public String getMapsAsJson() {		
//		//JSON test
//		try {
//			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//			String json = ow.writeValueAsString(getMaps());
//			return json.replace("\n", "").replace("\r", "");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "[]";
//	}
//
//	public List<MapLayer> getMapLayers(DefaultMap map) {
//		return getMapLayers(map.getLayers());
//	}
//	
//	public List<MapLayer> getMapLayers(List<Layer> layerList) {
//		List layers = new ArrayList();
//		for (Layer lyr : layerList) {
//			if (lyr.getClass().equals(KmlDocumentLayer.class)) {
//				layers.add(buildMapLayer((KmlDocumentLayer) lyr));
//			} else if (lyr.getClass().equals(GmlDocumentLayer.class)) {
//				layers.add(buildMapLayer((GmlDocumentLayer) lyr));
//			} else if (lyr.getClass().equals(GmlResourceLayer.class)) {
//				layers.add(buildMapLayer((GmlResourceLayer) lyr));
//			} else if (lyr.getClass().equals(WmsResourceLayer.class)) {
//				layers.add(buildMapLayer((WmsResourceLayer) lyr));
//			} else if (lyr instanceof mil.jpeocbd.enterprise.osgi.map.CompositeLayer) {
//				layers.add(buildMapLayer((mil.jpeocbd.enterprise.osgi.map.CompositeLayer) lyr));
//			} else if (lyr instanceof mil.jpeocbd.enterprise.osgi.map.GeometricLayer) {
//				layers.add(buildMapLayer((mil.jpeocbd.enterprise.osgi.map.GeometricLayer) lyr));
//			} else {
//				// unknown
//			}
//		}
//		return layers;
//		
//	}
//
//	@Override
	public void setBaseUrl(String url) {
		this.baseUrl = url;
	}

//	@Override
	public String getBaseUrl() {
		return this.baseUrl;
	}
//
//	@Override
//	public void deleteLayer(String id, String type) {
//		// TODO: support all layer types
//		String resource = REF_KML_DOCUMENTS;
//		if (type.equals(MapLayer.FORMAT_WMS)) {
//			resource = REF_GML_RESOURCES;
//		} else if (type.equals(MapLayer.FORMAT_GEORSS)) {
//			resource = REF_GML_RESOURCES;
//		} else if (type.equals(MapLayer.FORMAT_GML)) {
//			resource = REF_GML_DOCUMENTS;
//		}
//		try {
//			restTemplate.delete(getResource(resource) + "/" + id);
//		} catch (RestClientException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MapServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
	public void addLayerToMap(Layer layer, String mapName) {
		// get suggested id from name
		String mapId = "map-" + mapName.toLowerCase().replace(" ","-");	// TODO: make this more robust...special character removal etc.
		// look for the map
		String url = getBaseUrl() + REF_MAPS + "?names=" + mapName;
		ReferenceList list = restTemplate
				.getForObject(url, ReferenceList.class);
		if (!list.getLinks().isEmpty()) {
			mil.jpeocbd.enterprise.osgi.xml.jaxb.map.Map outMap = restTemplate
					.getForObject(list.getLinks().get(0).getHref(),
							mil.jpeocbd.enterprise.osgi.xml.jaxb.map.Map.class);
			DefaultMap map = (DefaultMap) marshaller.toDomainMap(outMap);
			map.addOrUpdateLayer(layer);
			updateMap(map);
		} else {
			// create new map
			DefaultMap map = new DefaultMap();
			map.setName(mapName);
			map.setId(mapId);
			map.addOrUpdateLayer(layer);
			addMap(map);
		}
	}

	public void updateMap(mil.jpeocbd.enterprise.osgi.map.Map map) {
		String url = getBaseUrl() + REF_MAPS + "/" + map.getId();
		restTemplate.put(url,
				(mil.jpeocbd.enterprise.osgi.xml.jaxb.map.Map) marshaller
						.toXmlMap(map));
	}

	public void addMap(mil.jpeocbd.enterprise.osgi.map.Map map) {
		mil.jpeocbd.enterprise.osgi.xml.jaxb.map.Map xmlMap = marshaller.toXmlMap(map);
		restTemplate.put(getBaseUrl() + REF_MAPS + "/" + map.getId(), xmlMap);
	}

//	@Override
	public void addKmlLayer(String name, String kmlBody, String user,
			String portletGroupId) {
		// create the layer
		KmlDocumentLayer domainLayer = new KmlDocumentLayer(name,
				new XmlDocument(kmlBody));
		// add geometry for GIS queries
		// Geometry geometry = ...
		// domainLayer.setGeometry(geometry);
		// convert the domain object
		domainLayer.setProperty(PROPERTY_USER_ID, user);
		domainLayer.setProperty(PROPERTY_COMMUNITY_ID, portletGroupId);
		AbstractLayerType layer = marshaller.toXmlLayer(domainLayer);

		// post to service
		try {

			// return
			// restTemplate.postForLocation(getResource(REF_KML_DOCUMENTS),
			// layer);
			addLayerToMap(domainLayer, DEFAULT_MAP_NAME);
		} catch (Exception e) {
		}
	}
//
//	@Override
//	public void addGmlLayer(String name, String gmlBody, String user,
//			String portletGroupId) {
//		// create the layer
//		GmlDocumentLayer gmlLayerIn = new GmlDocumentLayer(name,
//				new XmlDocument(gmlBody));
//		// convert the domain object
//		gmlLayerIn.setProperty(PROPERTY_USER_ID, user);
//		gmlLayerIn.setProperty(PROPERTY_COMMUNITY_ID, portletGroupId);
//		AbstractLayerType layer = marshaller.toXmlLayer(gmlLayerIn);
//
//		// post to service
//		try {
//			// return
//			// restTemplate.postForLocation(getResource(REF_GML_DOCUMENTS),
//			// layer);
//			addLayerToMap(gmlLayerIn, DEFAULT_MAP_NAME);
//		} catch (Exception e) {
//		}
//		// return null;
//	}
//
//	/**
//	 * Use the ref list to get the proper REST call.
//	 * 
//	 * @param ref
//	 * @return
//	 * @throws MapServiceException
//	 */
//	public String getResource(String ref) throws MapServiceException {
//		try {
//			ReferenceList list = restTemplate.getForObject(getBaseUrl(),
//					ReferenceList.class);
//			for (Link link : list.getLinks()) {
//				if (link.getRel().equals(ref)) {
//					return link.getHref();
//				}
//			}
//		} catch (Exception e) {
//			throw new MapServiceException(e.getMessage());
//		}
//		return null;
//	}
//
//	/**
//	 * Populate our local MapLayer implementation object.
//	 * 
//	 * @param kml
//	 * @return
//	 */
//	private MapLayer buildMapLayer(KmlDocumentLayer kml) {
//		String user = kml.getProperty(PROPERTY_USER_ID);
//		String community = kml.getProperty(PROPERTY_COMMUNITY_ID);
//		VectorLayer layer = new VectorLayer();
//		layer.setId(kml.getId());
//		layer.setName(kml.getName());
//		layer.setZOrder(kml.getZOrder());
//		layer.setFormat("KML");
//		layer.setOwnerID(user);
//		layer.setCommunityID(community);
//                layer.setDescription(kml.getDescription());
//                
//		return layer;
//	}
//
//	/**
//	 * Populate our local MapLayer implementation object.
//	 * 
//	 * @param geometric
//	 * @return
//	 */
//	private MapLayer buildMapLayer(mil.jpeocbd.enterprise.osgi.map.GeometricLayer geo) {
//		String user = geo.getProperty(PROPERTY_USER_ID);
//		String community = geo.getProperty(PROPERTY_COMMUNITY_ID);
//		WKTLayer layer = new WKTLayer();
//		layer.setId(geo.getId());
//		layer.setName(geo.getName());
//		layer.setZOrder(geo.getZOrder());
//		layer.setFormat("WKT");
//		layer.setOwnerID(user);
//		layer.setCommunityID(community);
//        layer.setDescription(geo.getDescription());
//        try {
//            for(FeaturedGeometry geometry : geo.getGeometries()){
//            	LayerFeature feature = new LayerFeature();
//            	feature.setLabel(geometry.getLabel());
//            	feature.setGeometry(geometry.getGeometry().toText());
//                if(geometry.getStyling()!=null) {
//                	switch(geometry.getStyling().getFill()) {
//                	
//	                	case SOLID:
//	            		default:
//	                		// TODO: figure out fill style in OpenLayers
//                	}
//                	//geometry.getStyling().setFill(PolygonStyle.Fill.)
//	                //System.out.println("getFill: " + geometry.getStyling().getFill() );
//                	feature.setFillColor(geometry.getStyling().getFillColor());
//                	feature.setStrokeWidth(geometry.getStyling().getLineStyle().getThickness());
//                	feature.setStrokeColor(geometry.getStyling().getLineStyle().getColor());
//                	switch(geometry.getStyling().getLineStyle().getFill()) {
//	                	case DASH:
//	                		feature.setStrokeDashstyle(LayerFeature.STROKE_DASH_STYLE.dash);
//	                	case SOLID:
//	            		default:
//	            			feature.setStrokeDashstyle(LayerFeature.STROKE_DASH_STYLE.solid);
//                	}
//                	// TODO: figure out line style
//	                
//                }
//                layer.addFeature(feature);
//            }
//        }
//        catch(Exception e) {}
//		return layer;
//	}
//
//	/**
//	 * Populate our local MapLayer implementation object.
//	 * 
//	 * @param gml
//	 * @return
//	 */
//	private MapLayer buildMapLayer(GmlDocumentLayer gml) {
//		String user = gml.getProperty(PROPERTY_USER_ID);
//		String community = gml.getProperty(PROPERTY_COMMUNITY_ID);
//		VectorLayer layer = new VectorLayer();
//		layer.setId(gml.getId());
//		layer.setName(gml.getName());
//		layer.setZOrder(gml.getZOrder());
//		layer.setFormat("GML");
//		layer.setOwnerID(user);
//		layer.setCommunityID(community);
//                layer.setDescription(gml.getDescription());
//		return layer;
//	}
//	
//	private MapLayer buildMapLayer(mil.jpeocbd.enterprise.osgi.map.CompositeLayer composite) {
//		CompositeLayer layerOut = new CompositeLayer();
//		layerOut.setId(composite.getId());
//		layerOut.setName(composite.getName());
//		layerOut.setLayers(this.getMapLayers(composite.getLayers()));
//                layerOut.setDescription(composite.getDescription());
//		return layerOut;
//	}
//
//	private Map buildMap(DefaultMap map) {
//		MapImpl ours = new MapImpl();
//		ours.setId(map.getId());
//		ours.setName(map.getName());
//                ours.setDescription(map.getDescription());
//		return ours;
//	}
//
//	/**
//	 * Populate our local MapLayer implementation object. Right now we only use
//	 * these for weather layers.
//	 * 
//	 * @param gml
//	 * @return
//	 */
//	private MapLayer buildMapLayer(GmlResourceLayer gml) {
//		// check the format
//		String format = gml.getProperty(PROPERTY_FORMAT);
//		String user = gml.getProperty(PROPERTY_USER_ID);
//		String community = gml.getProperty(PROPERTY_COMMUNITY_ID);
//		if (format == null)
//			format = "UNSET";
//		if (format.equals(MapLayer.FORMAT_GEORSS)) {
//			GeorssLayer layer = new GeorssLayer();
//			layer.setId(gml.getId());
//			layer.setName(gml.getName());
//			layer.setZOrder(gml.getZOrder());
//			layer.setFormat(MapLayer.FORMAT_GEORSS);
//			layer.setOwnerID(user);
//			layer.setCommunityID(community);
//                        layer.setDescription(gml.getDescription());
//			try {
//				layer.setUrl(gml.getUrl().toString());
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return layer;
//		}
//		return null;
//	}
//
//	/**
//	 * Populate our local MapLayer implementation object. Right now we only use
//	 * these for weather layers.
//	 * 
//	 * @param wms
//	 * @return
//	 */
//	private MapLayer buildMapLayer(WmsResourceLayer wms) {
//		// check the format
//		String format = wms.getProperty(PROPERTY_FORMAT);
//		String user = wms.getProperty(PROPERTY_USER_ID);
//		String community = wms.getProperty(PROPERTY_COMMUNITY_ID);
//		if (format == null)
//			format = "UNSET";
//		WmsLayer layer = new WmsLayer();
//		layer.setId(wms.getId());
//		layer.setName(wms.getName());
//		layer.setZOrder(wms.getZOrder());
//		layer.setFormat(MapLayer.FORMAT_WMS);
//		layer.setOwnerID(user);
//		layer.setCommunityID(community);
//                    layer.setDescription(wms.getDescription());
//		try {
//			layer.setUrl(wms.getUrl().toString());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return layer;
//	}
//
//	private String getKmlLayerBody(String uri) {
//		KmlDocumentLayer layer = getKmlDocumentLayer(uri);
//		return layer.getDocument().getContent();
//	}
//
//	private String getGmlLayerBody(String uri) {
//		GmlDocumentLayer layer = getGmlDocumentLayer(uri);
//		return layer.getDocument().getContent();
//	}
//
//	private String getWktLayerBody(String uri) {
//		GeometricLayer layer = getGeometricLayer(uri);
//		String wkt = "";
//		FeaturedGeometryListType list = layer.getGeometries();
//		for(FeaturedGeometryType feature : list.getFeaturedGeometries()) {
//			wkt+=feature.getGeometry().getWKT();
//		}
//		return wkt;
//	}
//
//	@Override
//	public String getKmlLayerBodyById(String id) {
//		String out = "<kml />";
//		try {
//			String link = getResource(REF_KML_DOCUMENTS) + "/" + id;
//			out = getKmlLayerBody(link);
//		} catch (MapServiceException mse) {
//
//		}
//		return out;
//	}
//
//	@Override
//	public String getGmlLayerBodyById(String id) {
//		String out = "<gml />";
//		try {
//			String link = getResource(REF_GML_DOCUMENTS) + "/" + id;
//			out = getGmlLayerBody(link);
//		} catch (MapServiceException mse) {
//
//		}
//		return out;
//	}
//
//	@Override
//	public String getWktLayerBodyById(String id) {
//		String out = "";
//		try {
//			String link = getResource(REF_GEOMETRIC) + "/" + id;
//			out = getWktLayerBody(link);
//		} catch (MapServiceException mse) {
//
//		}
//		return out;
//	}
//
//	private DefaultMap getMap(String uri) {
//		mil.jpeocbd.enterprise.osgi.xml.jaxb.map.Map map = restTemplate
//				.getForObject(uri,
//						mil.jpeocbd.enterprise.osgi.xml.jaxb.map.Map.class);
//		return (DefaultMap) marshaller.toDomainMap(map);
//	}
//
//	private KmlDocumentLayer getKmlDocumentLayer(String uri) {
//		mil.jpeocbd.enterprise.osgi.xml.jaxb.map.KmlDocumentLayer layer = restTemplate
//				.getForObject(
//						uri,
//						mil.jpeocbd.enterprise.osgi.xml.jaxb.map.KmlDocumentLayer.class);
//		return (KmlDocumentLayer) marshaller.toDomainLayer(layer);
//	}
//
//	private GeometricLayer getGeometricLayer(String uri) {
//		mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GeometricLayer layer = restTemplate
//				.getForObject(
//						uri,
//						mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GeometricLayer.class);
//		return (GeometricLayer) marshaller.toDomainLayer(layer);
//	}
//
//	private GmlDocumentLayer getGmlDocumentLayer(String uri) {
//		mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlDocumentLayer layer = restTemplate
//				.getForObject(
//						uri,
//						mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlDocumentLayer.class);
//		return (GmlDocumentLayer) marshaller.toDomainLayer(layer);
//	}
//
//	private GmlResourceLayer getGmlResourceLayer(String uri) {
//		mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlResourceLayer layer = restTemplate
//				.getForObject(
//						uri,
//						mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlResourceLayer.class);
//		return (GmlResourceLayer) marshaller.toDomainLayer(layer);
//	}
//
//	@Override
//	public void addWmsLayer(String name, String url, String layers,
//			String user, String portletGroupId) {
//		// create the layer
//		WmsResourceLayer wmsLayerIn = new WmsResourceLayer(name);
//		try {
//			URIBuilder uriBuilder = new URIBuilder(url);
//			URI erURI = uriBuilder.addParameter("layers", layers).build();
//			System.out.println("URI: " + erURI.toURL());
//			wmsLayerIn.setUrl(erURI.toURL());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		wmsLayerIn.setProperty(PROPERTY_FORMAT, MapLayer.FORMAT_WMS);
//		wmsLayerIn.setProperty(PROPERTY_USER_ID, user);
//		wmsLayerIn.setProperty(PROPERTY_COMMUNITY_ID, portletGroupId);
//		// convert the domain object
//		AbstractLayerType layer = marshaller.toXmlLayer(wmsLayerIn);
//
//		// post to service
//		try {
//			addLayerToMap(wmsLayerIn, DEFAULT_MAP_NAME);
//			// return
//			// restTemplate.postForLocation(getResource(REF_GML_RESOURCES),
//			// layer);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// return null;
//	}
//
//	@Override
//	public void addGeorssLayer(String name, String url, String user,
//			String portletGroupId) {
//		// create the layer
//		GmlResourceLayer georssLayerIn = new GmlResourceLayer();
//		try {
//			georssLayerIn.setName(name);
//			georssLayerIn.setUrl(url);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		georssLayerIn.setProperty(PROPERTY_FORMAT, MapLayer.FORMAT_GEORSS);
//		georssLayerIn.setProperty(PROPERTY_USER_ID, user);
//		georssLayerIn.setProperty(PROPERTY_COMMUNITY_ID, portletGroupId);
//		// convert the domain object
//		AbstractLayerType layer = marshaller.toXmlLayer(georssLayerIn);
//		// post to service
//		try {
//			// return
//			// restTemplate.postForLocation(getResource(REF_GML_RESOURCES),
//			// layer);
//			addLayerToMap(georssLayerIn, DEFAULT_MAP_NAME);
//		} catch (Exception e) {
//		}
//		// return null;
//	}
//
//	@Override
//	public void shareLayerById(String layerId, String communityId, String format) {
//		if (format.equals("KML")) {
//			mil.jpeocbd.enterprise.osgi.xml.jaxb.map.KmlDocumentLayer kmlLayer = restTemplate
//					.getForObject(
//							"http://localhost/enterprise/map/rest/layers/geometric/kml/document/"
//									+ layerId,
//							mil.jpeocbd.enterprise.osgi.xml.jaxb.map.KmlDocumentLayer.class);
//
//			KmlDocumentLayer kmlLayerOut = (KmlDocumentLayer) marshaller
//					.toDomainLayer(kmlLayer);
//			kmlLayerOut.setProperty(PROPERTY_COMMUNITY_ID, communityId);
//			AbstractLayerType layer = marshaller.toXmlLayer(kmlLayerOut);
//			try {
//				restTemplate.put(
//						"http://localhost/enterprise/map/rest/layers/geometric/kml/document/"
//								+ layerId, layer);
//
//			} catch (Exception e) {
//			}
//		} else if (format.equals("GML")) {
//			mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlDocumentLayer gmlLayer = restTemplate
//					.getForObject(
//							"http://localhost/enterprise/map/rest/maps?layerIds="
//									+ layerId,
//							mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlDocumentLayer.class);
//			GmlDocumentLayer gmlLayerOut = (GmlDocumentLayer) marshaller
//					.toDomainLayer(gmlLayer);
//			gmlLayerOut.setProperty(PROPERTY_COMMUNITY_ID, communityId);
//			AbstractLayerType layer = marshaller.toXmlLayer(gmlLayerOut);
//			try {
//				restTemplate.put(
//						"http://localhost/enterprise/map/rest/layers/geometric/gml/document/"
//								+ layerId, layer);
//			} catch (Exception e) {
//			}
//		} else {
//			mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlResourceLayer gmlLayer = restTemplate
//					.getForObject(
//							"http://localhost/enterprise/map/rest/maps?layerIds="
//									+ layerId,
//							mil.jpeocbd.enterprise.osgi.xml.jaxb.map.GmlResourceLayer.class);
//			GmlResourceLayer gmlLayerOut = (GmlResourceLayer) marshaller
//					.toDomainLayer(gmlLayer);
//			gmlLayerOut.setProperty(PROPERTY_COMMUNITY_ID, communityId);
//			AbstractLayerType layer = marshaller.toXmlLayer(gmlLayerOut);
//			try {
//				restTemplate.put(
//						"http://localhost/enterprise/map/restlayers/geometric/gml/resource/"
//								+ layerId, layer);
//			} catch (Exception e) {
//			}
//		}
//	}
//
//	@Override
//	public List<MapLayer> getMapLayers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//        
//        public List<MapLayer> getMapLayers(String uri){
//            return getMapLayers(getMap(uri));
//        }
}
