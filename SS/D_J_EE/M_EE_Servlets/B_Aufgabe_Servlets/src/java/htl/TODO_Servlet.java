/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
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
@WebServlet(name = "TODO_Servlet", urlPatterns = {"/todo_list"})
public class TODO_Servlet extends HttpServlet {

    //Sitzungattribut ToDo_liste
    private static final String ITEMS = "items";
    private static final String ITEM = "item";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        //To-Do_Liste anlegen
        List<String> items = (List<String>) session.getAttribute(ITEMS);
        if(items == null){
            items = new LinkedList<String>();
            session.setAttribute(ITEMS, items);
        }
        
        //neuen Eintrag holen
        String item = request.getParameter(ITEM);
        if(item != null && !item.trim().isEmpty()){
            items.add(item);
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>ToDo-Liste</h1>");
            out.println("<form method=\"POST\" action=\"todo_list\">");
            out.println("<input type=\"text\" name=\"item\">");
            out.println("<input type=\"submit\" value=\"Hinzufügen\">");

            if(items.isEmpty()){
                out.println("<h2>keine Einträge</h2>");
            }else{
                out.println("<h2>Auf der Liste:</h2>");
                out.println("<table>");
                for(String text : items){
                    out.println("<tr><td>" + text + "</td></tr>");
                }
                out.println("</table>");
            }
            
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
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
