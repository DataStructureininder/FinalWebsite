
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestProject
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Main() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if (request.getParameter("keyword") == null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		SearchResult sr = new SearchResult(request.getParameter("keyword"));
		sr.setResultURL();
		ArrayList<URLobj> arr0 = sr.getArr();
		String[][] arr = new String[arr0.size()][3];

		request.setAttribute("query", arr);

		int i = 0;
		for (URLobj ele : arr0) {
			String key = ele.title;
			String value = ele.getURL();
			arr[i][0] = key;
			arr[i][1] = value;
			// added
			String intro = ele.intro;
			arr[i][2] = intro;
			// end of added
			i++;
		}
//		URLobj[] arr1 = new URLobj[arr.size()];
//		for(int i = 0; i<arr.size();i++) {
//			arr1[i]=arr.get(i);
//		}

		request.getRequestDispatcher("ResultView2.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}