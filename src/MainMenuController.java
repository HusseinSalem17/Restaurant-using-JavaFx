/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Kareem_Muhamed
 */
public class MainMenuController implements Initializable {

    @FXML
    TextField numMeals;
    @FXML
    TextField numDrinks;
    @FXML
    Pane Drinks;
    @FXML
    Pane Meals;
    //Meals table ID
    @FXML
    TextField numM;
    @FXML
    TextField nameM;
    @FXML
    ComboBox<String> typeM;
    @FXML
    TextField costM;
    @FXML
    TextField countM;
    @FXML
    Label doneM;

    @FXML
    TableView<Meals> tableM;
    @FXML
    TableColumn<Meals, Integer> numCM;
    @FXML
    TableColumn<Meals, String> nameCM;
    @FXML
    TableColumn<Meals, String> typeCM;
    @FXML
    TableColumn<Meals, Integer> costCM;
    @FXML
    TableColumn<Meals, Integer> countCM;
    //Drinks table ID
    @FXML
    TextField numD;
    @FXML
    TextField nameD;
    @FXML
    ComboBox<String> typeD;
    @FXML
    TextField costD;
    @FXML
    TextField countD;
    @FXML
    Label doneD;

    @FXML
    TableView<Drinks> tableD;
    @FXML
    TableColumn<Drinks, Integer> numCD;
    @FXML
    TableColumn<Drinks, String> nameCD;
    @FXML
    TableColumn<Drinks, String> typeCD;
    @FXML
    TableColumn<Drinks, Integer> costCD;
    @FXML
    TableColumn<Drinks, Integer> countCD;

    @FXML
    ObservableList<Meals> listM;
    @FXML
    ObservableList<Drinks> listD;

    int indexM = -1, indexD = -1;

    @FXML
    TextField searchD;
    @FXML
    TextField searchM;


    public void entered(Event e) {
        ((Button) e.getSource()).setScaleX(1.1);
        ((Button) e.getSource()).setScaleY(1.1);

        if (((Button) e.getSource()).getText().equals("Logout")) {
            ((Button) e.getSource()).setTextFill(Color.RED);
        } else {
            ((Button) e.getSource()).setTextFill(Color.BLUE);
        }

    }

    public void exited(Event e) {
        ((Button) e.getSource()).setScaleX(1);
        ((Button) e.getSource()).setScaleY(1);

        if (((Button) e.getSource()).getText().equals("Logout")) {
            ((Button) e.getSource()).setTextFill(Color.RED);
        } else {
            ((Button) e.getSource()).setTextFill(Color.BLACK);
        }

    }

    public void Drinks() {
        Drinks.setVisible(true);
        Meals.setVisible(false);
    }

    public void Meals() {
        Drinks.setVisible(false);
        Meals.setVisible(true);
    }

