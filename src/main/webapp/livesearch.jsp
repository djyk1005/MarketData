<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Live Search</title>
	<style>
		#myInput {
    		width: 100%; /* Full-width */
    		font-size: 16px; /* Increase font-size */
    		padding: 12px 20px 12px 40px; /* Add some padding */
    		border: 1px solid #ddd; /* Add a grey border */
    		margin-bottom: 12px; /* Add some space below the input */
		}
		#myUL {
    		/* Remove default list styling */
    		list-style-type: none;
    		padding: 0;
   		 	margin: 0;
		}

		#myUL li a{
    		border: 1px solid #ddd; /* Add a border to all links */
    		margin-top: -1px; /* Prevent double borders */
    		background-color: #f6f6f6; /* Grey background color */
    		padding: 12px; /* Add some padding */
    		text-decoration: none; /* Remove default text underline */
    		font-size: 18px; /* Increase the font-size */
    		color: black; /* Add a black text color */
    		display: block; /* Make it into a block element to fill the whole list */
		}

		#myUL li a:hover:not(.header) {
    		background-color: #eee; /* Add a hover effect to all links, except for headers */
		}
	</style>
	
	<script>
	function myFunction() {
    	// Declare variables
    	var input, filter, ul, li, a, i;
    	input = document.getElementById('myInput');
    	filter = input.value.toUpperCase();
    	ul = document.getElementById("myUL");
    	li = ul.getElementsByTagName('li');

    	// Loop through all list items, and hide those who don't match the search query
    	for (i = 0; i < li.length; i++) {
        	a = li[i].getElementsByTagName("a")[0];
        	if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            	li[i].style.display = "";
        	} else {
            	li[i].style.display = "none";
        	}
    	}
	}
	function submitform(row, name){
		document.getElementById("comp").value=name;
		document.getElementById("myForm").submit();
	}
	</script>
	
</head>
<body>
	<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for companies...">

	<form id="myForm" action="find" method="GET"> 
	<input type="hidden" name="ticker" id="comp"></input> 
	<input type="hidden" name="dropdown" value="company"></input> 
	<ul id="myUL">
		<%
			List<String> comps = (List<String>) request.getAttribute("companies");
			int i=1;
			for (String c : comps) {
				request.setAttribute("c", c);
				request.setAttribute("row", i);
				i++;
		%>
		 
  		<li id="${row}" onclick="submitform('${row}', '${c}')"><a>${c}</a></li>
  		
  		<%
			}
		%>
	
	</ul>
	</form>

</body>
</html>