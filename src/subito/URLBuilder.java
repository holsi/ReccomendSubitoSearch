package subito;


public class URLBuilder {

	public String buildURL(String query, String region, String category, String province) {
		  boolean isProvince = true;
		  if ((province != null) && (province.equals("italia") || province.equals("vicino"))){
			  isProvince = false;
			  region = region+"-"+province;
		  }
	      String url = "www.subito.it/annunci-"+region+ "/vendita/";
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
	};
    
	public static void main (String[] args) {
		String query = "iphone";
		String category = "videogiochi";
		String region = "lazio";
		String province = "roma";
		String notProvince = "vicino";
		URLBuilder builder = new URLBuilder();
		System.out.println(builder.buildURL(query, region, category,province));
		System.out.println(builder.buildURL(query, region, null, null));
		System.out.println(builder.buildURL(query, region, null, notProvince));
		System.out.println(builder.buildURL(query, region, category,null));
		
		
		URLBuilder builder2 = new URLBuilder();
		System.out.println(builder.buildURL(query, region, category,province));
		System.out.println(builder.buildURL(query, region, null, null));
		System.out.println(builder.buildURL(query, region, null, notProvince));
		System.out.println(builder.buildURL(query, region, category,null));
	    System.out.println("CIAO");
	}
	
}