    //to insertMeals
    public void insertMeals() {

        //if i didn't insert any data
        try {
            int num = Integer.parseInt(numM.getText());
            String name = nameM.getText();
            String type = typeM.getValue().toString();
            int cost = Integer.parseInt(costM.getText());
            int count = Integer.parseInt(countM.getText());

            //because it return false if didn't insert or updated
            if (!DB.insert("Meals", num, name, type, cost, count)) {
                //to show the new Meal that i just added from ObservableList
                listM.add(new Meals(num, name, type, cost, count));

                doneM.setText("Successfully added");
                doneM.setVisible(true);
                numMeals.setText(Integer.parseInt(numMeals.getText()) + 1 + "");
                clearM();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Information Dialog");
            alert.setContentText("Please enter data in the fields to add...");
            alert.showAndWait();
        }

    }

    //to insertDrinks
    public void insertDrinks() {

        //if i didn't insert any data
        try {
            int num = Integer.parseInt(numD.getText());
            String name = nameD.getText();
            String type = typeD.getValue();
            int cost = Integer.parseInt(costD.getText());
            int count = Integer.parseInt(countD.getText());

            //because it return false if didn't insert or updated
            if (!DB.insert("Drinks", num, name, type, cost, count)) {
                //to show the new Drink that i just added ObservableList
                listD.add(new Drinks(num, name, type, cost, count));

                doneD.setText("Successfully added");
                doneD.setVisible(true);
                numDrinks.setText(Integer.parseInt(numDrinks.getText()) + 1 + "");
                clearD();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Information Dialog");
            alert.setContentText("Please enter data in the fields to add...");
            alert.showAndWait();
        }

    }


    //getSelected to return the index of item which i clicked in the table (every row have an index)
    public void getSelectedMeals() {

        numM.setDisable(true);
        indexM = tableM.getSelectionModel().getSelectedIndex();

        //because if there not any row ( there is no exist for -1 )
        if (indexM <= -1) {
            return;
        }

        //put the table data in TextField
        numM.setText(numCM.getCellData(indexM).toString());
        nameM.setText(nameCM.getCellData(indexM));
        //get the typeCM and put it in the typeM
        typeM.getSelectionModel().select(typeCM.getCellData(indexM));
        costM.setText(costCM.getCellData(indexM).toString());
        countM.setText(countCM.getCellData(indexM).toString());
    }

    //to updateMeals
    public void updateMeals() {
        //if i didn't select any row
        try {
            int num = Integer.parseInt(numM.getText());
            String name = nameM.getText();
            String type = typeM.getValue().toString();
            int cost = Integer.parseInt(costM.getText());
            int count = Integer.parseInt(countM.getText());


            if (DB.update("Meals", "where numM = " + num, name, type, cost, count)) {
                //to show what i just have updated in the table and put the new data
                listM.set(indexM, new Meals(listM.get(indexM).getId(), name, type, cost, count));

                doneM.setText("Successfully edited");
                doneM.setVisible(true);
                clearM();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Information Dialog");
            alert.setContentText("Please select a field to edit...");
            alert.showAndWait();
        }


    }

    public void clearM() {
        numM.setDisable(false);
        numM.setEditable(true);
        numM.clear();
        nameM.clear();
        typeM.getSelectionModel().select(-1); //-1 => for default ( ...اختر)
        costM.clear();
        countM.clear();

    }

    public void getSelectedDrinks() {

        numD.setDisable(true);
        indexD = tableD.getSelectionModel().getSelectedIndex();

        //because if there not any row ( there is no exist for -1 )
        if (indexD <= -1) {
            return;
        }

        //put the table data in TextField
        numD.setText(numCD.getCellData(indexD).toString());
        nameD.setText(nameCD.getCellData(indexD));
        //get the typeCM and put it in the typeM
        typeD.getSelectionModel().select(typeCD.getCellData(indexD));
        costD.setText(costCD.getCellData(indexD).toString());
        countD.setText(countCD.getCellData(indexD).toString());
    }

    //to updateMeals
    public void updateDrinks() {
        //if i didn't select any row
        try {
            int num = Integer.parseInt(numD.getText());
            String name = nameD.getText();
            String type = typeD.getValue().toString();
            int cost = Integer.parseInt(costD.getText());
            int count = Integer.parseInt(countD.getText());


            if (DB.update("Drinks", "where numD = " + num, name, type, cost, count)) {
                //to show what i just have updated in the table and put the new data
                listD.set(indexD, new Drinks(listD.get(indexD).getId(), name, type, cost, count));

                doneD.setText("Successfully edited");
                doneD.setVisible(true);
                clearD();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Information Dialog");
            alert.setContentText("Please select a field to edit...");
            alert.showAndWait();
        }


    }

    public void clearD() {
        numD.setDisable(false);
        numD.setEditable(true);
        numD.clear();
        nameD.clear();
        typeD.getSelectionModel().select(-1); //-1 => for default ( ...اختر)
        costD.clear();
        countD.clear();
    }

    public void deleteMeals() {

        if (indexM == -1)
            return;

        if (DB.delete("Meals", "numM =" + numCM.getCellData(indexM))) {
            listM.remove(indexM);
            doneM.setText("Successfully Deleted");
            doneM.setVisible(true);
            numMeals.setText(Integer.parseInt(numMeals.getText()) - 1 + "");
            indexM = -1;
            clearM();
        }
    }

    public void deleteDrinks() {

        if (indexD == -1)
            return;

        if (DB.delete("Drinks", "numD =" + numCD.getCellData(indexD))) {
            listD.remove(indexD);
            doneD.setText("Successfully Deleted");
            doneD.setVisible(true);
            numDrinks.setText(Integer.parseInt(numDrinks.getText()) - 1 + "");
            indexD = -1;
            clearD();
        }
    }

    //search for meals
    public void searchMeals() {

        searchM.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (searchM.textProperty().get().isEmpty()) {
                    tableM.setItems(listM);
                    return;
                }

                ObservableList<Meals> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Meals, ?>> cols = tableM.getColumns();
                for (int i = 0; i < listM.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(listM.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(searchM.getText().toLowerCase()) && cellValue.startsWith(searchM.getText().toLowerCase())) {
                            tableItems.add(listM.get(i));
                            break;
                        }
                    }
                }
                tableM.setItems(tableItems);
            }
        });

    }

