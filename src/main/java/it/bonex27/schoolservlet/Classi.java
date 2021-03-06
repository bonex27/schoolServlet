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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Connection con = null;
    PreparedStatement ps = null;
    
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
            try {
                doPatch(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Classi.class.getName()).log(Level.SEVERE, null, ex);
        }
        else 
            super.service(request, response);
            
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            out = response.getWriter();
            con = this.getConnection();
            
            if(request.getParameter("id") == null)
                 ps = con.prepareStatement("select * from class");
            else
                ps = con.prepareStatement("select * from class where id = "+request.getParameter("id"));
             
            ResultSet rs =ps.executeQuery();
            
            List<Classe> elencoClassi = new ArrayList();
            while (rs.next()) {
                Classe cl = new Classe(Integer.parseInt(rs.getString(1)),Integer.parseInt(rs.getString(2)),rs.getString(3));
                elencoClassi.add(cl);
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
        try
        {
            //String json = "{ 'year':1200, 'section':'AAA'}";
            Gson gson = new Gson();
            out = response.getWriter();
            con = this.getConnection();
            
            Classe clss =  gson.fromJson(getRequestBody(request), Classe.class);
            
            // Query mysql
            String query = "insert into class (year, section)"
              + " values (?, ?)";

            // Creazione statement
            ps = con.prepareStatement(query);
            ps.setInt(1, clss.getYear());
            ps.setString (2, clss.getSection());

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
                ps = con.prepareStatement("delete from class where id = ?");
            
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        Gson gson = new Gson();
        out = response.getWriter();
        con = this.getConnection();

        if (request.getParameter("id") == null) {
            System.out.println("Error");
        } else {
            Classe cls = gson.fromJson(getRequestBody(request), Classe.class);
            cls.setId(Integer.parseInt(request.getParameter("id")));

            // Query mysql
            String query = "UPDATE class SET year = ?,  section = ? WHERE id = ? ";

            try {
                // Creazione statement
                ps = con.prepareStatement(query);
                ps.setInt(1, cls.getYear());
                ps.setString(2, cls.getSection());
                ps.setInt(5, cls.getId());

                ps.executeUpdate();
                out.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
                out.print(e);
            }
        }
    }

    
     
    private void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Gson gson = new Gson();
        out = response.getWriter();
        con = this.getConnection();

        if (request.getParameter("id") == null) {
            System.out.println("Error");

        } else {
            Classe cls = gson.fromJson(getRequestBody(request), Classe.class);
            cls.setId(Integer.parseInt(request.getParameter("id")));

            String campi = "";
            int counter = 0;
            if (cls.getYear()!= 0) {
                counter++;
                campi += "year = ?,";
            }

            if (!cls.getSection().equals("")) {
                counter++;
                campi += "section = ?,";  
            }
            counter++;
            campi = campi.substring(0, campi.length() - 1);
           
            String query = "UPDATE class SET " + campi + " WHERE id = ?";
            ps = con.prepareStatement(query);
            
            counter = 0;
            if (cls.getYear()!= 0) {
                counter++;
                ps.setInt(counter, cls.getYear());
            }

            if (!cls.getSection().equals("")) {
                counter++;
                ps.setString(counter, cls.getSection());
            }
            counter++;
            ps.setInt(counter, cls.getId());
            try {
                ps.executeUpdate();
                out.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
                out.print(e);
            }
        }//To change body of generated methods, choose Tools | Templates.
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
