var underwearObject; //内搭
var overcoatObject; //外套
var trouserObject; //裤装

var manager;

$(document).ready(function() {
	if(!Detector.webgl) Detector.addGetWebGLMessage();
	var container, stats, controls;
	var camera, scene, renderer, light;
	var clock = new THREE.Clock();
	var mixers = [];
	init();

	function init() {
		container = document.createElement('div');
		$("#modelContainer").append(container);
		camera = new THREE.PerspectiveCamera(45, window.innerWidth * 0.5 / window.innerHeight, 1, 2000);
		scene = new THREE.Scene();
		//scene.fog = THREE.FogExp2(0xffffff,0.02);
		// grid
		var gridHelper = new THREE.GridHelper(45, 45, 0x9e9e9e, 0x9e9e9e);
		gridHelper.position.set(0, 0, 0);
		gridHelper.receiveShadow = true;
		scene.add(gridHelper);
		// stats
		//stats = new Stats();
		//container.appendChild(stats.dom);

		// model
		manager = new THREE.LoadingManager();
		manager.onProgress = function(item, loaded, total) {
			console.log(item, loaded, total);
		};
		var onProgress = function(xhr) {
			if(xhr.lengthComputable) {
				var percentComplete = xhr.loaded / xhr.total * 100;
				console.log(Math.round(percentComplete, 2) + '% downloaded');
			}
		};
		var onError = function(xhr) {
			console.error(xhr);
		};

		var loader = new THREE.FBXLoader(manager);
		var trouserTexture = new THREE.Texture();
		var underwearTexture = new THREE.Texture();
		var overcoatTexture = new THREE.Texture();
		
		var tLoader = new THREE.ImageLoader(manager);

		tLoader.load("image?imgPath="+trouserImageUrl, function(image) {
			trouserTexture.image = image;
			trouserTexture.needsUpdate = true;
			trouserTexture.wrapS = THREE.RepeatWrapping;
			trouserTexture.wrapT = THREE.RepeatWrapping;
			trouserTexture.repeat.set(1, 1);
		});
		
		tLoader.load("image?imgPath="+underwearImageUrl, function(image) {
			underwearTexture.image = image;
			underwearTexture.needsUpdate = true;
			underwearTexture.wrapS = THREE.RepeatWrapping;
			underwearTexture.wrapT = THREE.RepeatWrapping;
			underwearTexture.repeat.set(1, 1);
		});
		
		tLoader.load("image?imgPath="+overcoatImageUrl, function(image) {
			overcoatTexture.image = image;
			overcoatTexture.needsUpdate = true;
			overcoatTexture.wrapS = THREE.RepeatWrapping;
			overcoatTexture.wrapT = THREE.RepeatWrapping;
			overcoatTexture.repeat.set(1, 1);
		});
		
		loader.load('resources/models/fbx/body4.fbx', function(object) {

			scene.add(object);
			//object.castShadow = true;
			//加载裤装模型
			loader.load('resources/models/fbx/kuzi.fbx', function(object) {
				trouserObject = object; //裤装
				scene.add(object);
				trouserObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = trouserTexture;
					}
				});
				
				
			}, onProgress, onError);
			
			//加载内衣模型
			loader.load('resources/models/fbx/neida.fbx', function(object) {
				underwearObject = object;
				scene.add(object);
				underwearObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = underwearTexture;
					}
				});
			}, onProgress, onError);
	
			//加载外套模型
			loader.load('resources/models/fbx/xizhuang.fbx', function(object) {
				overcoatObject = object; //外套
				scene.add(object);
				overcoatObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = overcoatTexture;
					}
				});
			}, onProgress, onError);

		}, onProgress, onError);

		renderer = new THREE.WebGLRenderer();
		renderer.setPixelRatio(window.devicePixelRatio);
		renderer.setSize(window.innerWidth * 0.5, window.innerHeight - 20);
		container.appendChild(renderer.domElement);
		// controls, camera
		controls = new THREE.OrbitControls(camera, renderer.domElement);
		controls.target.set(0, 12, 0);
		camera.position.set(2, 18, 28);
		controls.update();

		window.addEventListener('resize', onWindowResize, false);
		/*
		light = new THREE.HemisphereLight(0xffffff, 0x333333, 1.0);
		light.position.set(0, 1, 3);
		scene.add(light);
		
		light = new THREE.DirectionalLight(0xffffff, 1.0);
		light.position.set(0, 1, -3);
		scene.add(light);
		light.castShadow = true
		*/
		light = new THREE.HemisphereLight(0xffffff, 0x333333, 1.0);
		light.position.set(0, 1, 3);
		scene.add(light);
		
		light = new THREE.DirectionalLight(0xffffff, 2.0);
		light.position.set(0, -1, -5);
		scene.add(light);
		
		light = new THREE.DirectionalLight(0xffffff, 1.0);
		light.position.set(0, 3, 1);
		scene.add(light);
		
		animate();
	}

	function onWindowResize() {
		camera.aspect = window.innerWidth * 0.5/ window.innerHeight;
		camera.updateProjectionMatrix();
		renderer.setSize(window.innerWidth * 0.5, window.innerHeight);
	}
	//
	function animate() {
		requestAnimationFrame(animate);
		if(mixers.length > 0) {
			for(var i = 0; i < mixers.length; i++) {
				mixers[i].update(clock.getDelta());
			}
		}
		//stats.update();
		render();
	}

	function render() {
		renderer.setClearColor(0xdcdcdc);
		//renderer.shadowMap.enabled = true;
		renderer.render(scene, camera);
	}
});