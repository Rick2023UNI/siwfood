var i=0;
	  function newImageInputAndThumbnail() {
		  	i++;
			addImageInput();
			showRecipeImageThumbnail(this);
			refreshEvents();
		}
		
		function removeImageInput(event) {
			console.log(event.target.parentElement.parentElement);
			//Rimuove il nodo genitore dell'elemento che ha lanciato l'evento
			event.target.parentElement.parentElement.remove();
		}
	//Inizializzazione evento per l'aggiunta di ulteriori input
	$(document).ready(function() {
		$('#fileImage0').change(newImageInputAndThumbnail);
			$('#fileImage0').on("change",function() {
	    console.log("change fired");
	    var file = $('#fileImage0')[0].files[0].name;
	    console.log(file);
	    document.getElementById("image0").getElementsByClassName("label")[0].innerHTML=file;
	  });
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
		A.classList.add("hidden")
		
		images.appendChild(newDiv);
		
		//Nasconde precedente input file
		var imageUploads=images.getElementsByClassName("imageUpload");
		imageUploads[imageUploads.length-2].getElementsByTagName("label")[0].style.display="none";
		
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