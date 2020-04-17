/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bonex27.schoolservlet.pojo;

/**
 *
 * @author pie
 */
public class Student {
    
    private int id;
    private String name;
    private String surname;
    private int sidiCode;
    private String taxCode;

    public Student(int id, String name, String surname, int sidiCode, String taxCode) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.sidiCode = sidiCode;
        this.taxCode = taxCode;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSidiCode() {
        return sidiCode;
    }

    public void setSidiCode(int sidiCode) {
        this.sidiCode = sidiCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
    
    
}
