package com.example.cs213_p4;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * Controller class responsible for the main controller
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class MainController {
    private StoreOrders allOrders;
    private Order currentOrder;

    @FXML
    private ImageView imageView_logo;

    @FXML
    private Button btn_Order_Donuts;

    @FXML
    private Button btn_Order_Coffee;

    @FXML
    private Button btn_View_Items_In_Cart;

    @FXML
    private Button btn_View_All_Orders;

    @FXML
    private Button btn_Quit;

    /**
     * Void responsible for termination of program
     * @param event
     */
    @FXML
    void action_quit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Void responsible for launching the order donut view
     * @param event
     */
    @FXML
    void action_Order_Donuts(ActionEvent event) {
        orderProcess();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("order-donuts-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("RU Cafe - Order Donuts");
            stage.setScene(scene);
            stage.setUserData(currentOrder);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Void responsible for launching the order coffee view
     * @param event
     */
    @FXML
    void action_Order_Coffee(ActionEvent event) {
        orderProcess();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("order-coffee-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("RU Cafe - Order Coffee");
            stage.setScene(scene);
            stage.setUserData(currentOrder);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * void responsible for launching basket view
     * @param event
     */
    @FXML
    void action_View_Items_In_Cart(ActionEvent event) {
        orderProcess();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("order-basket-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("RU Cafe - Order Basket");
            stage.setScene(scene);
            stage.setUserData(currentOrder);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * void responsible for launching all orders view
     * @param event
     */
    @FXML
    void action_View_All_Orders(ActionEvent event) {
        orderProcess();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("store-orders-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("RU Cafe - All Orders");
            stage.setScene(scene);
            stage.setUserData(allOrders);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * void responsible for view initialization. Including loading of imageView element.
     */
    @FXML
    public void initialize() {
        File file = new File("assets/RU_Cafe.png");
        Image image = new Image(file.toURI().toString());
        imageView_logo.setImage(image);
        allOrders = new StoreOrders();
        currentOrder = new Order();
    }

    /**
     * Helper void to process orders. Reduces code duplication.
     */
    private void orderProcess() {
        if (!(currentOrder.getOrderStatus().equals("In-Progress"))) {
            if (currentOrder.getOrderStatus().equals("Submitting")) {
                currentOrder.setOrderStatus("Submitted");
                allOrders.add(currentOrder);
            }
            currentOrder = new Order();
        }
    }

}