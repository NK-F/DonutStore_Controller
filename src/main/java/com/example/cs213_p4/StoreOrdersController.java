package com.example.cs213_p4;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller Class responsible for the all store orders view.
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class StoreOrdersController {

    private StoreOrders allOrders;

    @FXML
    private ListView order_listView;

    @FXML
    private Button btn_cancel_order;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_lookup_orders;

    @FXML
    private Button btn_export_to_txt_file;

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
     * void responsible for exporting all orders to txt file
     * @param event
     */
    @FXML
    void action_button_export_to_txt_file(ActionEvent event) {
        Stage stage = (Stage) btn_export_to_txt_file.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to txt File");
        fileChooser.setInitialFileName("file_export");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        File file = fileChooser.showSaveDialog(stage);

        try {
            BufferedWriter outputWriter = null;
            outputWriter = new BufferedWriter(new FileWriter(file));
            String[] out = allOrders.getOrderString();
            for (int i = 0; i < out.length; i++) {
                outputWriter.write(out[i]);
            }
            outputWriter.flush();
            outputWriter.close();
        } catch (IOException e) {

        }
    }

    /**
     * void responsible for cancelling selected order
     * @param event
     */
    @FXML
    void action_cancel_order(ActionEvent event) {
        String selection = order_listView.getSelectionModel().getSelectedItem().toString();

        if (selection != null) {
            Order[] search_orders = allOrders.getOrders();
            for (int i = 0; i < search_orders.length; i++) {
                if (search_orders[i].toString().equals(selection.toString())) {
                    allOrders.remove(search_orders[i]);
                    order_listView.getItems().remove(order_listView.getSelectionModel().getSelectedItem());
                    break;
                }
            }
        }
    }

    /**
     * void responsible for looking up all store orders
     * @param event
     */
    @FXML
    void action_lookup_orders(ActionEvent event) {
        Stage stage = (Stage) btn_lookup_orders.getScene().getWindow();
        allOrders = (StoreOrders) stage.getUserData();

        order_listView.getItems().clear();

        String[] toPrint = allOrders.getOrderString();

        for (int i = 0; i < toPrint.length; i++) {
            order_listView.getItems().add(toPrint[i]);
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