var underwear;
var uwId;
var greatcoat;
var gcId;
var trousers;
var trId;

var camera;
var renderer;
var scene;
var controls;
var fov = 45;
var near = 1; //最小范围
var far = 1000; //最大范围
var interaction;

$(document).ready(function() {
	
	$(window).resize(function() {
		location.reload()
	});
	
	function init() {
		// create a scene, that will hold all our elements such as objects, cameras and lights.
		scene = new THREE.Scene();
		// create a camera, which defines where we're looking at.
		camera = new THREE.PerspectiveCamera(fov, window.innerWidth  * 0.5 / window.innerHeight, near, far);

		// create a render and set the size
		renderer = new THREE.WebGLRenderer();
		
		interaction = new THREE.Interaction(renderer, scene, camera);
		//renderer.setClearColorHex();
		renderer.setClearColor(new THREE.Color(0xEEEEEE));
		renderer.setSize(window.innerWidth* 0.5, window.innerHeight);

		var xGeometry = new THREE.Geometry();
	    var xP1 = new THREE.Vector3(-20,0,0);
	    var xP2 = new THREE.Vector3(20,0,0);
	    xGeometry.vertices.push(xP1,xP2); 
	    var xMaterial=new THREE.LineBasicMaterial({
	        color:0x00796b
	    });
	    var xLine = new THREE.Line(xGeometry,xMaterial);
	    scene.add(xLine);
	    
	    var yGeometry = new THREE.Geometry();
	    var yP1 = new THREE.Vector3(0,-20,0);
	    var yP2 = new THREE.Vector3(0,20,0);
	    yGeometry.vertices.push(yP1,yP2); 
	    var yMaterial=new THREE.LineBasicMaterial({
	        color:0x2e7d32
	    });
	    var yLine = new THREE.Line(yGeometry,yMaterial);
	    scene.add(yLine);
	    
	    var zGeometry = new THREE.Geometry();
	    var zP1 = new THREE.Vector3(0,0,-20);
	    var zP2 = new THREE.Vector3(0,0,20);
	    zGeometry.vertices.push(zP1,zP2); 
	    var zMaterial=new THREE.LineBasicMaterial({
	        color:0xe65100 
	    });
	    var zLine = new THREE.Line(zGeometry,zMaterial);
	    scene.add(zLine);
	        
	    addText('商务',-18,0,0);
	    addText('休闲',33,0,0);
	    
	    addText('单色',0,-18,0);
	    addText('杂色',5,18,0);
	    
	    addText('冷冬',0,0,-18);
	    addText('暖夏',5,0,18);
	    
		// position and point the camera to the center of the scene
		camera.position.x = 10;
		camera.position.y = 50;
		camera.position.z = 20;
		camera.lookAt(scene.position);
		// add the output of the renderer to the html element
		document.getElementById("container").appendChild(renderer.domElement);
		// render the scene
		
		controls = new THREE.OrbitControls(camera, renderer.domElement);
		controls.addEventListener('change', render); // remove when using animation loop
		controls.enableZoom = true;
		
		render();
		
		$.ajax({
			type:'GET',
			url:'getRandomMaterial',
			data:{count:118},
			dataType:'json',
			success:function(data){
				
				for (var i = 0; i < data.object.length; i++){
					
					addMaterials(data.object[i].imageUrl,data.object[i].id, 
							data.object[i].style1*5,data.object[i].style2*5,data.object[i].style3*5);
				}
				
				setTimeout(function(){
					render();
				},5000);
			},
		});
		
		
	}

	function render() {
		renderer.render(scene, camera);
	}

	function addText(textValue,x,y,z){
		let fontface = "Helvetica";
	    let fontsize = 18;
	    let scale = window.devicePixelRatio;
	    let canvas = document.createElement('canvas');
	    let context = canvas.getContext('2d');
	    context.scale(scale,scale);
	    context.font = "light " + fontsize + "px " + fontface;
	    // background color
	    context.fillStyle = "#4a148c";
	    context.fill();
	    // text align
	    context.textAlign="center";
	    context.fillText(textValue, 25, 20);
	    
	    // canvas contents will be used for a texture
	    let texture = new THREE.Texture(canvas) 
	    texture.needsUpdate = true;
	    let spriteMaterial = new THREE.SpriteMaterial({ 
	        map: texture, 
	        color: 0xffffff
	    });
	    let sprite = new THREE.Sprite( spriteMaterial );
	    sprite.scale.set(20,10,1);
	    sprite.position.set(x,y,z);
	    scene.add(sprite);	
	}

	function addMaterials(imageAddress,id,x,y,z){
		var imageUrl = "image?imgPath="+imageAddress;
		let textureLoader = new THREE.TextureLoader();
	    let textureMap = textureLoader.load(imageUrl);

	    textureMap.needsUpdate = true;
	    let material = new THREE.SpriteMaterial({ 
	        map: textureMap, 
	        color: 0xffffff,
	        fog: true
	    });
	    let sprite = new THREE.Sprite( material )
	    sprite.scale.set(3,3,1);
	    sprite.position.set(x,y,z);
	    
	    sprite.cursor = 'pointer';
		sprite.on('click', function(ev) {
			var radioVal = $('input[name="clothingGroup"]:checked').val();
			var texture = new THREE.Texture();
			var tLoader = new THREE.ImageLoader(manager);
			
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
					underwear = imageAddress;
					uwId = id;
				} else if(radioVal == 1) { //外套
					overcoatObject.traverse(function(child) {
						if(child instanceof THREE.Mesh) {
							child.material.map = texture;
						}
					});
					greatcoat = imageAddress;
					gcId = id;
				} else if(radioVal == 2) { //裤装
					trouserObject.traverse(function(child) {
						if(child instanceof THREE.Mesh) {
							child.material.map = texture;
						}
					});
					trousers = imageAddress;
					trId = id;
				}
			});
		});

	    scene.add(sprite);
	    
	}

	function onResize(){
		camera.aspect = window.innerWidth * 0.5 / window.innerHeight;
		camera.updateProjectionMatrix();
		renderer.setSize( window.innerWidth * 0.5, window.innerHeight);
		renderer.render(scene, camera);
	}
	window.onload = init;
	window.addEventListener('resize',onResize, false);
	
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

	$("#resetBtn").click(function() {

		$("#matchName").val("");

		var texture = new THREE.Texture();
		var tLoader = new THREE.ImageLoader(manager);
		var imageUrl = "img/5_9.jpg";
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
});