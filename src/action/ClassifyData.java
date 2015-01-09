package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import controller.Controller;

import amazon.RetrieveAmazon;

import subito.Item;

/**
 * Servlet implementation class ClassifyData
 */
@WebServlet("/ClassifyData")
public class ClassifyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassifyData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Item> items = (ArrayList<Item>) request.getSession().getAttribute("elements"); 
		String query = (String) request.getSession().getAttribute("query");
		//check categorie
		SubitoUtils utils = new SubitoUtils();
		Controller controller = new Controller();
		ArrayList<Item> bestItems = controller.findBest(query,items,utils.checkCategories(request));
		Gson json = new Gson();
		String jsonItem = json.toJson(bestItems);
		System.out.println(bestItems.size());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    System.out.println(jsonItem);
		response.getWriter().write(jsonItem);
	}

}
