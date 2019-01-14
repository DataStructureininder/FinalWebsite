<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
<style>
body {
	background-color: #FFFAFA;
}
a:link {
  color: #778899; 
}

a:visited {
  color: #800080;
}

</style>

</head>

<body>
	<p style='font-family: Didot; font-size: 30px;'><u>Search Result</u></p>
<a href='http://localhost:8080/FinalTester/Main'>
	<button style='width:140px;height:25px;background-color:#dc9bcb; border-color: transparent;position: absolute; left: 10px; font-family: Didot; font-size: 18px;'>>>Search Page</button>
</a>
<img src="/FinalTester/imgs/chuangb2.png" style='width:700px; height: 700px; position: fixed; top: -10%; left: 22%;  z-index:-1;'>

<p>
		<%
			String[][] orderList = (String[][]) request.getAttribute("query");
			for (int i = 0; i < orderList.length; i++) {
		%>
		<br>
	<div style='background-color: rgba(100%,80%,80%,50%);'>
		<a href='<%=orderList[i][1]%>'><%=orderList[i][0]%></a> <br>
		<h style="font-size:5px ;"><%=orderList[i][1]%></h>
	</div>
	<br>

	<%
		}
	%>
</p>
</body>
</html>