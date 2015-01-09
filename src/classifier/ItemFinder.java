package classifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import subito.Item;
import subito.Retriever;

public class ItemFinder {
	
	private List<CategoryElement> categories;
	private Classifier classifier;
	
	public ItemFinder() {
		this.classifier = new Classifier();
		this.categories = new ArrayList<CategoryElement>();
		this.categories.add(new CategoryElement("garanzia",1,-1,0.5,0));
	}

	public double score(String itemDescription, ArrayList<String> selectedCategories){
		double score = 0;
		
		for (CategoryElement category : categories){
			String classified = classifier.classifyText(itemDescription, category.getCategory());
			if (selectedCategories.contains(category.getCategory())){
				if (classified.equals("si")){
					score = score + category.getYesOnYes();
				} else {
					score = score + category.getNoOnYes();
				}
			} else {
				if (classified.equals("si")){
					score = score + category.getYesOnNo();
				} else {
					score = score + category.getNoOnNO();
				}
			}
		}
		return score;
	}
	
	public ArrayList<Item> findBest(Double averagePrice, ArrayList<Item> items, ArrayList<String> categories) {
	    ArrayList<Item> scoredItems = new ArrayList<Item>();
	
		for (Item item : items){
			System.out.println(item.toString());
			double score = score(item.getDescription(), categories);
			System.out.println(score);
			item.setScore(score);
			scoredItems.add(item);
		}
		System.out.println(scoredItems.size());
		Collections.sort(scoredItems);
		int size;
		int i = 0;
		if (scoredItems.size()<=10){
			size = scoredItems.size();
		} else {
			size = 10;
		}
		Iterator<Item> iter = scoredItems.iterator();
		ArrayList<Item> bestItems = new ArrayList<Item>();
		while(iter.hasNext()){
			i++;
			bestItems.add(iter.next());
			if (i>=size)
				break;
			}
		return bestItems;
		}
		

	

	public static void main(String[] args){
	  Retriever retriever = new Retriever();
	  ItemFinder finder = new ItemFinder();
	  ArrayList<String> categories = new ArrayList<String>();
	  categories.add("garanzia");
	  
	  try {
		ArrayList<Item> items = retriever.retrieve("http://www.subito.it/annunci-lazio/vendita/videogiochi/lazio/roma/?q=Crash");
		items = finder.findBest(100.0, items, categories);
		for (Item item : items){
			System.out.println(item.toString());
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}
