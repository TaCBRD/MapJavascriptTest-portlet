<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<head>
<script type="text/javascript" src="/MapService-portlet/js/jquery.js"></script>
<script type="text/javascript" src="https://maps.google.com/maps/api/js?V=3.3&sensor=false"></script>
<script type="text/javascript" src="/MapService-portlet/js/tacbrd-map.js"></script>
<script type="text/javascript">
var tacbrdMap;
function init() {     
    tacbrdMap = new TaCBRDMap("tacbrd");
    //tacbrdMap.map.{your OpenLayers calls}
}
</script>
</head>
<body onload="init()">
<div id="tacbrd" style="position: relative; width: 100%; height: 500px;"></div>
</body>
</html>
