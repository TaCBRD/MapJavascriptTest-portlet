<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<%
// page settings
String resourceURL = "http://localhost:8080/web/guest/map?p_p_id=MapService_WAR_MapServiceportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1";
%>

<head>
<c:import url="http://localhost:8080/MapService-portlet/html/mapservice/initMap.jsp"/>
<script type="text/javascript">
var tacbrdMap;
function init() {     
    tacbrdMap = new TaCBRDMap("tacbrd","<%= resourceURL %>");
    //tacbrdMap.map.{your OpenLayers calls}
}
</script>
</head>
<body onload="init()">
<div id="tacbrd" style="position: relative; width: 100%; height: 500px;"></div>
</body>
</html>
