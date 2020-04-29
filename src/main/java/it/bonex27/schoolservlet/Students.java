/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bonex27.schoolservlet;

import com.google.gson.Gson;
import it.bonex27.schoolservlet.pojo.Classe;
import it.bonex27.schoolservlet.pojo.Student;
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
 * @author pietrobonechi
 */
public class Students extends HttpServlet {

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
            out.println("<title>Servlet Students</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Students at " + request.getContextPath() + "</h1>");
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
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")) {
            try {
                doPatch(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            super.service(request, response);
        }

    }

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
            out = response.getWriter();
            con = this.getConnection();

            if (request.getParameter("id") == null) {
                ps = con.prepareStatement("select * from student");
            } else {
                ps = con.prepareStatement("select * from student where id = " + request.getParameter("id"));
            }

            ResultSet rs = ps.executeQuery();

            List<Student> elencoStudenti = new ArrayList();
            while (rs.next()) {
                Student st = new Student(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), rs.getString(5));
                elencoStudenti.add(st);
            }

            String ret = new Gson().toJson(elencoStudenti);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(ret);
            out.flush();
        } catch (Exception e) {
            System.out.println(e);
            out.print(e);
        } finally {
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
        try {
            //String json = "{ 'year':1200, 'section':'AAA'}";
            Gson gson = new Gson();
            out = response.getWriter();
            con = this.getConnection();

            Student stu = gson.fromJson(getRequestBody(request), Student.class);

            // Query mysql
            String query = "insert into student (name, surname,sidiCode,taxCode)"
                    + " values (?,?,?,?)";

            try {
                // Creazione statement
                ps = con.prepareStatement(query);
                ps.setString(1, stu.getName());
                ps.setString(2, stu.getSurname());
                ps.setInt(3, stu.getSidiCode());
                ps.setString(4, stu.getTaxCode());

                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                out.print(e);
            }
            out.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            out.print(e);
        } finally {
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            out = response.getWriter();
            con = this.getConnection();

            if (request.getParameter("id") == null) {
                response.setHeader("Status code", "401");
            } else {
                ps = con.prepareStatement("delete from student where id = ?");
            }

            ps.setString(1, request.getParameter("id"));
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            out.print(e);
        } finally {
            out.close();

        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        out = response.getWriter();
        con = this.getConnection();

        if (request.getParameter("id") == null) {
            System.out.println("Error");
        } else {
            Student stu = gson.fromJson(getRequestBody(request), Student.class);
            stu.setId(Integer.parseInt(request.getParameter("id")));

            // Query mysql
            String query = "UPDATE student SET name = ?, surname = ?, sidiCode = ?, taxCode = ? WHERE id = ? ";

            try {
                // Creazione statement
                ps = con.prepareStatement(query);
                ps.setString(1, stu.getName());
                ps.setString(2, stu.getSurname());
                ps.setInt(3, stu.getSidiCode());
                ps.setString(4, stu.getTaxCode());
                ps.setInt(5, stu.getId());

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
            Student stu = gson.fromJson(getRequestBody(request), Student.class);
            stu.setId(Integer.parseInt(request.getParameter("id")));

            String campi = "";
            int counter = 0;
            if (!stu.getName().equals("")) {
                counter++;
                campi += "name = ?,";  
            }
            if (!stu.getSurname().equals("")) {
                counter++;
                campi += "surname = ?,";  
            }
            if (stu.getSidiCode()!= 0) {
                counter++;
                campi += "sidiCode = ?,";  
            }
            if (!stu.getTaxCode().equals("")) {
                counter++;
                campi += "taxCode = ?,";  
            }
            counter++;
            campi = campi.substring(0, campi.length() - 1);
           
            String query = "UPDATE student SET " + campi + " WHERE id = ?";
            ps = con.prepareStatement(query);
            
            counter = 0;
            if (!stu.getName().equals("")) {
                counter++;
                campi += "name = ?,";  
            }
            if (!stu.getSurname().equals("")) {
                counter++;
                campi += "surname = ?,";  
            }
            if (stu.getSidiCode()!= 0) {
                counter++;
                campi += "sidiCode = ?,";  
            }
            if (!stu.getTaxCode().equals("")) {
                counter++;
                campi += "taxCode = ?,";  
            }
            counter++;
            ps.setInt(counter, stu.getId());
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
