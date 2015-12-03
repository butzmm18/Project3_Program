package servlets;

import datastore.DAOSQLite;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 * All of this application's web pages send their requests to this controller
 * which then updates the model / database as needed and transfers control with
 * data to one the the HTML/JSP view-oriented programs for display.
 *
 * @author John Phillips
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get real path to the sqlite db
        ServletContext sc = this.getServletContext();
        String dbPath = sc.getRealPath("/WEB-INF/superstar.db");

        // set default url
        String url = "/home.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }

        // perform action and set url
        if (action.equalsIgnoreCase("home")) {
            System.out.println("controller:home");
            url = "/home.html";

        } else if (action.equalsIgnoreCase("createRecord")) {
            System.out.println("controller:createRecord");
            int bloodSugar = 0;

            // get parameters passed in from the request
            String email = request.getParameter("email");
            String bloodString = request.getParameter("bloodsugar");
            String bookTitle = request.getParameter("bookTitle");
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String notes = request.getParameter("notes");

            // validate and convert salary string into a double
            if (bloodString == null || bloodString.isEmpty()) {
                bloodSugar = 0;
            } else {
                bloodSugar = Integer.parseInt(bloodString);
            }

            // store data in an User object
            User user = new User(0, email, bloodSugar, bookTitle, date, time, notes);
            System.out.println("Controller:createRecord:user=" + user);

            // validate the parameters
            if (email == null || date == null || time == null
                    || email.isEmpty() || date.isEmpty() || time.isEmpty()) {
                url = "/createRecord.jsp";
            } else {
                // insert this data record into the database
                DAOSQLite.createRecord(user, dbPath);
                url = "/home.html";
            }

        } else if (action.equalsIgnoreCase("report")) {
            System.out.println("controller:report");
            String email = request.getParameter("email");
            if (email == null || email.isEmpty()) {
                email = "%";
            }
            String startdate = request.getParameter("startdate");
            String enddate = request.getParameter("enddate");
            String lowhigh = request.getParameter("lowhigh");
            List<User> mydata = DAOSQLite.retrieveRecords(dbPath, email, startdate, enddate, lowhigh);
            request.setAttribute("email", email);
            request.setAttribute("startdate", startdate);
            request.setAttribute("enddate", enddate);
            request.setAttribute("lowhigh", lowhigh);
            request.setAttribute("mydata", mydata);
            url = "/showRecords.jsp";

        } else if (action.equalsIgnoreCase("deleteRecord")) {
            System.out.println("controller:deleteRecord");
            String idString = request.getParameter("id");
            if (idString == null || idString.isEmpty()) {
                url = "/deleteRecord.jsp";
            } else {
                DAOSQLite.deleteRecord(Integer.parseInt(idString), dbPath);
                url = "/home.html";
            }

        } else if (action.equalsIgnoreCase("makeDB")) {
            System.out.println("controller:makeDB");
            DAOSQLite.dropTable(dbPath);
            DAOSQLite.createTable(dbPath);
            DAOSQLite.populateTable(dbPath);
            url = "/home.html";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller for Employee App";
    }// </editor-fold>

}
