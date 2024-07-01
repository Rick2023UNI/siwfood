$(document).ready(function() {
	$('#show').click(showPassword);
});

function showPassword() {
	document.getElementById("show").style.display='none';
	document.getElementById("hide").style.display='block';
	
	document.getElementById("password").type="text";
	
	$('#show').off("click", showPassword);
	$('#hide').click(hidePassword);
}

function hidePassword() {
	document.getElementById("show").style.display='block';
	document.getElementById("hide").style.display='none';
	
	document.getElementById("password").type="password";
	
	$('#show').off("click", showPassword);
	$('#show').click(showPassword);
}