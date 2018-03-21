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
		renderer.setClearColor(new THREE.Color(0xDCDCDC));
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
	        
	    addText('商务',-13,0,0,"#4a148c");
	    addText('休闲',28,0,0,"#ea80fc");
	    
	    addText('单色',8,-18,0,"#1e88e5");
	    addText('杂色',8,18,0,"#9575cd");
	    
	    addText('冷冬',8,0,-18,"#4fc3f7");
	    addText('暖夏',8,0,18,"#00796b");
	    
		// position and point the camera to the center of the scene
		camera.position.x = 10;
		camera.position.y = 50;
		camera.position.z = 20;
		camera.lookAt(scene.position);
		// add the output of the renderer to the html element
		document.getElementById("container").appendChild(renderer.domElement);
		// render the scene
		
		renderer.render(scene, camera);
		
		
		setTimeout(function(){
			renderer.render(scene, camera);
		},1000);
		

		controls = new THREE.OrbitControls(camera, renderer.domElement);
		controls.addEventListener('change', render); // remove when using animation loop
		controls.enableZoom = true;

		addMaterials(trouserImageUrl,trouserID,trouserX*5,trouserY*5,trouserZ*5);
		addMaterials(underwearImageUrl,underwearID,underwearX*5,underwearY*5,underwearZ*5);
		addMaterials(overcoatImageUrl,overcoatID,overcoatX*5,overcoatY*5,overcoatZ*5);
	}

	function render() {
		renderer.render(scene, camera);
	}

	function addText(textValue,x,y,z,color){
		let fontface = "Helvetica";
	    let fontsize = 18;
	    let scale = window.devicePixelRatio;
	    let canvas = document.createElement('canvas');
	    let context = canvas.getContext('2d');
	    context.scale(scale,scale);
	    context.font = "light " + fontsize + "px " + fontface;
	    // background color
	    context.fillStyle = color;
	    context.fillRect(0,0,50,25);
	    // text align
	    context.textAlign="center";
	    context.fillStyle = "#FFFFFF";
	    context.fillText(textValue, 25, 15);
	    
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

});