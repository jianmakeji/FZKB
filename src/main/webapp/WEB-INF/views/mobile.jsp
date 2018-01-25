<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>

	<head>
		<meta charset="utf-8">
		<title>服装设计师看板</title>
		<link rel="stylesheet" href="resources/css/materialize/materialize.css" />
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

		<!-- Link Swiper's CSS -->
		<link rel="stylesheet" href="resources/css/swiper/swiper.min.css">
		<!-- Demo styles -->
		<style>
			body {
				background: #fff;
				font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
				font-size: 14px;
				color: #000;
				margin: 0;
				padding: 0;
				text-align:center;
			}
			
			.swiper-container {
				width: 90%;
				padding-top: 15px;
				padding-bottom: 50px;
			}
			
			.swiper-slide {
				background-position: center;
				background-size: cover;
				width: 100px;
				height: 100px;
			}
			
			#matchNameLabel {
				position: relative;
				left: 10px;
			}
			
			#matchName {
				width: 110px;
				position: relative;
				left: 10px;
				color: white;
			}
			
			button {
				color: rgba(127, 255, 255, 0.75);
				background: transparent;
				outline: 1px solid rgba(127, 255, 255, 0.75);
				border: 0px;
				padding: 5px 10px;
				cursor: pointer;
			}
			
			button:hover {
				background-color: rgba(0, 255, 255, 0.5);
			}
			
			button:active {
				color: #000000;
				background-color: rgba(0, 255, 255, 0.75);
			}
			
			.btnClass{
				text-align: center;
			}
		</style>
	</head>

	<body>
		<!-- Swiper -->
		<div id="modelContainer" class="modelContent">

		</div>
		<div class="selectArea">
			<input name="clothingGroup" type="radio" value="0" id="underwear" checked="checked" />
			<label for="underwear">内搭</label>

			<input name="clothingGroup" type="radio" value="1" id="cloth" />
			<label for="cloth">外套</label>

			<input name="clothingGroup" type="radio" value="2" id="trousers" />
			<label for="trousers">裤装</label>

			<label id="matchNameLabel" for="matchName">搭配名称:</label>
			<input id="matchName" type="text" class="validate">

			
		</div>
		<div class="btnClass">
			<a class="waves-effect waves-light btn" id="saveMatchBtn">保存搭配</a>
			<a class="waves-effect waves-light btn" id="resetBtn">重&nbsp;&nbsp;&nbsp;&nbsp;置</a>
		</div>
		<div class="swiper-container">
			<div class="swiper-wrapper">

			</div>
		</div>

		<!-- Swiper JS -->
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
		<!--  <script src="resources/js/lib/stats.min.js"></script>-->
		<script src="resources/js/lib/inflate.min.js"></script>
		<script src="resources/js/src/3dmodel_mobile.js" async="async"></script>
		<script src="resources/js/swiper/swiper.min.js"></script>

		<!-- Initialize Swiper -->
		<script>
			
			var underwear;
			var uwId;
			var greatcoat;
			var gcId;
			var trousers;
			var trId;
			
			function IsPC() {
				  var userAgentInfo = navigator.userAgent;
				  var Agents = ["Android", "iPhone",
				        "SymbianOS", "Windows Phone",
				        "iPad", "iPod"];
				  var flag = true;
				  for (var v = 0; v < Agents.length; v++) {
				    if (userAgentInfo.indexOf(Agents[v]) > 0) {
				      flag = false;
				      break;
				    }
				  }
				  return flag;
				}
			
			$.ajax({
				type:'GET',
				url:'getRandomMaterial',
				data:{count:10},
				dataType:'json',
				success:function(data){

					for (var i = 0; i < data.object.length; i++){
						var html = '<div class="swiper-slide" id="'+data.object[i].id+'" style="background-image:url('+data.object[i].imageUrl+')"></div>';
						$(".swiper-wrapper").append(html);
						
					}
					
					var swiper = new Swiper('.swiper-container', {
						effect: 'coverflow',
						grabCursor: true,
						centeredSlides: true,
						slidesPerView: 'auto',
						initialSlide: 3,
						coverflow: {
							rotate: 50,
							stretch: 0,
							depth: 100,
							modifier: 1,
							slideShadows: true
						}
					});
					
					$(".swiper-slide").click(function(){
						var radioVal = $('input[name="clothingGroup"]:checked').val();
						var urlStr = $(this).css('background-image');
						if (IsPC()){
							urlStr = urlStr.substring(5,urlStr.length - 2);
						}
						else{
							urlStr = urlStr.substring(4,urlStr.length - 1);
						}
						
						var arrayStr = urlStr.split('/');
						var imageUrl = "image?imgPath="+urlStr;
						
						var id = $(this).attr('id');
						
						var texture = new THREE.Texture();
						var tLoader = new THREE.ImageLoader(manager);
						tLoader.crossOrigin = true;
						tLoader.load(imageUrl, function(image) {
							
							texture.image = image;
							texture.needsUpdate = true;
							texture.wrapS = THREE.RepeatWrapping;
							texture.wrapT = THREE.RepeatWrapping;
							texture.repeat.set(1, 1);
				
							if(radioVal == 0) { //内搭
								underwearObject.traverse(function(child) {
									if(child instanceof THREE.Mesh) {
										child.material.map = texture;
									}
								});
								underwear = urlStr;
								uwId = id;
							} else if(radioVal == 1) { //外套
								overcoatObject.traverse(function(child) {
									if(child instanceof THREE.Mesh) {
										child.material.map = texture;
									}
								});
								greatcoat = urlStr;
								gcId = id;
							} else if(radioVal == 2) { //裤装
								trouserObject.traverse(function(child) {
									if(child instanceof THREE.Mesh) {
										child.material.map = texture;
									}
								});
								trousers = urlStr;
								trId = id;
							}
						});
					});
				},
			});
			$(document).ready(function(){
				
				
				
				
				$("#resetBtn").click(function() {
					$("#matchName").val("");
			
					var texture = new THREE.Texture();
					var tLoader = new THREE.ImageLoader(manager);
					var imageUrl = "resources/img/texture.png";
					tLoader.load(imageUrl, function(image) {
						texture.image = image;
						texture.needsUpdate = true;
						texture.wrapS = THREE.RepeatWrapping;
						texture.wrapT = THREE.RepeatWrapping;
						texture.repeat.set(1, 1);
					});
					
					underwearObject.traverse(function(child) {
						if(child instanceof THREE.Mesh) {
							child.material.map = texture;
						}
					});
					underwear = "";
			
					overcoatObject.traverse(function(child) {
						if(child instanceof THREE.Mesh) {
							child.material.map = texture;
						}
					});
					greatcoat = "";
			
					trouserObject.traverse(function(child) {
						if(child instanceof THREE.Mesh) {
							child.material.map = texture;
						}
					});
					trousers = imageUrl;
				});
				
				$("#saveMatchBtn").click(function() {
					var name = $("#matchName").val();
					if(name == null || name == "") {
						Materialize.toast('请输入名称!', 4000);
						return;
					}
			
					if(underwear == null || underwear == "") {
						Materialize.toast('选择内衣贴图!', 4000);
						return;
					}
			
					if(greatcoat == null || greatcoat == "") {
						Materialize.toast('请外套名称!', 4000);
						return;
					}
			
					if(trousers == null || trousers == "") {
						Materialize.toast('请裤装名称!', 4000);
						return;
					}
			
					$.ajax({
						type:'POST',
						url:'match/createMatch',
						headers: {
							"Authorization": authCode,
							"userId":userId
					    },
						data:JSON.stringify({
							'userId':userId,
							'name':name,
							'uwId':uwId,
							'underwear':underwear,
							'gcId':gcId,
							'greatcoat':greatcoat,
							'trId':trId,
							'trousers':trousers
						}),
						dataType:'json',
						contentType: "application/json",
						success:function(data){
							if (data.resultCode == 200){
								Materialize.toast('保存成功！', 4000);
							}
						}
					});
				});
				
			});
		</script>
	</body>

</html>