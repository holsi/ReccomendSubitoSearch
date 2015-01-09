package subito;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PoolThreadLinks implements Runnable {


	private ArrayList<Element> pages;
	private ArrayList<String> links;

	public PoolThreadLinks(ArrayList<Element> pages, ArrayList<String> links){
		this.links=links;
		this.pages = pages;
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


	@Override
	public void run() {

		for (Element link : pages) {

			links.add(compose("%s", link.attr("abs:href"),
					trim(link.text(), 35)));
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
