package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import subito.Item;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import controller.Controller;

/**
 * Servlet implementation class RetriveData
 */
@WebServlet("/RetriveData")
public class RetriveData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RetriveData() {
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
		
		String query = request.getParameter("query");
	    String category = request.getParameter("category");
	    String region = request.getParameter("region");
	    String province = request.getParameter("province");
	    Controller controller = new Controller();
	    ArrayList<Item> items = (ArrayList<Item>) controller.retriveItems(query, region, province, category);
		Gson json = new Gson();
		
		String jsonItem = json.toJson(items);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    System.out.println(jsonItem);
	    request.getSession().setAttribute("elements", items);
	    request.getSession().setAttribute("query", query);
        response.getWriter().write(jsonItem);
        
   
	}

}
