
<%@page import="br.ucb.model.Local"%>
<%@page import="java.util.List"%>
<html>
<body>
	<h2>
		Hello World!
	</h2>
		<% List<Local> locais = (List) request.getAttribute("AttrLocais");
		for (Local l : locais) {
			out.print("<p>"+l.toString()+"</p>");
		}
		%>

</body>
</html>
