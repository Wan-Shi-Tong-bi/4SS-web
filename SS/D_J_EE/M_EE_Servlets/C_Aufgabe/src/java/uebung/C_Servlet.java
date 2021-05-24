/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uebung;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "C_Servlet", urlPatterns = {"/index"})
public class C_Servlet extends HttpServlet {

    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String ITEM = "item";

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

        boolean isLoggedIn = false;
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(15);

        try {

            String dbURL = "jdbc:derby://localhost:1527/M_EE_Servlets_C_Aufgabe";
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/M_EE_Servlets_C_Aufgabe_2", "db", "db");
            Statement s = conn.createStatement();
            s.execute("SELECT * FROM users");
            ResultSet rs = s.getResultSet();

            String name = request.getParameter(NAME);
            String password = request.getParameter(PASSWORD);
            if ((name != null && !name.trim().isEmpty()) && (password != null && !password.trim().isEmpty())) {
                while (rs.next()) {
                    if (rs.getString("name").equals(name) && rs.getString("password").equals(password)) {
                        isLoggedIn = true;
                        break;
                    }
                }
            }

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                //head
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>loggedIn</title>");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
                out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6\" crossorigin=\"anonymous\">");
                out.println("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf\" crossorigin=\"anonymous\"></script>");
                out.println("</head>");

                String item = (String) session.getAttribute(ITEM);
                if (item == null) {
                    item = "item";
                    session.setAttribute(ITEM, item);

                    out.println("<body>");
                    out.println("<div class=\"container\">");
                    out.println("<h1 class=\"display-2\">Anmelden<h2>");
                    out.println("<form method=\"POST\" action=\"index\">");
                    out.println("<label for=\"name\">Name:</label>\n");
                    out.println("<input type=\"text\" name=\"name\" id=\"name\" class=\"form-control rounded-right\">");
                    out.println("<label for=\"password\">Password:</label>\n");
                    out.println("<input type=\"text\" name=\"password\" id=\"password\" class=\"form-control rounded-right\">");
                    out.println("<input type=\"submit\" value=\"Submit\" class=\"btn btn-primary\">");
                    out.println("</form>");

                    out.println("</body>");
                    out.println("</html>");
                } else {

                    //body
                    if (!isLoggedIn) {
                        out.println("<body>");
                        out.println("<div class=\"container\">");
                        out.println("<h1 class=\"display-2\">Anmelden<h2>");
                        out.println("<form method=\"POST\" action=\"index\">");
                        out.println("<label for=\"name\">Name:</label>\n");
                        out.println("<input type=\"text\" name=\"name\" id=\"name\" class=\"form-control rounded-right\">");
                        out.println("<label for=\"password\">Password:</label>\n");
                        out.println("<input type=\"text\" name=\"password\" id=\"password\" class=\"form-control rounded-right\">");
                        out.println("<input type=\"submit\" value=\"Submit\" class=\"btn btn-primary\">");
                        out.println("</form>");

                        out.println("</body>");
                        out.println("</html>");

                    } else {
                        out.println("<body>");
                        out.println("<div class=\"container\">");
                        out.println("<h2 class=\"display-3\">Teilnehmerliste</h2>");
                        out.println("<table>");
                        out.println("<tr><th class=\"text-justify\">name</th></tr>");
                        s.execute("SELECT name FROM users");
                        rs = s.getResultSet();
                        while (rs.next()) {
                            out.println("<tr><td class=\"text-justify\">" + rs.getString("name") + "</td></tr>");
                        }
                        out.println("</table>");
                        out.println("<button class=\"btn btn-primary\" onClick=\"window.location.reload();\">Refresh Page</button>");

                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");
                    }

                }
            }

            conn.close();

        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
            Logger.getLogger(C_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Short description";
    }// </editor-fold>

}
