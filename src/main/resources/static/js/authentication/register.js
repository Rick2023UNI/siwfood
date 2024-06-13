	  function newImageInputAndThumbnail() {
			showImageThumbnail(this);
		}
	
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
		document.getElementById("thumbnail").style.display = "block";
		reader.readAsDataURL(file);
	}