/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bonex27.schoolservlet;

import com.google.gson.Gson;
import it.bonex27.schoolservlet.pojo.Classe;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pbone
 */
public class Classi extends HttpServlet {

    PrintWriter out = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet classi</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet classi at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    private Connection getConnection() {
        try {
            String dbDriver = "com.mysql.jdbc.Driver"; 
            String dbURL = "jdbc:mysql:// localhost:3306/"; 
            // Database name to access 
            String dbName = "FI_ITIS_MEUCCI"; 
            String dbUsername = "bonex"; 
            String dbPassword = "pietro001"; 

            Class.forName(dbDriver); 
            Connection con = DriverManager.getConnection(dbURL + dbName + "?serverTimezone=UTC", 
                                                         dbUsername,  
                                                         dbPassword); 
            return con;
        } catch (Exception e) {
             e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            out = response.getWriter();
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("select * from class");
            ResultSet rs =ps.executeQuery();
            
            List<Classe> elencoClassi = new ArrayList();
            while (rs.next()) {
                Classe cl = new Classe();
                cl.setId(Integer.parseInt(rs.getString(1)));
                cl.setYear(Integer.parseInt(rs.getString(2)));
                cl.setSection(rs.getString(3));
                elencoClassi.add(cl);
                //out.println("<p>"+nomeClasse+"</p>");
            }
            
            
            String ret = new Gson().toJson(elencoClassi);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(ret);
            out.flush();
        }
        catch(Exception e)
        {
            System.out.println(e);
            out.print(e);
        }
        finally
        {
            out.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.
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
