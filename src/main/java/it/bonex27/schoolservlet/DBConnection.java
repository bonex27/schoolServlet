/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bonex27.schoolservlet;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author pietrobonechi
 */
public class DBConnection {
    
    private String dbDriver = "com.mysql.jdbc.Driver"; 
    private String dbURL = "jdbc:mysql:// localhost:3306/"; 
    private String dbName = "FI_ITIS_MEUCCI"; 
    private String dbUsername = "bonex"; 
    private String dbPassword = "pietro001";
        
    public Connection getConnection()
    {
        try
        {
            Class.forName(dbDriver); 
            Connection con = DriverManager.getConnection(dbURL + dbName + "?serverTimezone=UTC",dbUsername,dbPassword); 
            return con;
            
        } catch (Exception e) 
        {
             e.printStackTrace();
            return null;
        }
    }
}
