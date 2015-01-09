

package amazon;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import subito.Item;
import subito.PoolThreadItems;
import subito.Retriever;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;

import org.jsoup.*;

public class RetrieveAmazon {


	private ArrayList<String> getLinks2Amazon(String url) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
        
		
		Document doc = Jsoup.connect(url).timeout(0).get();


		Elements links = doc.select("[class=a-link-normal s-access-detail-page a-text-normal]");


		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");


		for (Element link : links) {


			list.add(compose("%s", link.attr("abs:href"),
					trim(link.text(), 35)));
		}
		return list;
	}


	private Item getItem2Amazon(String itemLink) throws IOException, NumberFormatException{
		Document doc;
		doc = Jsoup.connect(itemLink).timeout(30000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
		Element header;
		String title;
		String priceText;

		if(doc.select("[class=a-container]").first()!=null)
			header = doc.select("[class=a-container]").first();
		else 
			if(doc.select("[id=handleBuy]").first()!=null)
				header = doc.select("[id=handleBuy]").first();
			else
				header = doc.select("[id=a-page]").first();

		if(!header.select("[id=productTitle]").isEmpty())
			title = header.select("[id=productTitle]").text();
		else 
			title = header.select("[id=btAsinTitle]").text();

		if(header.select("[id=priceblock_ourprice]").text().split(" ").length >= 2)
			priceText = header.select("[id=priceblock_ourprice]").text().split(" ")[1];
		else
			priceText = header.select("[id=priceblock_ourprice]").text().split(" ")[0];


		if (priceText.equals("")) {
			priceText = "0";
		} 

		if(priceText.contains(",")){
			String[] array = priceText.split("\\,");
			priceText = array[0];
		}

		Double price = Double.parseDouble(priceText);


		String description = doc.select("[class=content]").text();

		Item item = new Item(title,description, "", price,itemLink);
		return item; 
	}

	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	private  String compose(String msg, Object... args) {
		return String.format(msg, args);
	}

	public boolean isTheSameProduct(Item itS, Item itA) {
		double score = 0;
		int soglia = 100;
		double ltit = 1;					//max delle due stringhe
		double delta = 0.3;

		if(itS.getTitle().length()>=itA.getTitle().length())
			ltit = itS.getTitle().length();
		else
			ltit = itS.getTitle().length();

		for(String s: itS.getTitle().split(" ")) {
			for(String a: itA.getTitle().split(" ")) 
				if(s.equalsIgnoreCase(a))
					score += (soglia/3);
		}

		if(varianzaRelativa(itS.getPrice(),itA.getPrice(),delta))
			score += soglia*delta;

		for(String s: itS.getDescription().split(" ")) {
			for(String a: itA.getDescription().split(" ")) 
				if(s.equalsIgnoreCase(a))
					score += 1;
		}

		if(score>=soglia)
			return true;

		return false;

	}

	public boolean varianzaRelativa(Double n, Double n1, Double var) {
		double max = Math.max(n, n1);
		double min = Math.min(n, n1);
		double range = max*var;

		if((min+range)>=max)
			return true;

		return false;

	}

	public Item getAmazonItem(String url) throws IOException{
		ArrayList<String> links = getLinks2Amazon(url);
		ArrayList<Item> totItemA = new ArrayList<Item>();
		for(String linkA: links){
			totItemA.add(getItem2Amazon(linkA));
		}

		Item  itemsA = getItem2Amazon(links.get(0));

		for(Item item: totItemA)
			if(item.getPrice()>=itemsA.getPrice())
				itemsA = item;
		return itemsA;

	}
	
	public static void main(String[] args) throws IOException{	
		//		RetrieveAmazon retriver = new RetrieveAmazon();
		//		ArrayList<String> links= retriver.getLinks2Amazon("http://www.amazon.it/s/ref=nb_sb_noss_1?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Delectronics&field-keywords=htc+m8&rh=n%3A412609031%2Ck%3Ahtc+m8");
		//		System.out.println("Prodotti ritrovati:" + links.size());
		//		for (String link : links){
		//			System.out.println(retriver.getItem2Amazon(link).toString());
		//		}

		RetrieveAmazon retriever = new RetrieveAmazon();
		ArrayList<String> links = retriever.getLinks2Amazon("http://www.amazon.it/s/ref=nb_sb_noss_1?__mk_it_IT=ÅMÅŽÕÑ&url=search-alias%3Daps&field-keywords=bose+acoustimass");
		ArrayList<Item> totItemA = new ArrayList<Item>();
		for(String linkA: links){
			totItemA.add(retriever.getItem2Amazon(linkA));
		}

		Item  itemsA = retriever.getItem2Amazon(links.get(0));

		for(Item item: totItemA)
			if(item.getPrice()>=itemsA.getPrice())
				itemsA = item;

		//		for(String link: links) {
		//			itemsA.add(retriever.getItem2Amazon(link));
		//		}
		System.out.println(itemsA.getTitle());







		Retriever retrieverS = new Retriever();
		ArrayList<String> links2query = retrieverS.getPageLink2Query("http://www.subito.it/annunci-lazio/vendita/usato/?q=apple");
		ArrayList<String> linksS = retrieverS.getLinks(links2query);
		ArrayList<Item>  itemsS = new ArrayList<Item>();

		System.out.println("Start time:" + new Date());

		//modifica relativa al problema di multithread  su pagine che hanno granularita minore di 22

		ExecutorService executor;

		//quando e verificato questo if la procedura non termina.
		if(linksS.size()<22) {
			executor = Executors.newFixedThreadPool(1);
			Runnable worker = new PoolThreadItems(linksS ,itemsS);
			executor.execute(worker);
			//	          }

		}
		else {
			executor = Executors.newFixedThreadPool(linksS.size()/22);
			int surplus = 0;

			surplus = linksS.size();
			//			System.out.println(surplus);
			//			System.out.println(surplus%22);

			for (int i = 1; i <= linksS.size()/22 ; i++) {

				List sublist = new ArrayList();
				if (i==linksS.size()/22){
					sublist = new ArrayList<String>(linksS.subList((i-1)*22, surplus));
				} else {
					sublist = new ArrayList<String>(linksS.subList((i-1)*22, (i*22)));
				}

				Runnable worker = new PoolThreadItems((ArrayList<String>) sublist ,itemsS);
				executor.execute(worker);
			}
		}

		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("End time:" + new Date());

		System.out.println(itemsS.size());
		for(int i=0; i<6; i++) {
			//System.out.println(retriever.isTheSameProduct(itemsS.get(i),itemsA));
			if(retriever.isTheSameProduct(itemsS.get(i),itemsA))
				System.out.println(" Buono:" + itemsS.get(i).getLink());
			else
				System.out.println(" Cattivo:" + itemsS.get(i).getLink());
		}





		//test getPageLink2Amazon
		//		Retrieve retriver = new Retrieve();
		//		ArrayList<String> links = retriver.getLinks2Amazon("http://www.amazon.it/s/ref=nb_sb_noss_2?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Delectronics&field-keywords=iphone+6&rh=n%3A412609031%2Ck%3Aiphone+6");
		//		System.out.println("(" + links.size() + ")");
		//		for (String link : links){
		//			System.out.println(link.toString());
		//		}		


	}
}

