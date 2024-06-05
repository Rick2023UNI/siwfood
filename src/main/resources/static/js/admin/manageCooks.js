$(document).ready(function() {

// When the user clicks on <span> (x), close the modal
document.getElementsByClassName("close")[0].onclick = function() {
  document.getElementById("modal").style.display = "none";
}
document.getElementById("cancel").onclick = function() {
  document.getElementById("modal").style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == document.getElementById("modal")) {
    document.getElementById("modal").style.display = "none";
  }
}
})

// When the user clicks the button, open the modal 
function modalCheck(link) {
	document.getElementById("confirm").setAttribute("onclick", "location.href='"+link+"'");
  document.getElementById("modal").style.display = "block";
}