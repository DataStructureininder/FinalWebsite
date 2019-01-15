<!DOCTYPE HTML>
<!--
	Caminar by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
<head>
<title>Search Result</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="/FinalTester/css/main.css" />
</head>
<body>

	<!-- Header -->
	<header id="header">
		<div class="logo">
			<a href="http://localhost:8080/FinalTester/Main">Search Result<span>by
					group 13</span></a>
					<br><span><a href="/FinalTester/intro.html"
				style="font-size: 30px; color:#E6E6FA">Introduction</a></span>
		</div>
	</header>

	<!-- Main -->
	<section id="main">
		<div class="inner">
			

			<p>
				<%
					String[][] orderList = (String[][]) request.getAttribute("query");
					for (int i = 0; i < orderList.length; i++) {
				%>
				<br>
			<div>
				-
				<%=(i + 1) + ". "%>> <a href='<%=orderList[i][1]%>'><%=orderList[i][0]%></a>
				<br>
				<h style="font-size:5px ;"><%=orderList[i][2]%></h>
			</div>
			<br>

			<%
				}
			%>
			</p>
		</div>
	</section>

	<!-- Footer -->
	<footer id="footer">
		<div class="container">
			<ul class="icons">
				<li><a href="https://twitter.com" class="icon fa-twitter"><span
						class="label">Twitter</span></a></li>
				<li><a href="https://www.facebook.com" class="icon fa-facebook"><span
						class="label">Facebook</span></a></li>
				<li><a href="https://www.instagram.com/"
					class="icon fa-instagram"><span class="label">Instagram</span></a></li>
				<li><a href="https://mail.google.com/mail/"
					class="icon fa-envelope-o"><span class="label">Email</span></a></li>
			</ul>
		</div>
		<div class="copyright">
			&copy; Untitled. All rights reserved. Images <a
				href="https://unsplash.com">Unsplash</a> Design <a
				href="https://templated.co">TEMPLATED</a>
		</div>
	</footer>

	<!-- Scripts -->
	<script src="/FinalTester/JavaScript/jquery.min.js"></script>
	<script src="/FinalTester/JavaScript/jquery.poptrox.min.js"></script>
	<script src="/FinalTester/JavaScript/skel.min.js"></script>
	<script src="/FinalTester/JavaScript/util.js"></script>
	<script src="/FinalTester/JavaScript/main.js"></script>

</body>
</html>