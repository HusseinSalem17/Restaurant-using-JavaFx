/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kareem_Muhamed
 */
public class AdminsDB {
    // to connect with MA DataBase
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://Restaurant.accdb");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Not Connect");
        }
        return con;
    }

    // function to add all users data from MA DataBase
    public static List<Admins> getAdmins() {
        // i created list to get all users from sql and put all in it
        List<Admins> list = new ArrayList<Admins>();
        try {
            String sql = "select * from Admins";
            Connection con = AdminsDB.connect();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            // this line read the values from database that i was make connection to get
            ResultSet resultSet = preparedStatement.executeQuery();

            // loop to add all values that i was get from database in my list i was created
            while (resultSet.next()) {
                Admins adm = new Admins();
                //adm.setId(resultSet.getInt(1));
                adm.setUsername(resultSet.getString("name"));
                adm.setPassword(resultSet.getString("password"));
                list.add(adm);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error in Connection");
        }
        return list;
       /*
       this function it will return  list like this
       
       1 kareem 123
       2 user1 1234
       3 user2 8798
       
       */
    }

    public static Boolean signUp(String name, String pass) {
        PreparedStatement ps;
        String sql;
        Connection con = connect();
        try {
            sql = "insert into  Admins ([name], [password]) Values ('" + name + "', '" + pass + "')";
            ps = con.prepareStatement(sql);
            return ps.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up");
            alert.setHeaderText("Error");
            alert.setContentText("Enter another Name");
            alert.showAndWait();
        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }
        return true;

    }
}
