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
</body>
<script type="text/javascript">
	var trouserImageUrl = "${trUrl}";
	var underwearImageUrl = "${uwUrl}";
	var overcoatImageUrl = "${gcUrl}";
</script>
<script src="resources/js/lib/three.js"></script>
<script src="resources/js/lib/tween.min.js"></script>
<script src="resources/js/controls/TrackballControls.js"></script>
<script src="resources/js/lib/jquery-2.1.0.js"></script>
<script src="resources/js/controls/OrbitControls.js"></script>
<script src="resources/js/curves/NURBSCurve.js"></script>
<script src="resources/js/curves/NURBSUtils.js"></script>
<script src="resources/js/loaders/FBXLoader.js"></script>

<script src="resources/js/Detector.js"></script>
<script src="resources/js/lib/stats.min.js"></script>
<script src="resources/js/lib/inflate.min.js"></script>
<script src="resources/js/src/preview.js" async="async"></script>
</html>
