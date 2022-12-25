import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://Restaurant.accdb");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up");
            alert.setHeaderText("Error");
            alert.setContentText("Not Connect");
            alert.showAndWait();
        }
        return con;
    }

    //return the count of meals and drinks
    public static int count(String col, String table) {

        Connection con = connect();

        try {
            PreparedStatement ps = con.prepareStatement("select count(" + col + ") from " + table); //to return the number of columns in table
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {

        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }
        return 0;
    }

    //insert function
    public static boolean insert(String table, int id, String name, String type, int cost, int count) {
        PreparedStatement ps = null;
        String sql = "";
        Connection con = connect();

        try {
            if (table.equals("Meals")) {
                sql = "insert into " + table + "([numM], [nameM], [typeM], [costM], [countM]) Values (" + id + ", '" + name + "', '" + type + "', " + cost + ", " + count + ")";
            } else {
                sql = "insert into " + table + "([numD], [nameD], [typeD], [costD], [countD]) Values (" + id + ", '" + name + "', '" + type + "', " + cost + ", " + count + ")";
            }
            ps = con.prepareStatement(sql);

            //ps.execute (return boolean) if there result (didn't insert right or update) return true , if insert or update return false
            return ps.execute();

        } catch (Exception e) {
            if (table.equals("Drinks")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up");
                alert.setHeaderText("Error");
                alert.setContentText("رقم المشروب او اسم المشروب موجود مسبقا");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up");
                alert.setHeaderText("Error");
                alert.setContentText("رقم الوجبة او اسم الوجبة موجود مسبقا");
                alert.showAndWait();
            }

        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }

        return true;
    }

    public static ObservableList<Drinks> getDrinks() {
        Connection con = connect();
        ObservableList<Drinks> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = con.prepareStatement("select * from Drinks");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Drinks(rs.getInt("numD"), rs.getString("nameD"), rs.getString("typeD"), rs.getInt("costD"),rs.getInt("countD")));
            }

        } catch (Exception e) {

        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }
        return list;
    }

    public static ObservableList<Meals> getMeals() {
        Connection con = connect();
        ObservableList<Meals> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = con.prepareStatement("select * from Meals");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Meals(rs.getInt("numM"), rs.getString("nameM"), rs.getString("typeM"), rs.getInt("costM"), rs.getInt("countM")));
            }

        } catch (Exception e) {

        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }
        return list;
    }

    public static boolean update(String table, String where, String name, String type, int cost, int count) {

        Connection con = connect();
        String sql;
        if (table.equals("Drinks")) {
            sql = "update " + table + " set nameD ='" + name + "' ,typeD ='" + type + "' ,costD =" + cost + " ,countD =" + count + " " + where;
        } else {
            sql = "update " + table + " set nameM ='" + name + "' ,typeM ='" + type + "' ,costM =" + cost + " ,countM =" + count + " " + where;
        }
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            return !ps.execute();//return false if it insert or update or delete so i put (!) to return true

        } catch (Exception e) {
            //because if i entered name exist in the table ( name is unique )
            if (table.equals("Drinks")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up");
                alert.setHeaderText("Error");
                alert.setContentText("رقم المشروب او اسم المشروب موجود مسبقا");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up");
                alert.setHeaderText("Error");
                alert.setContentText("رقم المشروب او اسم المشروب موجود مسبقا");
                alert.showAndWait();
            }
        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }

        return false;
    }

    //delete data from database (ACCESS)
    public static boolean delete(String table, String where) {
        Connection con = connect();

        try {
            //to delete row from the table
            PreparedStatement ps = con.prepareStatement("delete from " + table + " where " + where);
            return !ps.execute();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up");
            alert.setHeaderText("Error");
            alert.setContentText("لم تنجح عملية الحذف...");
            alert.showAndWait();
        } finally {
            try {
                con.close();
            } catch (Exception e) {

            }
        }

        return false;
    }
}
