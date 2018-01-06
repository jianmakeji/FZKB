var underwear;
var uwId;
var greatcoat;
var gcId;
var trousers;
var trId;

var table = [
	"", "", "", 1, 1,
	"", "", "", 18, 1,
	"", "", "", 1, 2,
	"", "", "", 2, 2,
	"", "", "", 13, 2,
	"", "", "", 14, 2,
	"", "", "", 15, 2,
	"", "", "", 16, 2,
	"", "", "", 17, 2,
	"", "", "", 18, 2,
	"", "", "", 1, 3,
	"", "", "", 2, 3,
	"", "", "", 13, 3,
	"", "", "", 14, 3,
	"", "", "", 15, 3,
	"", "", "", 16, 3,
	"", "", "", 17, 3,
	"", "", "", 18, 3,
	"", "", "", 1, 4,
	"", "", "", 2, 4,
	"", "", "", 3, 4,
	"", "", "", 4, 4,
	"", "", "", 5, 4,
	"", "", "", 6, 4,
	"", "", "", 7, 4,
	"", "", "", 8, 4,
	"", "", "", 9, 4,
	"", "", "", 10, 4,
	"", "", "", 11, 4,
	"", "", "", 12, 4,
	"", "", "", 13, 4,
	"", "", "", 14, 4,
	"", "", "", 15, 4,
	"", "", "", 16, 4,
	"", "", "", 17, 4,
	"", "", "", 18, 4,
	"", "", "", 1, 5,
	"", "", "", 2, 5,
	"", "", "", 3, 5,
	"", "", "", 4, 5,
	"", "", "", 5, 5,
	"", "", "", 6, 5,
	"", "", "", 7, 5,
	"", "", "", 8, 5,
	"", "", "", 9, 5,
	"", "", "", 10, 5,
	"", "", "", 11, 5,
	"", "", "", 12, 5,
	"", "", "", 13, 5,
	"", "", "", 14, 5,
	"", "", "", 15, 5,
	"", "", "", 16, 5,
	"", "", "", 17, 5,
	"", "", "", 18, 5,
	"", "", "", 1, 6,
	"", "", "", 2, 6,
	"", "", "", 4, 9,
	"", "", "", 5, 9,
	"", "", "", 6, 9,
	"", "", "", 7, 9,
	"", "", "", 8, 9,
	"", "", "", 9, 9,
	"", "", "", 10, 9,
	"", "", "", 11, 9,
	"", "", "", 12, 9,
	"", "", "", 13, 9,
	"", "", "", 14, 9,
	"", "", "", 15, 9,
	"", "", "", 16, 9,
	"", "", "", 17, 9,
	"", "", "", 18, 9,
	"", "", "", 4, 6,
	"", "", "", 5, 6,
	"", "", "", 6, 6,
	"", "", "", 7, 6,
	"", "", "", 8, 6,
	"", "", "", 9, 6,
	"", "", "", 10, 6,
	"", "", "", 11, 6,
	"", "", "", 12, 6,
	"", "", "", 13, 6,
	"", "", "", 14, 6,
	"", "", "", 15, 6,
	"", "", "", 16, 6,
	"", "", "", 17, 6,
	"", "", "", 18, 6,
	"", "", "", 1, 7,
	"", "", "", 2, 7,
	"", "", "", 4, 10,
	"", "", "", 5, 10,
	"", "", "", 6, 10,
	"", "", "", 7, 10,
	"", "", "", 8, 10,
	"", "", "", 9, 10,
	"", "", "", 10, 10,
	"", "", "", 11, 10,
	"", "", "", 12, 10,
	"", "", "", 13, 10,
	"", "", "", 14, 10,
	"", "", "", 15, 10,
	"", "", "", 16, 10,
	"", "", "", 17, 10,
	"", "", "", 18, 10,
	"", "", "", 4, 7,
	"", "", "", 5, 7,
	"", "", "", 6, 7,
	"", "", "", 7, 7,
	"", "", "", 8, 7,
	"", "", "", 9, 7,
	"", "", "", 10, 7,
	"", "", "", 11, 7,
	"", "", "", 12, 7,
	"", "", "", 13, 7,
	"", "", "", 14, 7,
	"", "", "", 15, 7,
	"", "", "", 16, 7,
	"", "", "", 17, 7,
	"", "", "", 18, 7
];

var camera, scene, renderer;
var controls;
var objects = [];
var targets = {
	table: [],
	sphere: [],
	helix: [],
	grid: []
};

