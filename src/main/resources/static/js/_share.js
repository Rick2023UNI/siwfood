function share(event) {
	var url = document.URL;
	var linkToShare = window.location.href;
	var text = "";
	//Scelta descrizione contenuto
	if (url.includes("cook")) {
		text = "Ricette del cuoco ";
	} else {
		text = "Ricetta ";
	}

	text += document.getElementsByTagName("h1")[0].textContent + " ";
	var action = event.currentTarget.id;
	var link = "";
	if (action == "facebook") {
		link = 'https://www.facebook.com/sharer/sharer.php?u=' + linkToShare;
	}
	else if (action == "whatsapp") {
		link = 'https://wa.me/?text=' + text + " " + linkToShare;
	}
	else if (action == "twitter") {
		link = "https://twitter.com/intent/tweet?url=" + linkToShare + "&text=" + text;
	}
	else if (action == "reddit") {
		link = "http://www.reddit.com/submit?url=" + linkToShare;
	}
	else if (action == "linkedin") {
		link = "https://www.linkedin.com/shareArticle?mini=true&amp;url=" + linkToShare + "&amp;title=" + text + "&summary=" + text + linkToShare + ";source=";
	}
	else if (action == "email") {
		link = "mailto:?subject=" + text + "&body=" + text + linkToShare;
	}
	window.open(link, 'pop-up', 'left=20,top=20,width=500,height=500,toolbar=1,resizable=0');
}