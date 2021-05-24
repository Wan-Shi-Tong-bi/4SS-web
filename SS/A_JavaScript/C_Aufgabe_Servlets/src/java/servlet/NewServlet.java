/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jerem
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
              boolean logedIn = false;
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(15);
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/db",
                "db",
                "db");
        String name = (String) session.getAttribute("name");
        String pass = (String) session.getAttribute("pass");
        if (name == null && pass == null) {
            name = request.getParameter("fname");
            pass = request.getParameter("lname");
        }
        Statement stmt = (Statement) con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT FIRSTNAME, LASTNAME FROM USERN");
        ArrayList<String> list = new ArrayList<>();
        PrintWriter out = null;
        out = response.getWriter();

        out.println("<html>\n"
                + "    <head>\n"
                //+ "    <base href=\"https://www.w3schools.com/\" target=\"_blank\">"
                + "        <title>logedIn</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf\" crossorigin=\"anonymous\"></script>"
                + "        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6\" crossorigin=\"anonymous\">"
                + "    </head>\n");

        List<String> items = null;

        String nameDb = "";
        String passDb = "";

        if (name != null || pass == null) {
            items = new LinkedList<String>();

            while (rs.next()) {
                nameDb = rs.getString("FIRSTNAME");
                passDb = rs.getString("LASTNAME");
                items.add(nameDb);
                if (nameDb.equals(name) && passDb.equals(pass)) {
                    session.setAttribute("name", name);
                    session.setAttribute("pass", pass);

                    logedIn = true;
                }

            }
            if (!logedIn) {
                response.setContentType("text/html;charset=UTF-8");
                out = response.getWriter();
                out.println("<body>\n"
                        + "        <form action=\"http://localhost:8080/Vorzeigen/NewServlet\">\n"
                        + "            <label for=\"fname\">First name:</label><br>\n"
                        + "            <input type=\"text\" id=\"fname\" name=\"fname\" class=\"form-control rounded-right\"><br>\n"
                        + "            <label for=\"lname\">Last name:</label><br>\n"
                        + "            <input type=\"password\" id=\"lname\" name=\"lname\" class=\"form-control rounded-right\">\n"
                        + "            <input class=\"btn btn-primary\" type=\"submit\" value=\"Submit\">\n"
                        + "        </form>\n"
                        + "    </body>\n"
                        + "</html>\n");
                RequestDispatcher requestDispatcher
                        = request.getRequestDispatcher("/login.html");
                response.setHeader("Location", "http://localhost:8080/Vorzeigen/logedIn");

            }
        }
        if (logedIn) {
            out = null;
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");

            out = response.getWriter();

            out.println("<body>");

            out.println("<h2 class=\"display-3\">Teilnehmerliste</h2>");
            out.println("<table>");
            out.println("<h6 class=\"display-6\">name</h2>");
            for (String text : items) {
                out.println("<tr><td class=\"text-justify\">" + text + "</td></tr>");
            }
            out.println("</table>");
            out.println("<form action=\"http://localhost:8080/Vorzeigen/NewServlet\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"refresh\">");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
            response.setHeader("Location", "http://localhost:8080/Vorzeigen/notLogedIn");
            RequestDispatcher requestDispatcher
                    = request.getRequestDispatcher("/logedIn.html");
        }

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
