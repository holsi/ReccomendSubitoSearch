package subito;



import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Retriever {

	//action script dove viene inserita la query e convertita in htmleese



	//metodo retrieveLinks data in in input una stringa che indica url da cui prendere i links e restituisce una serie di links in base alla tipologia

	//metodo per filtrare solo i link utili

	//metodo per navidare i singoli link e prendere il contenuto

	//scelta del metodo di training

	//metodo di classificazione e predizione

	//retrieve  dati prodotti su amazon

	//filtering del prodotto rilevante

	//metodo di confronto - soglia sul prezzo del prodotto in base alla classificazione

	//presentazione dati finali



	public ArrayList<String> getPageLink2Query(String url) throws IOException {
		Document doc = Jsoup.connect(url).timeout(0).get();
		ArrayList<String> page2links = new ArrayList<String>();
		ArrayList<String >page2links2result = new ArrayList<String>();
		int number = 0;
		String lastPage;
		String baseURL;

		Elements links = doc.select("[class=pagination] a[href]");
		if(links.size()==0) {
			page2links2result.add(url);
			//System.out.println("Dentro zero if");
			System.out.println("URL:" + url);
		}
		else {
			for(Element link: links)
				page2links.add(link.attr("abs:href"));

			if(page2links.size()>10){
				//System.out.println("Dentro primo if " + page2links.size());
				System.out.println("URL:" + page2links.get(page2links.size()-1));
				lastPage = page2links.get(page2links.size()-1);
				baseURL = page2links.get(links.size()-1).substring(0, lastPage.length()-2);
				lastPage = lastPage.substring((lastPage.length()-3), lastPage.length());
				if(lastPage.contains("=")){
					lastPage = lastPage.substring(1, lastPage.length());
				}
				number = Integer.parseInt(lastPage);
			}
			else {
				//System.out.println("Dentro secondo else");
				System.out.println("URL:" + page2links.get(page2links.size()-1));
				lastPage = page2links.get(page2links.size()-1);
				baseURL = page2links.get(links.size()-1).substring(0, lastPage.length()-1);
				lastPage = lastPage.substring(lastPage.length()-1);
				if(lastPage.contains("=")){
					lastPage = lastPage.substring(1, lastPage.length());
				}
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

	public ArrayList<String> getLinks(ArrayList<String> url) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		
		System.out.println("Start time:" + new Date());
		
		for(String s: url){
			Document doc = Jsoup.connect(s).timeout(0).get();


			Elements links = doc.select("[class=adWhat] a[href]");

			
			Elements media = doc.select("[src]");
			Elements imports = doc.select("link[href]");

	
			int threads = links.size()/7;
			if (links.size()/7 < 1){
				threads = 1;
			}
			ExecutorService executor = Executors.newFixedThreadPool(threads);
			int surplus = 0;
			
			surplus =links.size();

	        for (int i = 1; i <= threads ; i++) {
	        	
	        	List sublist = new ArrayList();
	        	if (i==threads){
	        		sublist = new ArrayList<Element>(links.subList((i-1)*7, surplus));
	        	} else {
	        		sublist = new ArrayList<Element>(links.subList((i-1)*7, (i*7)));
	        	}
	        	
	            Runnable worker = new PoolThreadLinks((ArrayList<Element>) sublist , list);
	            executor.execute(worker);
	          }
	        executor.shutdown();
	        while (!executor.isTerminated()) {
	        }
		}
		System.out.println("End time:" + new Date());
		return list;
	}
	
	public ArrayList<String> getLinks2Amazon(String url) throws IOException {
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





	private  String compose(String msg, Object... args) {
		return String.format(msg, args);
	}

	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}



	


	public static void main(String[] args) throws IOException{
//		Retrieve retriver = new Retrieve();
//		ArrayList<String> links2query = retriver.getPageLink2Query("http://www.subito.it/annunci-lazio/vendita/usato/?q=windows");
//		ArrayList<String> links = retriver.getLinks(links2query);
//		System.out.println("Annunci ritrovati:" + links.size());
//		for (String link : links){
//			System.out.println(retriver.getItem(link).toString());
//		}
		

		//test getPageLink2Amazon
//		Retrieve retriver = new Retrieve();
//		ArrayList<String> links = retriver.getLinks2Amazon("http://www.amazon.it/s/ref=nb_sb_noss_2?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Delectronics&field-keywords=iphone+6&rh=n%3A412609031%2Ck%3Aiphone+6");
//		System.out.println("(" + links.size() + ")");
//		for (String link : links){
//			System.out.println(link.toString());
//		}		
		
		
		//test getPageLink2Query
		//		Retrieve retriver = new Retrieve();
		//		ArrayList<String> links = retriver.getPageLink2Query("http://www.subito.it/annunci-lazio/vendita/usato/?q=apple");
		//		System.out.println("(" + links.size() + ")");
		//		for (String link : links){
		//			System.out.println(link.toString());
		//		}
	}





	public ArrayList<Item> retrieve(String url) throws IOException{
		ArrayList<String> links2query = this.getPageLink2Query(url);
		ArrayList<String> linksS = this.getLinks(links2query);
		System.out.println("Links Number: "+ linksS.size());
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
		return itemsS;
	}	



}

