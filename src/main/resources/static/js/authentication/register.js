function newImageInputAndThumbnail() {
	showImageThumbnail(this);
}

$(document).ready(function() {
	$('#fileImage').change(newImageInputAndThumbnail);
	$('#fileImage').on("change", function() {
	});
});

function showImageThumbnail(fileInput) {
	file = fileInput.files[0];
	reader = new FileReader();

	reader.onload = function(e) {
		$('#thumbnail').attr('src', e.target.result);
	};
	document.getElementById("thumbnail").style.display = "block";
	reader.readAsDataURL(file);
}


$(document).ready(function() {
	$('#show').click(showPassword);
});

function showPassword() {
	document.getElementById("show").style.display = 'none';
	document.getElementById("hide").style.display = 'block';

	document.getElementById("password").type = "text";

	$('#show').off("click", showPassword);
	$('#hide').click(hidePassword);
}

function hidePassword() {
	document.getElementById("show").style.display = 'block';
	document.getElementById("hide").style.display = 'none';

	document.getElementById("password").type = "password";

	$('#show').off("click", showPassword);
	$('#show').click(showPassword);
}