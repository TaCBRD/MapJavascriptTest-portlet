<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<head>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/OpenLayers.js"></script>
<script type="text/javascript">
var map;
function init() {
	var mapOptions = {
	    div: "map"
	};

	map = new OpenLayers.Map('map', mapOptions);
	map.addLayer(new OpenLayers.Layer.OSM());
	map.addControl(new OpenLayers.Control.LayerSwitcher());
	map.setCenter(new OpenLayers.LonLat(0, 0), 2);
}
</script>
</head>
<body onload="init()">
<div id="map" style="position: relative; width: 100%; height: 500px;"></div>
</body>
</html>
