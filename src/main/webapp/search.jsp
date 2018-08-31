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
	<table>
		<tr>
			<th>Names     Price     Change      % Change</th>
		</tr>
		<%
			List<Company> comps = (List<Company>) request.getAttribute("companies");
			for (Company c : comps) {
				request.setAttribute("c", c);
		%>
		<tr>
        
        <td> <form action="find" method="GET">
            <input type="hidden" name="dropdown" value="ticker">
            <input type="hidden" name="ticker" value="${c.getTick()}">
            <label for="submit">
                <div>${c.getName()} (${c.getTick()})</div>
            <div>${c.getPrice()}</div>
            <div>${c.getPriceChange()}</div>
            <div>${c.getPercentChange()}</div></label>
            <input type="submit" value="Submit"/>
            </form>
            </td>
		
		</tr>
		<%
			}
		%>
	</table>

</body>
</html>