$.ajax({
	type:'GET',
	url:'getRandomMaterial',
	data:{count:118},
	dataType:'json',
	success:function(data){

		for (var i = 0; i < data.object.length; i++){
			table[5 * i] = data.object[i].imageUrl;
			table[5 * i + 1] = data.object[i].name;
			table[5 * i + 2] = data.object[i].id;
		}
		
		camera = new THREE.PerspectiveCamera(40, window.innerWidth * 0.4 / window.innerHeight, 1, 10000);
		camera.position.z = 3000;
		scene = new THREE.Scene();
		// table
		for(var i = 0; i < table.length; i += 5) {
			var element = document.createElement('div');
			element.className = 'element';
			element.style.backgroundColor = 'rgba(0,127,127,' + (Math.random() * 0.5 + 0.25) + ')';

			var symbol = document.createElement('div');

			var img = document.createElement('img');
			img.src = table[i];
			img.width = 120;
			img.height = 120;
			img.id= table[i+2];
			symbol.appendChild(img);
			element.appendChild(symbol);

			var details = document.createElement('div');
			details.className = 'details';
			details.innerHTML = table[i + 1] + '<br>' + table[i + 2];
			element.appendChild(details);
			var object = new THREE.CSS3DObject(element);
			object.position.x = Math.random() * 4000 - 2000;
			object.position.y = Math.random() * 4000 - 2000;
			object.position.z = Math.random() * 4000 - 2000;
			scene.add(object);
			objects.push(object);
			//
			var object = new THREE.Object3D();
			object.position.x = (table[i + 3] * 140) - 1330;
			object.position.y = -(table[i + 4] * 180) + 990;
			targets.table.push(object);
		}
		// sphere
		var vector = new THREE.Vector3();
		var spherical = new THREE.Spherical();
		for(var i = 0, l = objects.length; i < l; i++) {
			var phi = Math.acos(-1 + (2 * i) / l);
			var theta = Math.sqrt(l * Math.PI) * phi;
			var object = new THREE.Object3D();
			spherical.set(800, phi, theta);
			object.position.setFromSpherical(spherical);
			vector.copy(object.position).multiplyScalar(2);
			object.lookAt(vector);
			targets.sphere.push(object);
		}

		//
		renderer = new THREE.CSS3DRenderer();
		renderer.setSize(window.innerWidth * 0.4, window.innerHeight);
		renderer.domElement.style.position = 'absolute';
		document.getElementById('container').appendChild(renderer.domElement);
		//
		controls = new THREE.TrackballControls(camera, renderer.domElement);
		controls.rotateSpeed = 0.5;
		controls.minDistance = 500;
		controls.maxDistance = 6000;
		controls.addEventListener('change', render);

		transform(targets.sphere, 2000);
		//
		window.addEventListener('resize', onWindowResize, false);
		
		animate();
		
		$(".element").click(function() {

			var radioVal = $('input[name="clothingGroup"]:checked').val();
			var texture = new THREE.Texture();
			var tLoader = new THREE.ImageLoader(manager);

			var imageUrl = $(this).find("img").attr('src');
			var id = $(this).find("img").attr('id');
			console.log(imageUrl);
			console.log(id);
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
					underwear = imageUrl;
					uwId = id;
				} else if(radioVal == 1) { //外套
					overcoatObject.traverse(function(child) {
						if(child instanceof THREE.Mesh) {
							child.material.map = texture;
						}
					});
					greatcoat = imageUrl;
					gcId = id;
				} else if(radioVal == 2) { //裤装
					trouserObject.traverse(function(child) {
						if(child instanceof THREE.Mesh) {
							child.material.map = texture;
						}
					});
					trousers = imageUrl;
					trId = id;
				}
			});

		});
	},
});

function transform(targets, duration) {
	TWEEN.removeAll();
	for(var i = 0; i < objects.length; i++) {
		var object = objects[i];
		var target = targets[i];
		new TWEEN.Tween(object.position)
			.to({
				x: target.position.x,
				y: target.position.y,
				z: target.position.z
			}, Math.random() * duration + duration)
			.easing(TWEEN.Easing.Exponential.InOut)
			.start();
		new TWEEN.Tween(object.rotation)
			.to({
				x: target.rotation.x,
				y: target.rotation.y,
				z: target.rotation.z
			}, Math.random() * duration + duration)
			.easing(TWEEN.Easing.Exponential.InOut)
			.start();
	}
	new TWEEN.Tween(this)
		.to({}, duration * 2)
		.onUpdate(render)
		.start();
}

function onWindowResize() {
	camera.aspect = window.innerWidth * 0.4 / window.innerHeight;
	camera.updateProjectionMatrix();
	renderer.setSize(window.innerWidth * 0.4, window.innerHeight);
	render();
}

function animate() {
	requestAnimationFrame(animate);
	TWEEN.update();
	controls.update();
}

function render() {
	//renderer.setClearColor(0xdcdcdc);
	renderer.render(scene, camera);
}

$(document).ready(function() {
	
	$(window).resize(function() {
		location.reload()
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
			url:'/match/createMatch',
			data:{
				'userId':userId,
				'name':name,
				'uwId':uwId,
				'underwear':underwear,
				'gcId':gcId,
				'greatcoat':greatcoat,
				'trId':trId,
				'trousers':trousers
			},
			dataType:'json',
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