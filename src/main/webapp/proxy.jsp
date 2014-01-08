<%@page session="false"%>
<%@page import="java.net.*,java.io.*" %>
<%
try {
	String reqUrl = URLDecoder.decode(request.getParameter("url"));
        
	URL url = new URL(reqUrl);
	HttpURLConnection con = (HttpURLConnection)url.openConnection();
	con.setDoOutput(true);
	con.setRequestMethod(request.getMethod());
	int clength = request.getContentLength();
	if(clength > 0) {
		con.setDoInput(true);
		byte[] idata = new byte[clength];
		request.getInputStream().read(idata, 0, clength);
		con.getOutputStream().write(idata, 0, clength);
	}
	response.setContentType(con.getContentType());
        if(con.getContentType().contains("image")){
            response.setHeader("Content-Disposition", "inline;filename=\"\"");
            response.setHeader("Content-Length", ((Integer)con.getContentLength()).toString());
        }
 
	BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
	String line;
	while ((line = rd.readLine()) != null) {
		out.println(line); 
                //System.out.println(line);
	}
	rd.close();
 
} catch(Exception e) {
	e.printStackTrace();
	response.setStatus(500);
}
%>