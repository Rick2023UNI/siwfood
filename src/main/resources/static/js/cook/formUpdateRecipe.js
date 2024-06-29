var i=0;
	  function newImageInputAndThumbnail() {
		  	i++;
			addImageInput();
			showRecipeImageThumbnail(this);
			refreshEvents();
		}
		
		function removeImageInput(event) {
			//Rimuove il nodo genitore dell'elemento che ha lanciato l'evento
			event.target.parentElement.parentElement.parentElement.remove();
		}
	//Inizializzazione evento per l'aggiunta di ulteriori input
	$(document).ready(function() {
		$('#fileImage0').change(newImageInputAndThumbnail);
		$('#ingredientSelector').change(checkIngredientShoHideForm);
	});
	
	function showRecipeImageThumbnail(fileInput) {
		file =fileInput.files[0];
		reader = new FileReader();
		
		reader.onload = function(e) {
			$('#thumbnail'+(i-1)).attr('src', e.target.result);
		};
		
		reader.readAsDataURL(file);
	} 
	
	function addImageInput() {
		var images=document.getElementById("images");
		//Creazione nuovo input e impostazione
		var newDiv=images.getElementsByClassName("imageUpload")[0].cloneNode(true);
		//Impostazione id del nuovo div contenente il nuovo input
		newDiv.id = 'image'+i.toString();
		
		//Cambiamento id nuovo input
		var fileName=newDiv.getElementsByTagName("input")[0];
		fileName.id='fileImage'+i;
		
		//Pulizia selezione input clonato
		fileName.value="";
		
		//Cambiamento for nuovo label
		var label=newDiv.getElementsByTagName("label")[0];
		label.setAttribute("for", "fileImage"+i);
		
		//Cambiamento id nuovo "a" dell'icona
		var label=newDiv.getElementsByTagName("a")[0];
		label.id="fileImageRemove"+i;
		
		//Cambiamento id nuovo thumbnail
		var image=newDiv.getElementsByTagName("img")[0];
		image.id="thumbnail"+i;
		image.setAttribute("src", "");
		
		var A=newDiv.getElementsByTagName("a")[0];
		A.classList.add("hidden");
		
		images.appendChild(newDiv);
		
		//Nasconde precedente input file
		var imageUploads=images.getElementsByClassName("imageUpload");
		document.getElementById("image"+(i-1)).getElementsByClassName("label")[0].style.display="none";
		
		//Reset nuovo input
		document.getElementById("image"+i).getElementsByClassName("label")[0].style.display="block";
		
		console.log(imageUploads[imageUploads.length-2].getElementsByTagName("label")[0]);
		if ($('#image'+(i-1)).length > 0) {
			//Aggiunta possibilità rimozione elemento precedente
			var newA=document.getElementById('fileImageRemove'+(i-1));
			newA.classList.remove("hidden");
			$('#fileImageRemove'+(i-1)).click(function(event) { removeImageInput(event); });
		}		
	}
	
	function refreshEvents() {
		//Rimozione vecchio evento dall'input precedente
		$('#fileImage'+(i-1)).unbind('change');
		//Aggiunta dell'evento al nuovo elemento
		$('#fileImage'+i).change(newImageInputAndThumbnail);
	}
	
		$(document).ready(function() {
		$('#fileImageIngredient').change(function () {
			showImageThumbnail(this);
		});
	});
	
	function showImageThumbnail(fileInput) {
		file =fileInput.files[0];
		reader = new FileReader();
		
		reader.onload = function(e) {
			$('#thumbnailIngredient').attr('src', e.target.result);
		};
		
		reader.readAsDataURL(file);
	}
	
	function checkIngredientShoHideForm() {
		var image=document.getElementById("new-image");
		var ingredient=document.getElementById("new-ingredient");
		var imageIngredient=document.getElementById("thumbnailIngredientSelector");
		if (document.getElementById("ingredientSelector").value=="-1") {
			image.style.display='';
			ingredient.style.display='';
			imageIngredient.style.display='none';
		} 
		else {
			image.style.display='none';
			ingredient.style.display='none';
			imageIngredient.style.display='';
			var selector=document.getElementById('ingredientSelector');
			var selected=selector.options[selector.selectedIndex];
			$('#thumbnailIngredientSelector').attr('src', selected.getAttribute("imagelink"));
		}
	}