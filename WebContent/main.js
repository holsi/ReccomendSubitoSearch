var objects;
var index2region = ["lazio","toscana","umbria","marche","piemonte","lombardia","campania","sardegna","emilia-romagna","trentino-alto-adige","valle-d-aosta","friuli-venezia-giulia","liguria","calabria","abruzzo","sicilia","puglia"]
var province = []
var region2province = [["lazio",["roma","latina","frosinone","rieti","viterbo"]],
                       ["toscana",["firenze","pisa","livorno","prato","siena","lucca","pistoia","massa-carrara","arezzo","grosseto"]],
                       ["abruzzo",["teramo","pescara","chieti","l-aquila"]],
                       ["molise",["isrnia","campobasso"]],
                       ["campania",["napoli","caserta","benevento","salerno","avellino"]],
                       ["marche",["ancona","pesaro-urbino","macerata","fermo","ascoli-piceno"]],
                       ["umbria",["perugia","terni"]],
                       ["basilicata",["matera","potenza"]],
                       ["val-d-aosta",[]],
                       ["lombardia",["milano","monza","lodi","cremona","sondrio","varese","brescia","bergamo","como","pavia","lecco"]],
                       ["piemonte",["alessandria","asti","biella","cuneo","torino","novara","vercelli","verbano-cusia-ossola"]],
                       ["ligura",["genova","savona","imperia","la-spezia"]],
                       ["emilia-romgagna","bologna","forli-cesena","ferrara","rimini","reggio-emilia","modena","parma","piacenza","ravenna"],
                       ["trentino-alto-adige",["bolzano","trento"]],
                       ["friuli-venezia-giulia",["trieste","gorizia","pordenone","udine"]],
                       ["puglia",["bari","taranto","brindisi","foggia","lecce","barletta-andria-trani"]],
                       ["calabria",["reggio-calabria","cosenza","crotone","catanzaro","vibo-valencia"]],
                       ["sicilia",["palermo","ragusa","siracusa","agrigento","caltanissetta","messina","enna","trapani"]],
                       ["sardegna",["sassari","cagliari","oristano","nuoro","olbia-tempio","carbonia-iglesias","ogliastra","medio-campidano"]],
                       ["veneto",["venezia","verona","belluno","padova","rovigo","treviso","vicenza"]]]


$("#region").on("change",function(e){
	changeProvince();
});

function changeProvince(){
	var index = index2region[($("#region")[0].selectedIndex)];

	for (var i=0; i<region2province.length; i++){
		console.log(region2province[i][0]);
		if (index === region2province[i][0]) {
			var province = region2province[i][1];
			break;
		}

	}
	$("#province").fadeOut(0);

	var provinceText = '<select name="province" id="province">';
	for (var j=0; j<province.length; j++){

		provinceText = provinceText + '<option name="'+province[j]+'">'+province[j]+'</option>';

	}
	provinceText = provinceText + '</selected>';
	$(".provinceInput").prepend(provinceText);
	j=0;
}

changeProvince();



$(".searchButton").on("click",function(e){
	e.preventDefault();
	var $myForm = $('#searchForm'); //prendo la form
	if (!$myForm[0].checkValidity()) { //controllo validitÃ  del form
		console.log("VALIDITY ERROR");
	}
	else {
		var formData = $("#searchForm").serialize(); //get all data from form
		$.ajax({
			type: "GET",
			url: "RetriveData",
			dataType: "json",
			cache: false,
			data: formData,
			success: function(result) {
				console.log("Success");
				console.log(result);
				saveResults(result);
				$(".classifyButton").fadeIn(0);
				$(".categoryCheckbox").fadeIn(0);
			},
			error: function(result) {
				console.log("error");
				console.log(result);

			}
		});
	}
});

function saveResults(result){
	console.log(result);
	$(".results").fadeOut(0);
	$(".resultsBox").prepend('<div class="results" style="height:800px;overflow-y:scroll"></div>');
	for (i in result){
		item = result[i];
		console.log(item);
		$(".results").append('<div class="item"><div class="imageBox"><img src="'+item.image+'"></img></div><div class="infoBox"><b>Title:</b>'+item.title+'<br><b>Description:</b>'+item.description+'<br><b>Price</b>'+item.price+' Eur<br><a href="'+item.link+'"type="button">Go to Item</button></div>');
	}
}

$(".classifyButton").on("click",function(e){
	e.preventDefault();
	var $myForm = $('#searchForm'); //prendo la form
	if (!$myForm[0].checkValidity()) { //controllo validitÃ  del form
		console.log("VALIDITY ERROR");
	}
	else {
		var categories = produceCategories();
		console.log(categories);
		$.ajax({
			type: "GET",
			url: "ClassifyData",
			dataType: "json",
			data:{categories: categories},
			cache: false,
			success: function(result) {
				console.log("Success");
				console.log(result);
				saveResults(result);

			},
			error: function(result) {
				console.log("error");
				console.log(result);

			}
		});
	}
});

function produceCategories(){
	var category = null;
	if ($("#garanzia")[0].value == "on"){
		if (category == null){
			category = "garanzia";
		} else {
			category = category + ",garanzia";
		}
	}
	return category;
}

$(".classifyButton").fadeOut(0);
$(".categoryCheckbox").fadeOut(0);