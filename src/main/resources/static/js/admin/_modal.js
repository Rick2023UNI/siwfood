$(document).ready(function() {

// Quando l'utente clicca la x, chiude il modal
document.getElementsByClassName("close")[0].onclick = function() {
  document.getElementById("modal").style.display = "none";
}
document.getElementById("cancel").onclick = function() {
  document.getElementById("modal").style.display = "none";
}

// Quando l'utente clicca fuori dal modal, chiudilo
window.onclick = function(event) {
  if (event.target == document.getElementById("modal")) {
    document.getElementById("modal").style.display = "none";
  }
}
})

// Quando l'utente clicca sull'icona, apri il modal
function modalCheck(link) {
	document.getElementById("confirm").setAttribute("onclick", "location.href='"+link+"'");
  document.getElementById("modal").style.display = "block";
}