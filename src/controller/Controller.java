package controller;

import subito.*;

import java.io.IOException;
import java.util.*;

import classifier.ItemFinder;

import amazon.AmazonRetriver;

public class Controller {

	public ArrayList<Item> retriveItems(String query, String region, String province, String category){
		URLBuilder builder = new URLBuilder();
		String url = builder.buildURL(query, region, category, province);
		Retriever retriever = new Retriever();
		try {
			ArrayList<Item> itemList = retriever.retrieve(url);
			return itemList;
		} catch (IOException e) {
		     return null;
		}
		
	}

	public ArrayList<Item> findBest(String query, ArrayList<Item> items) {
		AmazonRetriver retriver = new AmazonRetriver();
		Double averagePrice = retriver.searchItemValue(query);
		ItemFinder finder = new ItemFinder();
		ArrayList<Item> bestItems = finder.findBest(averagePrice,items);
		
		return bestItems;
	}
	
}
