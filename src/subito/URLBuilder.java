package subito;


public class URLBuilder {

	public String buildURL(String query, String region, String category, String province) {
		  boolean isProvince = true;
		  if ((province != null) && (province.equals("italia") || province.equals("vicino"))){
			  isProvince = false;
			  region = region+"-"+province;
		  }
	      String url = "http://www.subito.it/annunci-"+region+ "/vendita/";
	      if (category == null) {
	    	  url = url +"usato/";
	      } else {
	    	  url = url + category + "/";
	      }
	      if ((province != null) && (isProvince)) {
	    	  url = url + province + "/";
	      }
	      if (query != null) {
	    	  url = url + "?q=" + query;
	      }
	      
	      return url;
	}

	public String buildAmazonUrl(String query) {
		return "http://www.amazon.it/s/ref=nb_sb_noss_1?__mk_it_IT=ÅMÅŽÕÑ&url=search-alias%3Daps&field-keywords=bose+acoustimass";
	}
    
	
}

