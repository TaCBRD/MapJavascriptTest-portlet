<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<head>
<portlet:actionURL name="saveDrawing" var="saveDrawingURL"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/OpenLayers.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.js"></script>
<script type="text/javascript">
var map;
var drawingControls;
var drawingLayer;
var GEOGRAPHIC = new OpenLayers.Projection("EPSG:4326");
var MERCATOR = new OpenLayers.Projection("EPSG:900913");
var TPAT_LAYER_URL = "http://localhost/enterprise/map/rest/layers/geometric/TpatAreaOfInterest";

function init() {
	var mapOptions = {
	    div: "map"
	};

	map = new OpenLayers.Map('map', mapOptions);
	map.addLayer(new OpenLayers.Layer.OSM());
	map.addControl(new OpenLayers.Control.LayerSwitcher());
	map.setCenter(new OpenLayers.LonLat(0, 0), 2);

	drawingLayer = new OpenLayers.Layer.Vector("Drawing layer");
	map.addLayer(drawingLayer);

	drawingControls = {
	    polygon: new OpenLayers.Control.DrawFeature(drawingLayer, OpenLayers.Handler.Polygon, {
	        eventListeners: {
	            "featureadded": controlFeatureHandler
	        }
	    }),
	    select: new OpenLayers.Control.SelectFeature(
	        drawingLayer,
	        {
	            clickout: false, toggle: false,
	            multiple: false, hover: false,
	            toggleKey: "ctrlKey", // ctrl key removes from selection
	            multipleKey: "shiftKey", // shift key adds to selection
	            box: true
	        }
	    )
	};

	for (var key in drawingControls) {
	    map.addControl(drawingControls[key]);
	}
	
	// Get TPAT layer
	// Initializce the layer in IM/IT using:
	//	 http://localhost/tpat/rest/publishAreaOfInterest
	// It should show up at
	//   http://localhost/enterprise/map/rest/layers/geometric/TpatAreaOfInterest
	addTPAT();
}

function changeMapMode(value) {
    for (var key in drawingControls) {
        var control = drawingControls[key];
        if (value == key) {
            control.activate();
        } else {
            control.deactivate();
        }
    }
}

function controlFeatureHandler(event) {
}

function saveDrawingLayer() {
	var folderName = prompt("Enter a name for this layer: ");
	var description = "Saved from drawing layer.";
	$("#drawingKML").val(
			getKMLFromFeatures(drawingLayer.features, folderName, description));
	$("#drawingName").val(folderName);
	$("#drawingForm").submit();
}

function getKMLFromFeatures(features, folderName, folderDescription) {
	var format = new OpenLayers.Format.KML({
		'maxDepth' : 10,
		'extractStyles' : true,
		'internalProjection' : map.baseLayer.projection,
		'externalProjection' : new OpenLayers.Projection(GEOGRAPHIC),
		'foldersName' : folderName,
		'foldersDesc' : folderDescription
	// 'placemarksDesc': placemarksDesc
	});

	return format.write(features);
}


function addWKTToMap(data){
     var tpat = new OpenLayers.Layer.Vector(
                    data.name			
                );
        if(data.hasOwnProperty("geometries")){
        $.each(data.geometries.featuredGeometries, function(num, geo){
            var feature_style = OpenLayers.Util.extend({}, OpenLayers.Feature.Vector.style["default"]);
            feature_style.label=geo.label;
            var fillCol = geo.styling.fillColor;
            var lineCol = geo.styling.lineStyle.color;
            feature_style.fillColor='rgb(' + [fillCol.r, fillCol.g, fillCol.b].join(',') + ')';
            feature_style.strokeWidth=geo.styling.lineStyle.thickness;
            feature_style.strokeColor='rgb(' + [lineCol.r, lineCol.g, lineCol.b].join(',') + ')';
            var wktText = geo.geometry.wkt;
            var feature_geometry = OpenLayers.Geometry.fromWKT(wktText);
            feature_geometry.transform(GEOGRAPHIC, MERCATOR);
            var layer_feature = new OpenLayers.Feature.Vector(feature_geometry, null, feature_style);
            tpat.addFeatures([layer_feature]);
        });
        }

    map.addLayer(tpat);
}

function addTPAT() {
	$.ajax({
        url: "<%= request.getContextPath() %>/proxy.jsp?url=" + TPAT_LAYER_URL,
        dataType: "json",
        crossDomain:true,
        success: function (obj) {
        	addWKTToMap(obj);
        }
    });
}
</script>
</head>
<body onload="init()">
<div style="font-weight: bold">Drawing Layer</div>
<select id="mapMode" name="mapMode" size="1" onchange="changeMapMode(this.value);">
    <option value="none" selected>Navigation</option>
    <option value="polygon">Draw Polygon</option>
</select>
<button onClick="saveDrawingLayer()">Save</button>
<div id="map" style="position: relative; width: 100%; height: 500px;"></div>

<form id="drawingForm" method="post" action="<%= saveDrawingURL.toString() %>">
	<input id="drawingKML" name="drawingKML" type="hidden" value="" />
	<input id="drawingName" name="drawingName" type="hidden" value="" />
</form>	
</body>
</html>
