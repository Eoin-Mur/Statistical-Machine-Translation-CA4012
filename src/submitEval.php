<?php
	parse_str($_SERVER['QUERY_STRING']);

	$myFile = fopen("../Data/user.eval","a");
	$txt = $id."    ".$_POST["adequacy"]."    ".$_POST["fluency"]."\n";

	fwrite($myFile, $txt);
	fclose($myFile);

?>
<html>
	<head>
		<style>
			body
			{
				background-color: lightgrey;
			}
			div.middle
			{
				position: absolute;
				height: 200px;
			    width: 400px;
			    margin: -100px 0 0 -200px;
				top: 50%;
				left: 50%;
			}
		</style>
	</head>
	<body>
		<div align="center">
			<h1 >Thank You Heres a cookie</h1>
			<image src="https://www.hamptoncreek.com/img/p-just-cookies/panel-cookie-choc-cookie.png" width="500" height="500" align="center"></image>
		</div>
	</body>
</html>