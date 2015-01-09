package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class SubitoUtils {
	
	ArrayList<String> checkingCategories;
	
	public SubitoUtils() {
		this.checkingCategories = new ArrayList<String>();
		this.checkingCategories.add("garanzia");
	}
	
	public ArrayList<String> checkCategories(HttpServletRequest request) {
		ArrayList<String> categories = new ArrayList<String>();
		String[] categoryArray = request.getParameter("categories").split(",");
		for (String category : categoryArray){
			if (checkingCategories.contains(category)){
				categories.add(category);
			}
		}
		
		return categories;
	}

}


