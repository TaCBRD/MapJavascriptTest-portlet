<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<head>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/OpenLayers.js"></script>
<script type="text/javascript">
var map;
var drawingControls;
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
</script>
</head>
<body onload="init()">
<div id="map" style="position: relative; width: 100%; height: 500px;"></div>
<div style="font-weight: bold">Drawing Layer</div>
<select id="mapMode" name="mapMode" size="1" onchange="changeMapMode(this.value);">
    <option value="none" selected>Navigation</option>
    <option value="polygon">Draw Polygon</option>
</select>
</body>
</html>
