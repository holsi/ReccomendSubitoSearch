package subito;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PoolThreadItems implements Runnable {





	private ArrayList<Item> items;
	private ArrayList<String> links;

	public PoolThreadItems(ArrayList<String> links,ArrayList<Item> items){
		this.links=links;
		this.items = items;
	}

	public Item getItem(String itemLink) throws IOException{
		Document doc;
		doc = Jsoup.connect(itemLink).timeout(0).get();
		Element header = doc.select("[class=title]").first();
		String title = header.select("[id=subject_ad]").text();
		String priceText = header.select("[class=price]").text().split(" ")[0];
		if (priceText.equals("")) {
			priceText = "0";
		} 

		if(priceText.contains("EUR")) {
			String[] array = priceText.split("EUR");

			String tmp= "";
			for (String a : array){

				tmp = tmp.concat(a);
			}
			priceText = tmp;
		}

		if(priceText.contains(".")){
			String[] array = priceText.split("\\.");

			String tmp= "";
			for (String a : array){

				tmp = tmp.concat(a);
			}
			priceText = tmp;
		}

		priceText.replaceAll(",", ".");
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

	@Override
	public void run() {

		for(String link: this.links) {
			if (link == null || link.isEmpty()){
				//TO DO
			} else {
				try {
					this.items.add(getItem(link));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void processCommand() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}