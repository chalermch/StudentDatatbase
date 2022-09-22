/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentdatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.DatabaseHandler;

/**
 *
 * @author Admin
 */
public class StudentTable {
    public static int updateStudent(DatabaseHandler dbHandler, Student std) {
        String sql = "update student set name=?, GPA=? where id=?";
        int rowUpdated;
        try {
            rowUpdated = dbHandler.update(sql, std.getName(), std.getGpa(), std.getId());
        }
        catch (SQLException ex ) {
            
            rowUpdated = 0;
        }
        
        return rowUpdated;
    }
     public static int removeStudent(DatabaseHandler dbHandler, Student std) {
         String sql ="delete from student where id = ?";
         
         int rowDeleted;
         try {
            rowDeleted = dbHandler.update(sql, std.getId());
         }
         catch (SQLException ex ) {
             rowDeleted = 0;
         }
        return rowDeleted;
    }
    public static int insertStudent(DatabaseHandler dbHandler, Student std) {
         String sql = "insert into student (id, name, GPA)" + 
               " values (?,?,?)";
         
         int rowInserted;
         try {
             rowInserted = dbHandler.update(sql, std.getId(), std.getName(), std.getGpa());
         }
         catch(SQLException ex ) {
             rowInserted = 0;
         }
         return rowInserted;
    } 
     public static Student findStudentById(DatabaseHandler dbHandler, int id) throws SQLException {
        String sql = "select * from student where id = ?";
        ResultSet rs;
        Student std = null;
        rs = dbHandler.query(sql, id);
        if (rs.next()) {
           std = new Student();
           std.setId(rs.getInt("id"));
           std.setName(rs.getString("name"));
           std.setGpa(rs.getDouble("GPA"));
       }
        return std;
        
    }
    public static ArrayList<Student> findStudentByName(DatabaseHandler dbHandler, String name) throws SQLException {
        String sql = "select * from student where name = ?";
        ResultSet rs;
        ArrayList<Student> stdList = null;
        rs = dbHandler.query(sql, name);
        stdList = extractStudent(rs);
        return stdList;
        
    } 
    public static ArrayList<Student> findAllStudent(DatabaseHandler dbHandler) throws SQLException {
        String sql = "select * from student order by id";
        ResultSet rs; 
        ArrayList<Student> stdList = null;
        rs = dbHandler.query(sql);
        stdList = extractStudent(rs);
        return stdList;
    }
    private static ArrayList<Student> extractStudent(ResultSet rs) {
        ArrayList<Student> stdList = new ArrayList<>();
        try {
            while(rs.next()) {
                Student student = new Student();
                try {
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setGpa(rs.getDouble("GPA"));
                } catch (SQLException ex) {
                    Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                stdList.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(stdList.isEmpty()) {
            stdList = null;
        }
        return stdList;
    }
}
