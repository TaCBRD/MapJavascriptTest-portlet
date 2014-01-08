package com.omi.mapservice.portlet;

import org.springframework.web.client.RestTemplate;
import mil.jpeocbd.enterprise.osgi.core.xml.jaxb.map.MapMarshallingServiceImpl;
import mil.jpeocbd.enterprise.osgi.map.KmlDocumentLayer;
import mil.jpeocbd.enterprise.osgi.xml.XmlDocument;
import mil.jpeocbd.enterprise.osgi.xml.jaxb.map.AbstractLayerType;
import mil.jpeocbd.enterprise.osgi.xml.jaxb.map.MapMarshallingService;
import mil.jpeocbd.enterprise.osgi.xml.jaxb.map.ReferenceList;
import mil.jpeocbd.enterprise.osgi.map.DefaultMap;
import mil.jpeocbd.enterprise.osgi.map.FeaturedGeometry;
import mil.jpeocbd.enterprise.osgi.map.Layer;

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

	// // IM/IT OSGi connection
	RestTemplate restTemplate = new RestTemplate();
	private static MapMarshallingService marshaller = new MapMarshallingServiceImpl();

	public void setBaseUrl(String url) {
		this.baseUrl = url;
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public void addLayerToMap(Layer layer, String mapName) {
		// get suggested id from name
		String mapId = "map-" + mapName.toLowerCase().replace(" ", "-"); 
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
		mil.jpeocbd.enterprise.osgi.xml.jaxb.map.Map xmlMap = marshaller
				.toXmlMap(map);
		restTemplate.put(getBaseUrl() + REF_MAPS + "/" + map.getId(), xmlMap);
	}

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

	/**
	 * Populate our local MapLayer implementation object.
	 * 
	 * @param geometric
	 * @return
	 */
	private WKTLayer buildMapLayer(
			mil.jpeocbd.enterprise.osgi.map.GeometricLayer geo) {
		String user = geo.getProperty(PROPERTY_USER_ID);
		String community = geo.getProperty(PROPERTY_COMMUNITY_ID);
		WKTLayer layer = new WKTLayer();
		layer.setId(geo.getId());
		layer.setName(geo.getName());
		layer.setZOrder(geo.getZOrder());
		layer.setFormat("WKT");
		layer.setOwnerID(user);
		layer.setCommunityID(community);
		layer.setDescription(geo.getDescription());
		try {
			for (FeaturedGeometry geometry : geo.getGeometries()) {
				LayerFeature feature = new LayerFeature();
				feature.setLabel(geometry.getLabel());
				feature.setGeometry(geometry.getGeometry().toText());
				if (geometry.getStyling() != null) {
					switch (geometry.getStyling().getFill()) {

					case SOLID:
					default:
						// TODO: figure out fill style in OpenLayers
					}
					// geometry.getStyling().setFill(PolygonStyle.Fill.)
					// System.out.println("getFill: " +
					// geometry.getStyling().getFill() );
					feature.setFillColor(geometry.getStyling().getFillColor());
					feature.setStrokeWidth(geometry.getStyling().getLineStyle()
							.getThickness());
					feature.setStrokeColor(geometry.getStyling().getLineStyle()
							.getColor());
					switch (geometry.getStyling().getLineStyle().getFill()) {
					case DASH:
						feature.setStrokeDashstyle(LayerFeature.STROKE_DASH_STYLE.dash);
					case SOLID:
					default:
						feature.setStrokeDashstyle(LayerFeature.STROKE_DASH_STYLE.solid);
					}
					// TODO: figure out line style

				}
				layer.addFeature(feature);
			}
		} catch (Exception e) {
		}
		return layer;
	}
}
