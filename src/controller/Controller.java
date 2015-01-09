package controller;

import subito.*;

import java.io.IOException;
import java.util.*;

import classifier.ItemFinder;

import amazon.RetrieveAmazon;

public class Controller {

	public ArrayList<Item> retriveItems(String query, String region, String province, String category){
		URLBuilder builder = new URLBuilder();
		String url = builder.buildURL(query, region, category, province);
		Retriever retriever = new Retriever();
		try {
			ArrayList<Item> itemList = retriever.retrieve(url);
			return itemList;
		} catch (IOException e) {
			 e.printStackTrace();
		     return null;
		}
		
	}

	public ArrayList<Item> findBest(String query, ArrayList<Item> items,ArrayList<String> categories) {
		RetrieveAmazon retriver = new RetrieveAmazon();
		URLBuilder builder = new URLBuilder();
		String amazonUrl = builder.buildAmazonUrl(query);
		Item itemA;
		try {
			itemA = retriver.getAmazonItem(amazonUrl);
			ItemFinder finder = new ItemFinder();
			ArrayList<Item> bestItems = finder.findBest(itemA.getPrice(),items,categories);
			
			return bestItems;
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		return null;
	}
	
}
