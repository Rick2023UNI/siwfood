var i=0;

//Inizializzazione evento per l'aggiunta di ulteriori selector e la loro rimozione
	$(document).ready(function() {
		$('#ingredientRemove0').click(function(event) { removeImageInput(event); });
		$('#newIngredient').click(function() { newIngredientSelect(); });
	});

function newIngredientSelect() {
	i++;
	var newSelector=document.getElementById("selectors").getElementsByTagName("div")[0].cloneNode(true);
	newSelector.style.display="block";
	newSelector.id = "ingredient"+i;
	newSelector.getElementsByTagName("a")[0].id="ingredientRemove"+i;
	newSelector.getElementsByTagName("select")[0].id="ingredientSelector"+i;
	newSelector.getElementsByTagName("select")[0].style.display="block";
	
	var selectors=document.getElementById("selectors");
	selectors.appendChild(newSelector);
	$('#ingredientRemove'+i).click(function(event) { removeImageInput(event); });
}
function removeImageInput(event) {
	event.currentTarget.parentElement.remove();
	
}