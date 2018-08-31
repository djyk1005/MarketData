<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Info</title>
</head>
<body>
    <h1>Company name: ${company.getName()}</h1>

    <p>Ticker: ${company.getTick()}</p>
    <p>Price: ${company.getPrice()}</p>
    <p>Day to Day Price Change: ${company.getPriceChange()}</p>
    <p>Percent Change: ${company.getPercentChange()}</p>

    <form action="seeMore"  method="GET" >
        <input type="hidden" name="ticker" value="${company.getTick()}">
        <input type = "submit" value = "See More"/> 
    </form>
<p></p>

</body>
</html>