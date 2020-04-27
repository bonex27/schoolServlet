/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bonex27.schoolservlet;

import com.google.gson.Gson;
import it.bonex27.schoolservlet.pojo.Classe;
import it.bonex27.schoolservlet.pojo.Student;
import it.bonex27.schoolservlet.pojo.StudentClass;
import java.io.BufferedReader;
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
 * @author pietrobonechi
 */
public class StudentClasses extends HttpServlet {

    PrintWriter out = null; 
    Connection con = null;
    PreparedStatement ps = null;
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentClasses</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentClasses at " + request.getContextPath() + "</h1>");
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
    private String getRequestBody(HttpServletRequest request) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader reader = request.getReader();
    try {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
    } finally {
        reader.close();
    }
    return sb.toString();
}
    
    @Override
     public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
     {
        if (request.getMethod().equalsIgnoreCase("PATCH"))
            doPatch(request, response);
        else 
            super.service(request, response);
            
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
        try{
            out = response.getWriter();
            con = this.getConnection();
            
            if(request.getParameter("id") == null)
                 ps = con.prepareStatement("select * from student_class");
            else
                ps = con.prepareStatement("select * from student_class where id = "+request.getParameter("id"));
             
            ResultSet rs =ps.executeQuery();
            
            List<StudentClass> elencoSC= new ArrayList();
            while (rs.next()) {
                StudentClass st = new StudentClass(rs.getInt(1),rs.getInt(2),rs.getInt(3));
                elencoSC.add(st);
            }
            
            
            String ret = new Gson().toJson(elencoSC);
            
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
        try
        {
            Gson gson = new Gson();
            out = response.getWriter();
            con = this.getConnection();
            
            StudentClass stuClss =  gson.fromJson(getRequestBody(request), StudentClass.class);
            
            // Query mysql
            String query = "insert into student_class (idStudent, idClass)"
              + " values (?, ?)";

            // Creazione statement
            ps = con.prepareStatement(query);
            ps.setInt(1, stuClss.getIdStudent());
            ps.setInt(2, stuClss.getIdClass());

             ps.execute();
            
             out.close();
             con.close();
             
        } catch(Exception e)
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         try{
            out = response.getWriter();
            con = this.getConnection();
            
            if(request.getParameter("id") == null)
                 response.setHeader("Status code", "401");
            else
                ps = con.prepareStatement("delete from student_class where id = ?");
            
            ps.setString(1, request.getParameter("id"));
            ps.executeUpdate();
        con.close();
        }
        catch(Exception e)
        {
            out.print(e);
        }
        finally
        {
            out.close();
            
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void doPatch(HttpServletRequest request, HttpServletResponse response) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
