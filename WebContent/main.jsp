<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SubitoSearch</title>
</head>
<body>
	
	<div class="formBox">
		<form  id="searchForm">
			<div class="queryInput">
				<input type="text" name="query">
			</div>
			<div class="categoryInput">
				<select name="category">
					<option value="elettronica">--ELETTRONICA--</option>
					<option value="informatica">Informatica</option>
					<option value="console-e-videogiochi">Console e
						Videogiochi</option>
					<option value="audio-video">Audio/Video</option>
					<option value="fotografia">Fotografia</option>
					<option value="telefonia">Telefonia</option>

				</select>
			</div>
			<div class="regionInput">
				<select name="region" id="region">
					<option value="lazio">Lazio</option>
					<option value="toscana">Toscana</option>
					<option value="umbria">Umbria</option>
					<option value="marche">Marche</option>
					<option value="piemonte">Piemonte</option>
					<option value="lombardia">Lombardia</option>
					<option value="campania">Campania</option>
					<option value="Sardegna">Sardegna</option>
					<option value="emilia-romagna">Emilia-Romagna</option>
					<option value="trentino-alto-adige">Trentino-Alto Adige</option>
					<option value="valle-d-aosta">Val D'Aosta</option>
					<option value="friuli-venezia-giulia">Friuli-Venezia-Giulia</option>
					<option value="liguria">Liguria</option>
					<option value="calabria">Calabria</option>
					<option value="abruzzo">Abruzzo</option>
					<option value="sicilia">Sicilia</option>
					<option value="puglia">Puglia</option>
					<option value="molise">Molise</option>
					<option value="basilicata">Basilicata</option>
					<option value="veneto">Veneto</option>
				</select>
			</div>
			<div class="provinceInput">
			
			</div>
			<div class="categoryInput">
				<input type="checkbox" class="categoryCheckbox" id="garanzia">				
			</div>
		</form>
		<button class="searchButton">Cerca Su Subito</button>
		<button class="classifyButton">Trova i migliori annunci</button>
	</div>
	<div class="resultsBox"></div>
</body>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script>
        console.log("Page loaded");
        $(document).ready(function() {
            $.getScript("main.js");

        });
</script>
</html>