 var i=0;
	  function newImageInputAndThumbnail() {
		  	i++;
			addImageInput();
			showImageThumbnail(this);
			refreshEvents();
		}
		
		function removeImageInput(event) {
			//Rimuove il nodo genitore dell'elemento che ha lanciato l'evento
			event.target.parentElement.remove();
		}
	//Inizializzazione evento per l'aggiunta di ulteriori input
	$(document).ready(function() {
		$('#fileImage0').change(newImageInputAndThumbnail);
	});
	
	function showImageThumbnail(fileInput) {
		file =fileInput.files[0];
		reader = new FileReader();
		
		reader.onload = function(e) {
			$('#thumbnail').attr('src', e.target.result);
		};
		
		reader.readAsDataURL(file);
	} 
	
	function addImageInput() {
		//images.appendChild();
		var images=document.getElementById("images");
		//Creazione nuovo input e impostazione
		var newDiv=document.createElement("div");
		//Impostazione id del nuovo div contenente il nuovo input
		newDiv.id = 'image'+i.toString();
		
		//Aggiunta elementi alla pagina
		var fileName=document.getElementsByClassName("fileImage")[0].cloneNode(true);
		newDiv.appendChild(fileName);
		fileName.id='fileImage'+i;
		//Pulizia selezione input clonato
		fileName.value="";
		
		images.appendChild(newDiv);
		
		if ($('#image'+(i-1)).length > 0) {
			//Aggiunta possibilità rimozione elemento precedente
			var newA=document.createElement("a");
			newA.setAttribute("id", 'fileImageRemove'+(i-1));
			newA.innerHTML="Rimuovi";
			document.getElementById("image"+(i-1)).appendChild(newA);
			$('#fileImageRemove'+(i-1)).click(function(event) { removeImageInput(event); });
		}		
	}
	
	function refreshEvents() {
		//Rimozione vecchio evento dall'input precedente
		$('#fileImage'+(i-1)).unbind('change');
		//Aggiunta dell'evento al nuovo elemento
		$('#fileImage'+i).change(newImageInputAndThumbnail);
	}