package com.example.cs213_p4;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class responsible for the order coffee view.
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class OrderCoffeeController {

    @FXML
    private ComboBox coffee_size_comboBox;

    @FXML
    private CheckBox coffee_addin_cream;

    @FXML
    private CheckBox coffee_addin_syrup;

    @FXML
    private CheckBox coffee_addin_milk;

    @FXML
    private CheckBox coffee_addin_caramel;

    @FXML
    private CheckBox coffee_addin_whipped_cream;

    @FXML
    private VBox container_mod_quantity;

    @FXML
    private TextField selected_coffee_quantity;

    @FXML
    private Label label_subtotal;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_submit;

    @FXML
    private Button btn_quit;

    /**
     * Void responsible for termination of program
     * @param event
     */
    @FXML
    void action_quit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void action_Submit(ActionEvent event) {
        Stage stage = (Stage) btn_submit.getScene().getWindow();
        Order currentOrder = (Order) stage.getUserData();

        String size = (String) coffee_size_comboBox.getValue();
        int Quantity = 0;
        String temp_Quantity = selected_coffee_quantity.getText();

        if (temp_Quantity != null) {
            try {
                Quantity = Integer.parseInt(temp_Quantity);
            } catch (NumberFormatException e) {
            }
        }

        if (Quantity >= 0) {
            if(size != null){
                Coffee coffee_to_add = new Coffee(size);

                if (coffee_addin_cream.isSelected()) {
                    coffee_to_add.add(new Coffee(size, "Cream"));
                }
                if (coffee_addin_syrup.isSelected()) {
                    coffee_to_add.add(new Coffee(size, "Syrup"));
                }
                if (coffee_addin_milk.isSelected()) {
                    coffee_to_add.add(new Coffee(size, "Milk"));
                }
                if (coffee_addin_caramel.isSelected()) {
                    coffee_to_add.add(new Coffee(size, "Caramel"));
                }
                if (coffee_addin_whipped_cream.isSelected()) {
                    coffee_to_add.add(new Coffee(size, "Whipped Cream"));
                }
                for (int i = 0; i < Quantity; i++) {
                    currentOrder.add(coffee_to_add);
                }
                stage.close();
            }
        }

    }

    /**
     * void responsible for closing current view without taking any action.
     * @param event
     */
    @FXML
    void action_Cancel(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * void resposnible for processing actions taken on comboBox elements
     * @param event
     */
    @FXML
    void action_comboBox_select(ActionEvent event) {
        String size = (String) coffee_size_comboBox.getValue();
        if (size != null) {
            container_mod_quantity.setVisible(true);
            label_subtotal.setVisible(true);
            updatePrice();
        }
    }

    /**
     * void responsible for processing checkBox selections
     * @param event
     */
    @FXML
    void action_checkbox_selection(ActionEvent event) {
        String size = (String) coffee_size_comboBox.getValue();
        if (size != null) {
            updatePrice();
        }
    }

    /**
     * void responsible for processing textField updates
     * @param event
     */
    @FXML
    void action_textfield_update(ActionEvent event) {
        updatePrice();
    }

    /**
     * Void responsible for initialization of current view. Including population of combobox with coffee sizes.
     */
    @FXML
    public void initialize() {
        coffee_size_comboBox.getItems().removeAll(coffee_size_comboBox.getItems());
        coffee_size_comboBox.getItems().addAll("Short", "Tall", "Grande", "Venti");
    }

    /**
     * helper void responsible for updating price displayed in the interface. Reduces code duplication.
     */
    private void updatePrice() {
        String size = (String) coffee_size_comboBox.getValue();

        int Quantity = 0;
        String temp_Quantity = selected_coffee_quantity.getText();

        if (temp_Quantity != null) {
            try {
                Quantity = Integer.parseInt(temp_Quantity);
            } catch (NumberFormatException e) {
                selected_coffee_quantity.setText("0");
            }
        }

        if (Quantity >= 0) {
            double cost = 1.69;

            switch (size) {
                case "Tall" -> cost += 0.40;
                case "Grande" -> cost += 0.80;
                case "Venti" -> cost += 1.20;
            }

            if (coffee_addin_cream.isSelected()) {
                cost += 0.30;
            }
            if (coffee_addin_syrup.isSelected()) {
                cost += 0.30;
            }
            if (coffee_addin_milk.isSelected()) {
                cost += 0.30;
            }
            if (coffee_addin_caramel.isSelected()) {
                cost += 0.30;
            }
            if (coffee_addin_whipped_cream.isSelected()) {
                cost += 0.30;
            }

            cost *= Quantity;
            label_subtotal.setText("Subtotal: $" + (Math.round(cost * 100.0) / 100.0));
        } else {
            selected_coffee_quantity.setText("0");
            label_subtotal.setText("Subtotal: $0.00");
        }
    }
}