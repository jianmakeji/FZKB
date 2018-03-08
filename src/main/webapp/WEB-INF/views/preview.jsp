<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>预览</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<link rel="stylesheet" type="text/css" href="resources/css/src/preview.css" />
</head>
<body>
	<div id="modelContainer" class="modelContent">
	</div>
	<div class="operationContent">

			<div id="container"></div>
		</div>
</body>
<script type="text/javascript">
	var trouserImageUrl = "${tr.imageUrl}";
	var trouserX = "${tr.style1}";
	var trouserY = "${tr.style2}";
	var trouserZ = "${tr.style3}";
	var trouserID = "${tr.id}";
	var underwearImageUrl = "${uw.imageUrl}";
	var underwearX = "${uw.style1}";
	var underwearY = "${uw.style2}";
	var underwearZ = "${uw.style3}";
	var underwearID = "${uw.id}";
	var overcoatImageUrl = "${gc.imageUrl}";
	var overcoatX = "${gc.style1}";
	var overcoatY = "${gc.style2}";
	var overcoatZ = "${gc.style3}";
	var overcoatID = "${gc.id}";
	
</script>
<script src="resources/js/lib/three.js"></script>
<script src="resources/js/lib/three.interaction.js"></script>
<script src="resources/js/lib/jquery-2.1.0.js"></script>
<script src="resources/js/controls/OrbitControls.js"></script>
<script src="resources/js/curves/NURBSCurve.js"></script>
<script src="resources/js/curves/NURBSUtils.js"></script>
<script src="resources/js/loaders/FBXLoader.js"></script>

<script src="resources/js/Detector.js"></script>
<script src="resources/js/lib/stats.min.js"></script>
<script src="resources/js/lib/inflate.min.js"></script>
<script src="resources/js/src/preview.js" async="async"></script>
<script src="resources/js/src/preview_axis.js" async="async"></script>
</html>
