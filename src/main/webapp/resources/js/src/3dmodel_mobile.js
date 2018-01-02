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
		camera = new THREE.PerspectiveCamera(45, window.innerWidth * 0.9 / window.innerHeight, 1, 2000);
		scene = new THREE.Scene();
		//scene.fog = THREE.FogExp2(0xffffff,0.02);
		// grid
		var gridHelper = new THREE.GridHelper(45, 45, 0x9e9e9e, 0x9e9e9e);
		gridHelper.position.set(0, 0, 0);
		gridHelper.receiveShadow = true;
		scene.add(gridHelper);
		// stats
		stats = new Stats();
		container.appendChild(stats.dom);

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
		
		loader.load('models/fbx/body4.fbx', function(object) {

			scene.add(object);
			//object.castShadow = true;
			//加载裤装模型
			loader.load('models/fbx/kuzi.fbx', function(object) {
				trouserObject = object; //裤装
				scene.add(object);
				trouserObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = texture;
					}
				});
				
				
			}, onProgress, onError);
			
			//加载内衣模型
			loader.load('models/fbx/neida.fbx', function(object) {
				underwearObject = object;
				scene.add(object);
				underwearObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = texture;
					}
				});
			}, onProgress, onError);
	
			//加载外套模型
			loader.load('models/fbx/xizhuang.fbx', function(object) {
				overcoatObject = object; //外套
				scene.add(object);
				overcoatObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = texture;
					}
				});
			}, onProgress, onError);

		}, onProgress, onError);

		renderer = new THREE.WebGLRenderer();
		renderer.setPixelRatio(window.devicePixelRatio);
		renderer.setSize(window.innerWidth * 0.9, window.innerHeight - 230);
		container.appendChild(renderer.domElement);
		// controls, camera
		controls = new THREE.OrbitControls(camera, renderer.domElement);
		controls.target.set(0, 12, 0);
		camera.position.set(2, 18, 28);
		controls.update();

		//window.addEventListener('resize', onWindowResize, false);

		light = new THREE.HemisphereLight(0xffffff, 0x333333, 1.0);
		light.position.set(0, 1, 3);
		scene.add(light);
		
		light = new THREE.DirectionalLight(0xffffff, 1.0);
		light.position.set(0, 1, -3);
		scene.add(light);
		light.castShadow = true
		
		animate();
	}

	function onWindowResize() {
		camera.aspect = window.innerWidth * 0.6 / window.innerHeight;
		camera.updateProjectionMatrix();
		renderer.setSize(window.innerWidth * 0.6, window.innerHeight);
	}
	//
	function animate() {
		requestAnimationFrame(animate);
		if(mixers.length > 0) {
			for(var i = 0; i < mixers.length; i++) {
				mixers[i].update(clock.getDelta());
			}
		}
		stats.update();
		render();
	}

	function render() {
		renderer.setClearColor(0xdcdcdc);
		//renderer.shadowMap.enabled = true;
		renderer.render(scene, camera);
	}
});