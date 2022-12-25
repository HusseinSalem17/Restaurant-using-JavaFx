/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author Kareem_Muhamed
 */
public class LoginController implements Initializable {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    /*
    public void login(Event e) {
        try {

            if (username.getText().trim().matches("[aA]dmin") && password.getText().equals("123")) {
                Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                Scene scene = new Scene(root,width,height-60);
                //e.getSource to get the source which clicked on it (Button) but i stored it as Node because every parent
                //or children in Node
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("The Main");

                //to open the MenuMain window in the center
                Rectangle2D rd = Screen.getPrimary().getBounds();
                stage.setX((rd.getWidth() - stage.getWidth()) / 2);
                stage.setY((rd.getHeight() - stage.getHeight()) / 2);

            } else {
                JOptionPane.showMessageDialog(null, "تحقق من اسم المستخدم وكلمة المرور");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }*/

    public void enter(ActionEvent e) throws IOException {

        List<Admins> list = AdminsDB.getAdmins(); // from mysql
        Map<String, String> map = new HashMap<String, String>();
        // in this loop i put all valuse that i was get from database in my map
        for (Admins a : list) {
            map.put(a.getUsername(), a.getPassword()); // key,value
        }
        // look username->key        password->value

        if (map.containsKey(username.getText())) {

            String val2 = map.get(username.getText());

            if (val2.equals(password.getText())) {
                //check.setText("Success");
                Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                Scene scene = new Scene(root, width, height - 60);
                //e.getSource to get the source which clicked on it (Button) but i stored it as Node because every parent
                //or children in Node
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("The Main");

                //to open the MenuMain window in the center
                Rectangle2D rd = Screen.getPrimary().getBounds();
                stage.setX((rd.getWidth() - stage.getWidth()) / 2);
                stage.setY((rd.getHeight() - stage.getHeight()) / 2);

            } else {
                //check.setText("Failed try again");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login");
                alert.setHeaderText("Information Dialog");
                alert.setContentText("Make Sure the Login Name and Password Are Correct");
                alert.showAndWait();
            }

        } else {
            //check.setText("Failed try again");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText("Information Dialog");
            alert.setContentText("Make Sure the Login Name and Password Are Correct");
            alert.showAndWait();
            System.out.println("Make Sure the Login Name and Password Are Correct");
        }
    }

   /* public void signUp(ActionEvent e)throws IOException{
        String name = newN.getText();
        String pass = newP.getText();
        Admins adm = new Admins();
        adm.setUsername(name);
        adm.setPassword(pass);
        int status = AdminsDB.signUp(adm);
        if(status>0){
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Sign Up");
                   alert.setHeaderText("Information Dialog");
                   alert.setContentText("Signed Up successfully");
                   alert.showAndWait();
               }else{
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Sign Up");
                   alert.setHeaderText("ERROR");
                   alert.setContentText("Sorry! Can't Create New Account");
                   alert.showAndWait();
               }
    }*/

    public void signUp2() {

        //if i didn't insert any data
        String name = username.getText();
        String pass = password.getText();

        if (name.isEmpty() || pass.equals("") || pass.isEmpty() || pass.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign Up");
            alert.setHeaderText("Error");
            alert.setContentText("Enter data in fields");
            alert.showAndWait();
        } else if (!AdminsDB.signUp(name, pass)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Information Dialog");
            alert.setContentText("Signed Successfully");
            alert.showAndWait();
        }

    }

    public void exit() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
