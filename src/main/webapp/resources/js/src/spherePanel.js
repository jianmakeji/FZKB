var underwear;
var greatcoat;
var trousers;

$(document).ready(function() {
	var table = [
		"1_1.jpg", "Hydrogen", "1.00794", 1, 1,
		"1_2.jpg", "Helium", "4.002602", 18, 1,
		"1_3.jpg", "Lithium", "6.941", 1, 2,
		"1_4.jpg", "Beryllium", "9.012182", 2, 2,
		"1_5.jpg", "Boron", "10.811", 13, 2,
		"1_6.jpg", "Carbon", "12.0107", 14, 2,
		"1_7.jpg", "Nitrogen", "14.0067", 15, 2,
		"1_8.jpg", "Oxygen", "15.9994", 16, 2,
		"1_9.jpg", "Fluorine", "18.9984032", 17, 2,
		"1_10.jpg", "Neon", "20.1797", 18, 2,
		"2_1.jpg", "Sodium", "22.98976...", 1, 3,
		"2_2.jpg", "Magnesium", "24.305", 2, 3,
		"2_3.jpg", "Aluminium", "26.9815386", 13, 3,
		"2_4.jpg", "Silicon", "28.0855", 14, 3,
		"2_5.jpg", "Phosphorus", "30.973762", 15, 3,
		"2_6.jpg", "Sulfur", "32.065", 16, 3,
		"2_7.jpg", "Chlorine", "35.453", 17, 3,
		"2_8.jpg", "Argon", "39.948", 18, 3,
		"2_9.jpg", "Potassium", "39.948", 1, 4,
		"3_1.jpg", "Calcium", "40.078", 2, 4,
		"3_2.jpg", "Scandium", "44.955912", 3, 4,
		"3_3.jpg", "Titanium", "47.867", 4, 4,
		"3_4.jpg", "Vanadium", "50.9415", 5, 4,
		"3_5.jpg", "Chromium", "51.9961", 6, 4,
		"3_6.jpg", "Manganese", "54.938045", 7, 4,
		"3_7.jpg", "Iron", "55.845", 8, 4,
		"3_8.jpg", "Cobalt", "58.933195", 9, 4,
		"3_9.jpg", "Nickel", "58.6934", 10, 4,
		"4_1.jpg", "Copper", "63.546", 11, 4,
		"4_2.jpg", "Zinc", "65.38", 12, 4,
		"4_3.jpg", "Gallium", "69.723", 13, 4,
		"4_4.jpg", "Germanium", "72.63", 14, 4,
		"4_5.jpg", "Arsenic", "74.9216", 15, 4,
		"4_6.jpg", "Selenium", "78.96", 16, 4,
		"4_7.jpg", "Bromine", "79.904", 17, 4,
		"4_8.jpg", "Krypton", "83.798", 18, 4,
		"4_9.jpg", "Rubidium", "85.4678", 1, 5,
		"5_1.jpg", "Strontium", "87.62", 2, 5,
		"5_2.jpg", "Yttrium", "88.90585", 3, 5,
		"5_3.jpg", "Zirconium", "91.224", 4, 5,
		"5_4.jpg", "Niobium", "92.90628", 5, 5,
		"5_5.jpg", "Molybdenum", "95.96", 6, 5,
		"5_6.jpg", "Technetium", "(98)", 7, 5,
		"5_7.jpg", "Ruthenium", "101.07", 8, 5,
		"5_8.jpg", "Rhodium", "102.9055", 9, 5,
		"5_10.jpg", "Palladium", "106.42", 10, 5,
		"1_10.jpg", "Silver", "107.8682", 11, 5,
		"5_1.jpg", "Cadmium", "112.411", 12, 5,
		"5_2.jpg", "Indium", "114.818", 13, 5,
		"1_1.jpg", "Tin", "118.71", 14, 5,
		"5_3.jpg", "Antimony", "121.76", 15, 5,
		"2_1.jpg", "Tellurium", "127.6", 16, 5,
		"3_1.jpg", "Iodine", "126.90447", 17, 5,
		"4_1.jpg", "Xenon", "131.293", 18, 5,
		"5_1.jpg", "Caesium", "132.9054", 1, 6,
		"5_1.jpg", "Barium", "132.9054", 2, 6,
		"5_1.jpg", "Lanthanum", "138.90547", 4, 9,
		"5_1.jpg", "Cerium", "140.116", 5, 9,
		"3_9.jpg", "Praseodymium", "140.90765", 6, 9,
		"3_8.jpg", "Neodymium", "144.242", 7, 9,
		"3_7.jpg", "Promethium", "(145)", 8, 9,
		"3_6.jpg", "Samarium", "150.36", 9, 9,
		"3_5.jpg", "Europium", "151.964", 10, 9,
		"3_4.jpg", "Gadolinium", "157.25", 11, 9,
		"3_3.jpg", "Terbium", "158.92535", 12, 9,
		"3_2.jpg", "Dysprosium", "162.5", 13, 9,
		"3_1.jpg", "Holmium", "164.93032", 14, 9,
		"5_1.jpg", "Erbium", "167.259", 15, 9,
		"5_1.jpg", "Thulium", "168.93421", 16, 9,
		"5_1.jpg", "Ytterbium", "173.054", 17, 9,
		"5_1.jpg", "Lutetium", "174.9668", 18, 9,
		"5_1.jpg", "Hafnium", "178.49", 4, 6,
		"5_1.jpg", "Tantalum", "180.94788", 5, 6,
		"1_1.jpg", "Tungsten", "183.84", 6, 6,
		"2_1.jpg", "Rhenium", "186.207", 7, 6,
		"3_1.jpg", "Osmium", "190.23", 8, 6,
		"4_1.jpg", "Iridium", "192.217", 9, 6,
		"5_1.jpg", "Platinum", "195.084", 10, 6,
		"1_3.jpg", "Gold", "196.966569", 11, 6,
		"5_5.jpg", "Mercury", "200.59", 12, 6,
		"5_6.jpg", "Thallium", "204.3833", 13, 6,
		"5_7.jpg", "Lead", "207.2", 14, 6,
		"5_8.jpg", "Bismuth", "208.9804", 15, 6,
		"5_9.jpg", "Polonium", "(209)", 16, 6,
		"5_1.jpg", "Astatine", "(210)", 17, 6,
		"5_1.jpg", "Radon", "(222)", 18, 6,
		"5_1.jpg", "Francium", "(223)", 1, 7,
		"3_1.jpg", "Radium", "(226)", 2, 7,
		"3_2.jpg", "Actinium", "(227)", 4, 10,
		"3_3.jpg", "Thorium", "232.03806", 5, 10,
		"3_4.jpg", "Protactinium", "231.0588", 6, 10,
		"3_5.jpg", "Uranium", "238.02891", 7, 10,
		"3_6.jpg", "Neptunium", "(237)", 8, 10,
		"4_3.jpg", "Plutonium", "(244)", 9, 10,
		"4_4.jpg", "Americium", "(243)", 10, 10,
		"4_5.jpg", "Curium", "(247)", 11, 10,
		"4_6.jpg", "Berkelium", "(247)", 12, 10,
		"4_7.jpg", "Californium", "(251)", 13, 10,
		"4_8.jpg", "Einstenium", "(252)", 14, 10,
		"5_1.jpg", "Fermium", "(257)", 15, 10,
		"5_1.jpg", "Mendelevium", "(258)", 16, 10,
		"5_1.jpg", "Nobelium", "(259)", 17, 10,
		"2_1.jpg", "Lawrencium", "(262)", 18, 10,
		"2_2.jpg", "Rutherfordium", "(267)", 4, 7,
		"2_3.jpg", "Dubnium", "(268)", 5, 7,
		"2_4.jpg", "Seaborgium", "(271)", 6, 7,
		"2_5.jpg", "Bohrium", "(272)", 7, 7,
		"2_6.jpg", "Hassium", "(270)", 8, 7,
		"2_7.jpg", "Meitnerium", "(276)", 9, 7,
		"5_8.jpg", "Darmstadium", "(281)", 10, 7,
		"5_9.jpg", "Roentgenium", "(280)", 11, 7,
		"5_1.jpg", "Copernicium", "(285)", 12, 7,
		"1_1.jpg", "Nihonium", "(286)", 13, 7,
		"1_2.jpg", "Flerovium", "(289)", 14, 7,
		"1_3.jpg", "Moscovium", "(290)", 15, 7,
		"1_4.jpg", "Livermorium", "(293)", 16, 7,
		"1_5.jpg", "Tennessine", "(294)", 17, 7,
		"1_6.jpg", "Oganesson", "(294)", 18, 7
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
	init();
	animate();

	function init() {
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
			img.src = "img/" + table[i];
			img.width = 120;
			img.height = 120;
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
	}

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

	$(window).resize(function() {
		location.reload()
	});

	$(".element").click(function() {

		var radioVal = $('input[name="clothingGroup"]:checked').val();
		var texture = new THREE.Texture();
		var tLoader = new THREE.ImageLoader(manager);

		var imageUrl = $(this).find("img").attr('src');
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
			} else if(radioVal == 1) { //外套
				overcoatObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = texture;
					}
				});
				greatcoat = imageUrl;
			} else if(radioVal == 2) { //裤装
				trouserObject.traverse(function(child) {
					if(child instanceof THREE.Mesh) {
						child.material.map = texture;
					}
				});
				trousers = imageUrl;
			}
		});

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