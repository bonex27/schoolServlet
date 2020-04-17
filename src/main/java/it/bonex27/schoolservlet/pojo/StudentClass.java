/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bonex27.schoolservlet.pojo;

/**
 *
 * @author bonex27
 */
public class StudentClass {
    
    private int id;
    private int idStudent;
    private int idClass;

    public StudentClass(int id, int idStudent, int idClass) {
        this.id = id;
        this.idStudent = idStudent;
        this.idClass = idClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }
    
}
