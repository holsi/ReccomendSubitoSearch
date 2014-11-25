package subito;


import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Retriever {

	private ArrayList<String> getPageLink2Query(String url) throws IOException {
		Document doc = Jsoup.connect(url).timeout(0).get();
		ArrayList<String> page2links = new ArrayList<String>();
		ArrayList<String >page2links2result = new ArrayList<String>();
		int number = 0;
		String lastPage;
		String baseURL;

		Elements links = doc.select("[class=pagination] a[href]");
		if(links.size()==0) {
			page2links2result.add(url);
			System.out.println("URL:" + url);
		}
		else {
			for(Element link: links)
				page2links.add(link.attr("abs:href"));

			if(page2links.size()>=9){
				System.out.println("URL:" + page2links.get(page2links.size()-1));
				lastPage = page2links.get(page2links.size()-1);
				baseURL = page2links.get(links.size()-1).substring(0, lastPage.length()-2);
				lastPage = lastPage.substring((lastPage.length()-3), lastPage.length());
				if(lastPage.contains("=")){
					lastPage.substring(1, lastPage.length());
				}
				number = Integer.parseInt(lastPage);
			}
			else {
				lastPage = page2links.get(page2links.size()-1);
				baseURL = page2links.get(links.size()-1).substring(0, lastPage.length()-1);
				lastPage = lastPage.substring(lastPage.length()-1);
				number = Integer.parseInt(lastPage);
			}
			page2links2result.add(url);
			int i=2;
			number++;
			while(i!=number){
				page2links2result.add(baseURL + i);
				i++;

			}
		}
		System.out.println("Pagine ritrovate:" + page2links2result.size());
		return page2links2result;

	}



	//	private ArrayList<String> getLinks(String url) throws IOException {
	//		Document doc = Jsoup.connect(url).get();
	//		ArrayList<String> list = new ArrayList<String>();
	//
	//		Elements links = doc.select("[class=adWhat] a[href]");
	//
	//
	//		Elements media = doc.select("[src]");
	//		Elements imports = doc.select("link[href]");
	//
	//
	//		for (Element link : links) {
	//
	//
	//			list.add(compose("%s", link.attr("abs:href"),
	//					trim(link.text(), 35)));
	//		}
	//		return list;
	//	}

	private ArrayList<String> getLinks(ArrayList<String> url) throws IOException {
		ArrayList<String> list = new ArrayList<String>();

		for(String s: url){
			Document doc = Jsoup.connect(s).timeout(0).get();


			Elements links = doc.select("[class=adWhat] a[href]");


			Elements media = doc.select("[src]");
			Elements imports = doc.select("link[href]");


			for (Element link : links) {


				list.add(compose("%s", link.attr("abs:href"),
						trim(link.text(), 35)));
			}
		}
		return list;
	}





	private  String compose(String msg, Object... args) {
		return String.format(msg, args);
	}

	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	private Item getItem(String itemLink) throws IOException{
		Document doc;
		doc = Jsoup.connect(itemLink).timeout(0).get();
		Element header = doc.select("[class=title]").first();
		String title = header.select("[id=subject_ad]").text();
		String priceText = header.select("[class=price]").text().split(" ")[0];
		if (priceText.equals("")) {
			priceText = "0";
		} 
		Elements image = doc.select("[id=display_image]");
		Double price = Double.parseDouble(priceText);
		String style = image.attr("style"); 
		String imageLink  = style.substring(style.indexOf("'") + 1);
		if (imageLink.length() == 0){
			imageLink = "None";
		} else {
			imageLink = imageLink.substring(0, (imageLink.length()-3));
		}     

		String description = doc.select("[id=body_txt]").text();

		Item item = new Item(title,description,imageLink,price,itemLink);
		return item; 
	}


	public ArrayList<Item> retrieve(String url) throws IOException{
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<String> links2query = this.getPageLink2Query(url);
		ArrayList<String> links = this.getLinks(links2query);
		System.out.println(links.size());
		for (String link : links){
			Item item = this.getItem(link);
			items.add(item);
		}
		return items;
	}	



}

