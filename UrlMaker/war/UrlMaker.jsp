<!doctype html>

<%@page import="com.google.appengine.api.utils.SystemProperty"
		import="com.thiagovinicius.web.urlmkr.shared.DefaultValues"
%><%
	boolean domainOk = request.getServerName().equals(DefaultValues.MAIN_DOMAIN);
	if (!domainOk && SystemProperty.environment.value() ==
		SystemProperty.Environment.Value.Production) {
		String querystr = request.getQueryString();
		querystr = querystr == null ? "" : "?"+querystr;
		response.sendRedirect("http://"+DefaultValues.MAIN_DOMAIN+
			request.getRequestURI()+querystr);
	}
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="UrlMaker.css">
    <title>Url Maker</title>
    <style>
h1 {
  font-size: 2em;
  font-weight: bold;
  color: #777777;
  margin: 40px 0px 70px;
  text-align: center;
}
    </style>
    <script type="text/javascript" language="javascript" src="urlmaker/urlmaker.nocache.js"></script>
  </head>

  <body>

    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>

    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>

    <h1>Url Maker</h1>

    <div>
    </div>

  </body>
</html>