    //search for Drinks
    public void searchDrinks() {

        //set listener to check the input
        searchD.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //if there is no words in searchD
                if (searchD.textProperty().get().isEmpty()) {
                    tableD.setItems(listD);
                    return;
                }

                //to store the search data in tableItems
                ObservableList<Drinks> tableItems = FXCollections.observableArrayList();
                //to check every cell in the column
                ObservableList<TableColumn<Drinks, ?>> cols = tableD.getColumns();
                //first loop for rows
                for (int i = 0; i < listD.size(); i++) {
                    //second loop for columns
                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn col = cols.get(j);//to get the column
                        String cellValue = col.getCellData(listD.get(i)).toString();//to take the cell which i take the value from it
                        cellValue = cellValue.toLowerCase();//to convert the cellValue to lowerCase
                        //to check if the cellValue have the i just typed   | to check if starts like the word i just typed or not
                        if (cellValue.contains(searchD.getText().toLowerCase()) && cellValue.startsWith(searchD.getText().toLowerCase())) {
                            tableItems.add(listD.get(i)); // to add the row if true
                            break;
                        }
                    }
                }
                //to show the new data which add from search (add in the table)
                tableD.setItems(tableItems);
            }
        });

    }

    //to logout (back to Login Screen)
    public void logout(Event e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            //e.getSource to get the source which clicked on it (Button) but i stored it as Node because every parent
            //or children in Node
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");

            //to open the MenuMain window in the center
            Rectangle2D rd = Screen.getPrimary().getBounds();
            stage.setX((rd.getWidth() - stage.getWidth()) / 2);
            stage.setY((rd.getHeight() - stage.getHeight()) / 2);
        } catch (Exception exception) {

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get the data from class Meals
        numCM.setCellValueFactory(new PropertyValueFactory<Meals, Integer>("id"));
        nameCM.setCellValueFactory(new PropertyValueFactory<Meals, String>("name"));
        typeCM.setCellValueFactory(new PropertyValueFactory<Meals, String>("type"));
        costCM.setCellValueFactory(new PropertyValueFactory<Meals, Integer>("cost"));
        countCM.setCellValueFactory(new PropertyValueFactory<Meals, Integer>("count"));

        //get the data from class Drinks
        numCD.setCellValueFactory(new PropertyValueFactory<Drinks, Integer>("id"));
        nameCD.setCellValueFactory(new PropertyValueFactory<Drinks, String>("name"));
        typeCD.setCellValueFactory(new PropertyValueFactory<Drinks, String>("type"));
        costCD.setCellValueFactory(new PropertyValueFactory<Drinks, Integer>("cost"));
        countCD.setCellValueFactory(new PropertyValueFactory<Drinks, Integer>("count"));


        //to show the data which i got from the class Meals in the table
        listM = DB.getMeals();
        tableM.setItems(listM);

        //to show the data which i got from the class Meals in the table
        listD = DB.getDrinks();
        tableD.setItems(listD);

        //set the count number of numDrinks and numMeals in ACCESS
        numDrinks.setText(DB.count("numD", "Drinks") + "");
        numMeals.setText(DB.count("numM", "Meals") + "");

        ObservableList<String> listM = FXCollections.observableArrayList("Breakfast", "Burgers", "Happy Meal", "Fries & Sides");
        typeM.setItems(listM);

        ObservableList<String> listD = FXCollections.observableArrayList("MilkShake", "Soda MIX", "Smoothies", "Sunday", "Hot Milky", "Coffee");
        typeD.setItems(listD);

        // TODO
    }

}
