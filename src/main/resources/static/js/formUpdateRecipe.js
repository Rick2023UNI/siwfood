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
			$('#fileImage0').on("change",function() {
	    console.log("change fired");
	    var file = $('#fileImage0')[0].files[0].name;
	    console.log(file);
	    document.getElementById("image0").getElementsByClassName("label")[0].innerHTML=file;
	  });
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
		var images=document.getElementById("images");
		//Creazione nuovo input e impostazione
		var newDiv=document.createElement("div");
		//Impostazione id del nuovo div contenente il nuovo input
		newDiv.id = 'image'+i.toString();
		
		//Aggiunta input al nuovo div
		var fileName=document.getElementsByClassName("fileImage")[0].cloneNode(true);
		newDiv.appendChild(fileName);
		fileName.id='fileImage'+i;
		
		//Pulizia selezione input clonato
		fileName.value="";
		
		//Nuovo label
		var label=document.getElementsByClassName("label")[0].cloneNode(true);
		newDiv.appendChild(label);
		label.setAttribute("for", "fileImage"+i);
		
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