package com.example.cs213_p4;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller class responsible for the basket view.
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class OrderBasketController {
    Order currentOrder;

    @FXML
    private ListView order_listView;

    @FXML
    private Label label_subtotal;

    @FXML
    private Label label_tax;

    @FXML
    private Label label_total;

    @FXML
    private Button btn_remove_item;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_lookup_order;

    @FXML
    private Button btn_place_order;

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

    /**
     * void responsible for placing order
     * @param event
     */
    @FXML
    void action_place_order(ActionEvent event) {
        Stage stage = (Stage) btn_place_order.getScene().getWindow();
        if(!(order_listView.getItems().isEmpty())){
            currentOrder.setOrderStatus("Submitting");
            stage.close();
        }
    }

    /**
     * void responsible for looking up current order.
     * @param event
     */
    @FXML
    void action_lookup_order(ActionEvent event) {
        Stage stage = (Stage) btn_place_order.getScene().getWindow();
        currentOrder = (Order) stage.getUserData();

        order_listView.getItems().clear();

        MenuItem[] order_items = currentOrder.getOrderItems();

        double subtotal = 0.00;

        for (int i = 0; i < order_items.length; i++) {
            order_listView.getItems().add(order_items[i].toString());
            subtotal += order_items[i].itemPrice();
        }

        double tax = subtotal * 0.06625;
        double total = subtotal + tax;
        label_subtotal.setText("Subtotal: $" + (Math.round(subtotal * 100.0) / 100.0));
        label_tax.setText("Tax: $" + (Math.round(tax * 100.0) / 100.0));
        label_total.setText("Total: $" + (Math.round(total * 100.0) / 100.0));
    }

    /**
     * void responsible for removing item from order
     * @param event
     */
    @FXML
    void action_remove_item(ActionEvent event) {
        String selection = order_listView.getSelectionModel().getSelectedItem().toString();

        if (selection != null) {
            Stage stage = (Stage) btn_place_order.getScene().getWindow();
            currentOrder = (Order) stage.getUserData();

            MenuItem[] order_items = currentOrder.getOrderItems();
            for (int i = 0; i < order_items.length; i++) {
                if (order_items[i].toString().equals(selection)) {
                    currentOrder.remove(order_items[i]);
                    order_listView.getItems().remove(order_listView.getSelectionModel().getSelectedItem());
                    break;
                }
            }

            order_items = currentOrder.getOrderItems();

            double subtotal = 0.00;

            for (int i = 0; i < order_items.length; i++) {
                subtotal += order_items[i].itemPrice();
            }

            double tax = subtotal * 0.06625;
            double total = subtotal + tax;
            label_subtotal.setText("Subtotal: $" + (Math.round(subtotal * 100.0) / 100.0));
            label_tax.setText("Tax: $" + (Math.round(tax * 100.0) / 100.0));
            label_total.setText("Total: $" + (Math.round(total * 100.0) / 100.0));

        }
    }

    /**
     * void responsible for closing current view without taking any action.
     * @param event
     */
    @FXML
    void action_cancel(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

}