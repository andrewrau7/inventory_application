package rau.inv_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the Inventory's Main Form.
 FUTURE ENHANCEMENT: Add functionality to Delete buttons to allow them to delete parts or products that are the result of a search.
 */
public class MainFormController implements Initializable {

    Stage stage;

    Parent scene;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField partSearchTextField;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField productSearchTextField;

    @FXML
    private TableView<Product> productTableView;


    /** Method for switching screens.
     Implemented in order to reduce redundant code and increase readability.
     @param event Any event that is meant to trigger a scene change.
     @param source String that specifies location of the desired fxml file.
     */
    public void sceneSwitch(ActionEvent event, String source ) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(source));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** Method to switch user to the Add Part form.
     Implements sceneSwitch method.
     @param event The "Add" part button.
     */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {
        sceneSwitch(event, "AddPart-view.fxml");
    }


    /** Method to switch user to the Add Product form.
     Implements sceneSwitch method.
     @param event The "Add" product button.
     */
    @FXML
    public void onActionAddProduct(ActionEvent event) throws IOException {
        sceneSwitch(event, "AddProduct-view.fxml");
    }


    /** Method to delete a selected part from the partTableView.
      Includes a warning alert to require confirmation of deletion.
      @param event The "Delete" part button.
     */
    @FXML
    public void onActionDeletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part. Please confirm.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
        }
    }


    /** Method to delete a selected part from the productTableView.
     Includes a warning alert to require confirmation of deletion.
     @param event The "Delete" product button.
     */
    @FXML
    public void onActionDeleteProduct(ActionEvent event) {
        if (!(productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selected product can not be deleted due to it having associated parts");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected product. Please confirm.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            }
        }
    }


    /** Method to terminated program.
     @param event The "Exit" button.
     */
    @FXML
    public void onActionExitProgram(ActionEvent event) {
        System.exit(0);
    }


    /** Method to take user to the Modify Part form.
     Sends the selected part's data to the modify part form by loading the ModifyPartController and utilizing the sendPart method.
     @param event The "Modify" part button.
     */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException {

        if(partTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part selected");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart-view.fxml"));
        loader.load();

        ModifyPartController MPController = loader.getController();

        MPController.sendPart(partTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** Method to take user to the Modify Product form.
     Sends the selected product's data to the Modify Product form by loading the ModifyProductController and utilizing the sendProduct method.
     @param event The "Modify" product button.
     */
    @FXML
    public void onActionModifyProduct(ActionEvent event) throws IOException {

        if(productTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No product selected");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct-view.fxml"));
        loader.load();

        ModifyProductController MPController = loader.getController();

        MPController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** Method to search through the Inventory's parts.
      Search results are held in an ObservableList that is initialized within the declared method.
      Automatically sorts results by ID number.
      If partSearchTextField is blank, all parts in the Inventory are displayed.
      Exception handling is utilized to differ between intended ID number inputs and intended part name strings.
      @param event "Enter" or "Return" key is pressed on user keyboard when the partSearchTextField is selected.
     */
    @FXML
    public void onActionSearchParts(ActionEvent event) {

        partTableView.getSortOrder().add(partIdColumn);

        int inputId;
        ObservableList<Part> idResult = FXCollections.observableArrayList();
        String inputName;

        if (Objects.equals(partSearchTextField.getText(), "")){
            partTableView.setItems(Inventory.getAllParts());
            return;
        }

        try{
            inputId = Integer.parseInt(partSearchTextField.getText());
            idResult.add(Inventory.lookUpPart(inputId));
            partTableView.setItems(idResult);
            if(Inventory.lookUpPart(inputId) == null){
                partTableView.getItems().clear();
            }
        }catch(Exception e){
            inputName = partSearchTextField.getText();
            partTableView.setItems(Inventory.lookUpPart(inputName));
            if(Inventory.lookUpPart(inputName).isEmpty()){
            partTableView.getItems().clear();}
        }
    }


    /** Method to search through the Inventory's products.
     Search results are held in an ObservableList that is initialized within the declared method.
     Automatically sorts results by ID number.
     If productSearchTextField is blank, all parts in the Inventory are displayed.
     Exception handling is utilized to differ between intended ID number inputs and intended product name strings.
     @param event "Enter" or "Return" key is pressed on user keyboard when the productSearchTextField is selected.
     */
    @FXML
    public void onActionSearchProducts(ActionEvent event) {

        productTableView.getSortOrder().add(productIdCol);

        int inputId;
        ObservableList<Product> idResult = FXCollections.observableArrayList();
        String inputName;

        if (productSearchTextField.getText() == ""){
            productTableView.setItems(Inventory.getAllProducts());
            return;
        }

        try{
            inputId = Integer.parseInt(productSearchTextField.getText());
            idResult.add(Inventory.lookUpProduct(inputId));
            productTableView.setItems(idResult);
            if(Inventory.lookUpProduct(inputId) == null){
                productTableView.getItems().clear();
            }
        }catch(Exception e){
            inputName = productSearchTextField.getText();
            productTableView.setItems(Inventory.lookUpProduct(inputName));
            if(Inventory.lookUpProduct(inputName).isEmpty()){
                productTableView.getItems().clear();}
        }
    }


    /** Method to initialize the Main Form view tables with data from the Inventory's ObservableList of parts, and it's ObservableList of products.
     Sorted by ID number.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partTableView.setItems(Inventory.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTableView.getSortOrder().add(partIdColumn);


        productTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.getSortOrder().add(productIdCol);
    }
}
