<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List, com.sapient.models.Company"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
</head>
<body>
        <ul class="list-group">
                <% 	List<Company> comps = (List<Company>) request.getAttribute("companies");
                    for(Company c : comps) {
                        %>
                        <li class="list-group-item"><%  out.println(c);  %></li>
                        <%
                    }
                %>
                </ul>
</body>
</html>