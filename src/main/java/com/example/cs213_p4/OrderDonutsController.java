package com.example.cs213_p4;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class responsible for the order donut view.
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class OrderDonutsController {
    Order currentOrder;

    @FXML
    private ComboBox donut_type_comboBox;

    @FXML
    private ComboBox donut_flavor_comboBox;

    @FXML
    private VBox container_mod_quantity;

    @FXML
    private TextField selected_donut_quantity;

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
     *
     * @param event
     */
    @FXML
    void action_quit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * void responsible for processing the request to add selected donut to an order.
     *
     * @param event
     */
    @FXML
    void action_Submit(ActionEvent event) {
        Stage stage = (Stage) btn_submit.getScene().getWindow();
        if (currentOrder != null) {
            String type = (String) donut_type_comboBox.getValue();
            String flavor = (String) donut_flavor_comboBox.getValue();

            int Quantity = 0;

            String temp_Quantity = selected_donut_quantity.getText();

            if (temp_Quantity != null) {
                try {
                    Quantity = Integer.parseInt(temp_Quantity);
                } catch (NumberFormatException e) {
                }
            }

            if (Quantity >= 0) {

                if ((type != null) && (flavor != null)) {
                    Donut donut_to_add = new Donut(type, flavor);
                    for (int i = 0; i < Quantity; i++) {
                        currentOrder.add(donut_to_add);
                    }
                    stage.close();
                }
            }
        }
    }

    /**
     * void responsible for closing current view without taking any action.
     *
     * @param event
     */
    @FXML
    void action_Cancel(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * void resposnsible for procesing comboBox selections
     *
     * @param event
     */
    @FXML
    void action_comboBox_select(ActionEvent event) {
        String type = (String) donut_type_comboBox.getValue();
        String flavor = (String) donut_flavor_comboBox.getValue();

        if ((type != null) && (flavor != null)) {
            Stage stage = (Stage) btn_submit.getScene().getWindow();
            currentOrder = (Order) stage.getUserData();
            container_mod_quantity.setVisible(true);
            label_subtotal.setVisible(true);
            updatePrice();
        }

    }

    /**
     * void for processing textField updates
     *
     * @param event
     */
    @FXML
    void action_textfield_update(ActionEvent event) {
        updatePrice();
    }

    /**
     * void responsible for current view initialization. Including population of comboBox selection for donut type and flavor.
     */
    @FXML
    public void initialize() {
        donut_type_comboBox.getItems().removeAll(donut_type_comboBox.getItems());
        donut_type_comboBox.getItems().addAll("Yeast Donut", "Cake Donut", "Donut Holes");
        donut_flavor_comboBox.getItems().removeAll(donut_flavor_comboBox.getItems());
        donut_flavor_comboBox.getItems().addAll("Flavor 1", "Flavor 2", "Flavor 3");
    }

    /**
     * helper void responsible for updating price displayed in the interface. Reduces code duplication.
     */
    private void updatePrice() {
        String type = (String) donut_type_comboBox.getValue();

        int Quantity = 0;

        String temp_Quantity = selected_donut_quantity.getText();

        if (temp_Quantity != null) {
            try {
                Quantity = Integer.parseInt(temp_Quantity);
            } catch (NumberFormatException e) {
                selected_donut_quantity.setText("0");
            }
        }

        if (Quantity >= 0) {
            double cost = switch (type) {
                case "Yeast Donut" -> 1.59;
                case "Cake Donut" -> 1.79;
                case "Donut Holes" -> 0.39;
                default -> 0.00;
            };

            cost *= Quantity;
            label_subtotal.setText("Subtotal: $" + (Math.round(cost * 100.0) / 100.0));
        } else {
            selected_donut_quantity.setText("0");
            label_subtotal.setText("Subtotal: $0.00");
        }
    }
}