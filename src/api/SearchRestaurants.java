package api;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet("/restaurants")
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRestaurants() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
/*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	     response.setContentType("application/json");
	   	 response.addHeader("Access-Control-Allow-Origin", "*");
	   	 String username = "";
	   	 PrintWriter out = response.getWriter();
	   	 if (request.getParameter("username") != null) {
	   		 username = request.getParameter("username");
	   		 out.print("Hello " + username);
	   	 }
	   	 out.flush();
	   	 out.close();

	}*/
	private static final Logger LOGGER = Logger.getLogger(SearchRestaurants.class.getName());
	
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
    	// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}
    	  JSONArray array = new JSONArray();
  		DBConnection connection = new MySQLDBConnection();
    	//DBConnection connection = new MongoDBConnection();
  		if (request.getParameterMap().containsKey("lat")
  				&& request.getParameterMap().containsKey("lon")) {
  			// term is null or empty by default
  			String term = request.getParameter("term");
  			//String userId = (String) session.getAttribute("user");
            String userId = "1111";
  			double lat = Double.parseDouble(request.getParameter("lat"));
  			double lon = Double.parseDouble(request.getParameter("lon"));
  			LOGGER.log(Level.INFO, "lat:" + lat + ",lon:" + lon);
  			array = connection.searchRestaurants(userId, lat, lon, term);
  		}
  		RpcParser.writeOutput(response, array);
      }

	




	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}
		doGet(request, response);
	}

}
