<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>

	<head>
		<meta charset="utf-8" />
		<title>服装设计师看板</title>
		<link rel="stylesheet" href="resources/css/materialize/materialize.css" />
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<link rel="stylesheet" type="text/css" href="resources/css/src/match.css"/>
	</head>

	<body>
		<div id="modelContainer" class="modelContent">
			<div class="selectArea">
				<input name="clothingGroup" type="radio" value="0" id="underwear" checked="checked" />
				<label for="underwear">内搭</label>

				<input name="clothingGroup" type="radio" value="1" id="cloth" />
				<label for="cloth">外套</label>

				<input name="clothingGroup" type="radio" value="2" id="trousers" />
				<label for="trousers">裤装</label>
				
				<label id="matchNameLabel" for="matchName">搭配名称:</label>
				<input id="matchName" type="text" class="validate" style="color:gray;">
          		
				<a class="waves-effect waves-light btn" id="saveMatchBtn">保存搭配</a>
				
				<a class="waves-effect waves-light btn" id="resetBtn">重&nbsp;&nbsp;&nbsp;置</a>
			</div>
			
		</div>

		<div class="operationContent">

			<div id="container"></div>
		</div>
	</body>
	<script type="text/javascript">
		var authCode = "${authCode}";
		var userId = "${userId}";
	</script>
	<script src="resources/js/lib/three.js"></script>
	<script src="resources/js/lib/tween.min.js"></script>
	<script src="resources/js/controls/TrackballControls.js"></script>
	<script src="resources/js/renderers/CSS3DRenderer.js"></script>
	<script src="resources/js/lib/jquery-2.1.0.js"></script>
	<script src="resources/js/materialize/materialize.js"></script>
	<script src="resources/js/controls/OrbitControls.js"></script>
	<script src="resources/js/curves/NURBSCurve.js"></script>
	<script src="resources/js/curves/NURBSUtils.js"></script>
	<script src="resources/js/loaders/FBXLoader.js"></script>

	<script src="resources/js/Detector.js"></script>
	<script src="resources/js/lib/stats.min.js"></script>
	<script src="resources/js/lib/inflate.min.js"></script>
	<script src="resources/js/src/3dmodel.js"  async="async"></script>
	<script src="resources/js/src/spherePanel.js" async="async"></script>

</html>