	  function newImageInputAndThumbnail() {
			showImageThumbnail(this);
		}
		
		function removeImageInput(event) {
			//Rimuove il nodo genitore dell'elemento che ha lanciato l'evento
			event.target.parentElement.remove();
		}
	//Inizializzazione evento per l'aggiunta di ulteriori input
	$(document).ready(function() {
		$('#fileImage').change(newImageInputAndThumbnail);
			$('#fileImage').on("change",function() {
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