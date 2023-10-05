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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the Inventory's Modify Product Form. */
public class ModifyProductController implements Initializable {

    Stage stage;

    Parent scene;

    ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private Label invExceptionLabel;

    @FXML
    private Label invInbetweenExceptionLabel;

    @FXML
    private Label lessThanExceptionLabel;

    @FXML
    private Label maxExceptionLabel;

    @FXML
    private Label minExceptionLabel;

    @FXML
    private Label nameExceptionLabel;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

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
    private Label priceExceptionLabel;

    @FXML
    private TextField productInvTextField;

    @FXML
    private TextField productMaxTextField;

    @FXML
    private TextField productMinTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField productPriceTextField;

    @FXML
    private TextField productIdTextField;


    /** Method for switching screens.
     Implemented in order to reduce redundant code and increase readability.
     @param event Any event that is meant to trigger a scene change.
     @param source String that specifies location of the desired fxml file.
     */
    public void sceneSwitch(ActionEvent event, String source) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(source));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** Method for adding parts from the Inventory to a product's list of associated parts.
     RUNTIME ERROR: It was possible to add null parts to the associated list. Fixed by adding an if statement that checked for null parts.
     @param event The "Add" button.
     */
    @FXML
    public void onActionAddPart(ActionEvent event) {
        if (!(partTableView.getSelectionModel().getSelectedItem() == null)) {
            associatedPartList.add(partTableView.getSelectionModel().getSelectedItem());
            associatedPartTableView.getSortOrder().add(partIdCol);
        }
    }


    /** Method for canceling a modification and returning the user to the main form.
     Utilizes a confirmation box to help prevent the user from accidentally deleting any information entered.
     @param event The "Cancel" button.
     */
    @FXML
    public void onActionCancelModify(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No changes will be saved. Do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            sceneSwitch(event, "MainForm-view.fxml");
        }
    }


    /** Method for removing a part from a product's associated part's list.
     Utilizes object methods to remove parts. Includes a Confirmation alert to prevent any accidental part removals.
     RUNTIME ERROR: Confirmation Alert was still appearing even when no part was selected. Fixed by adding an if statement to check if any item was selected.
     @param event The "Remove Associated Part" button.
     */
    @FXML
    public void onActionRemoveAssociatedPart(ActionEvent event) {

        if (associatedPartTableView.getSelectionModel().getSelectedItem() == null){
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the selected part. Please confirm.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedPartList.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
            associatedPartTableView.getSortOrder().add(partIdCol);
        }
    }


    /** Method for adding a new part to the Inventory.
     Sequence of if/else statements and exception handlers are utilized to ensure proper data values are entered into the proper text fields.
     An exception counter is utilized in each exception event to prevent any number of improper data entries.
     Advanced for loop is utilized to save parts to the updated Product's associated parts list.
     Product object is updated directly instead of being replaced with new object.
     Exception labels are reset upon each event to reflect any changes in user input.
     @param event The "Save" button.
     */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException {

        int exceptCount = 0;


        nameExceptionLabel.setText("");
        invExceptionLabel.setText("");
        priceExceptionLabel.setText("");
        maxExceptionLabel.setText("");
        minExceptionLabel.setText("");
        lessThanExceptionLabel.setText("");
        invInbetweenExceptionLabel.setText("");

        int saveId = Integer.parseInt(productIdTextField.getText());
        String saveName = "";
        int saveInv = 0;
        double savePrice = 0;
        int saveMax = 0;
        int saveMin = 0;


        if (Objects.equals(productNameTextField.getText(), "")) {
            nameExceptionLabel.setText("Name must have input");
            exceptCount++;
        } else {
            saveName = productNameTextField.getText();
        }

        try {
            saveInv = Integer.parseInt(productInvTextField.getText());
        } catch (Exception e) {
            invExceptionLabel.setText("Inv must be an integer");
            exceptCount++;
        }

        try {
            savePrice = Double.parseDouble(productPriceTextField.getText());
        } catch (Exception e) {
            priceExceptionLabel.setText("Price must be a decimal number");
            exceptCount++;
        }

        try {
            saveMax = Integer.parseInt(productMaxTextField.getText());
        } catch (Exception e) {
            maxExceptionLabel.setText("Max must be an integer");
            exceptCount++;
        }

        try {
            saveMin = Integer.parseInt(productMinTextField.getText());
        } catch (Exception e) {
            minExceptionLabel.setText("Min must be an integer");
            exceptCount++;
        }

        if (saveMin > saveMax) {
            lessThanExceptionLabel.setText("Max must be greater than Min");
            exceptCount++;
        }

        if( saveInv > saveMax || saveInv < saveMin){
            invInbetweenExceptionLabel.setText("Inv must be between Min and Max");
            exceptCount++;
        }


        if (exceptCount == 0){
            Inventory.lookUpProduct(saveId).setName(saveName);
            Inventory.lookUpProduct(saveId).setPrice(savePrice);
            Inventory.lookUpProduct(saveId).setStock(saveInv);
            Inventory.lookUpProduct(saveId).setMax(saveMax);
            Inventory.lookUpProduct(saveId).setMin(saveMin);

            Inventory.lookUpProduct(saveId).getAllAssociatedParts().clear();

            for(Part part : associatedPartList){
                Inventory.lookUpProduct(saveId).addAssociatedPart(part);
            }

            sceneSwitch(event, "MainForm-view.fxml");
        }
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

        partTableView.getSortOrder().add(partIdCol);

        int inputId;
        ObservableList<Part> idResult = FXCollections.observableArrayList();
        String inputName;

        if (partSearchTextField.getText() == ""){
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

    /** Method to initialize the Modify Product Form view tables with data from the Inventory's ObservableList of parts, and the specified Product's list of associated parts.
     Sorted by ID number.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTableView.getSortOrder().add(partIdCol);

        associatedPartTableView.setItems(associatedPartList);

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTableView.getSortOrder().add(partIdCol);
    }


    /** Method for initializing the Modify Product form with a specific product's data.
     Utilized by the MainFormController in order to send information from the selected product in the productTableView to the Modify Product form.
     Enhanced for loop is utilized to fill the associatedPartTableView.
     @param product The chosen product from the Main Form.
     */
    public void sendProduct(Product product) {
        productIdTextField.setText(String.valueOf(product.getId()));
        productNameTextField.setText(String.valueOf(product.getName()));
        productInvTextField.setText(String.valueOf(product.getStock()));
        productPriceTextField.setText(String.valueOf(product.getPrice()));
        productMaxTextField.setText(String.valueOf(product.getMax()));
        productMinTextField.setText(String.valueOf(product.getMin()));

        for(Part part : product.getAllAssociatedParts()){
            associatedPartList.add(part);
        }
    }
}
