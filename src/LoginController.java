import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField username;
    @FXML
    PasswordField password;

    public void login(Event e) {
        try {

            if (username.getText().trim().matches("[aA]dmin") && password.getText().equals("123")) {
                Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                Scene scene = new Scene(root);
                //e.getSource to get the source which clicked on it (Button) but i stored it as Node because every parent
                //or children in Node
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("الرئيسية");

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

    }

    public void exit() {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
