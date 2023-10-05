package rau.inv_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the Inventory's Add Part form.
 FUTURE ENHANCEMENT: Add functionality to TextField's so that pressing the "Enter" key automatically selects the next TextField for the user.
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRadButton;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField machineCNameTextField;

    @FXML
    private Label machineCompLabel;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private RadioButton outsourcedRadButton;

    @FXML
    private ToggleGroup partType;

    @FXML
    private TextField priceTextField;

    @FXML
    private Label nameExceptionLabel;
    @FXML
    private Label invExceptionLabel;
    @FXML
    private Label priceExceptionLabel;
    @FXML
    private Label maxExceptionLabel;
    @FXML
    private Label minExceptionLabel;
    @FXML
    private Label lessThanExceptionLabel;
    @FXML
    private Label machineCNameExceptLabel;

    @FXML
    private Label invInbetweenExceptionLabel;


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


    /** Method for canceling an add and returning the user to the main form.
     Utilizes a confirmation box to help prevent the user from accidentally deleting any information entered.
     @param event The "Cancel" button.
     */
    @FXML
    public void onActionCancelAdd(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values. Do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            sceneSwitch(event, "MainForm-view.fxml");
        }
    }


    /** Method for adding a new part to the Inventory.
     Sequence of if/else statements and exception handlers are utilized to ensure proper data values are entered into the proper text fields.
     An exception counter is utilized in each exception event to prevent any number of improper data entries.
     If there is no exceptions, one final if/else statement is utilized in order to determine the proper constructor for Outsourced vs Inhouse parts.
     RUNTIME ERROR: Exception labels were permanently displayed even if condition was met after save button was pressed again. Fixed by resetting labels on every event.
     @param event The "Save" button.
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException {
        int exceptCount = 0;

        nameExceptionLabel.setText("");
        invExceptionLabel.setText("");
        priceExceptionLabel.setText("");
        maxExceptionLabel.setText("");
        minExceptionLabel.setText("");
        lessThanExceptionLabel.setText("");
        machineCNameExceptLabel.setText("");

        int saveId = Inventory.getPartIdCounter();
        String saveName = "";
        int saveInv = 0;
        double savePrice = 0;
        int saveMax = 0;
        int saveMin = 0;
        int saveMachineId = 0;
        String saveCompanyName = "";

        if(Objects.equals(nameTextField.getText(), "")){
            nameExceptionLabel.setText("Name must have input");
            exceptCount++;
        }else{
            saveName = nameTextField.getText();
        }

        try{
            saveInv = Integer.parseInt(invTextField.getText());
        }catch(Exception e){
            invExceptionLabel.setText("Inv must be an integer");
            exceptCount++;
        }

        try{
            savePrice = Double.parseDouble(priceTextField.getText());
        }catch(Exception e){
            priceExceptionLabel.setText("Price must be a decimal number");
            exceptCount++;
        }

        try{
            saveMax = Integer.parseInt(maxTextField.getText());
        }catch(Exception e){
            maxExceptionLabel.setText("Max must be an integer");
            exceptCount++;
        }

        try{
            saveMin = Integer.parseInt(minTextField.getText());
        }catch(Exception e){
            minExceptionLabel.setText("Min must be an integer");
            exceptCount++;
        }

        if (saveMin > saveMax){
            lessThanExceptionLabel.setText("Max must be greater than Min");
            exceptCount++;
        }

        if (inHouseRadButton.isSelected()){
            try{
                saveMachineId = Integer.parseInt(machineCNameTextField.getText());
            }catch(Exception e){
                machineCNameExceptLabel.setText("Machine ID must be an integer");
                exceptCount++;
            }
        }

        if (outsourcedRadButton.isSelected()){
            if(Objects.equals(machineCNameTextField.getText(), "")){
                machineCNameExceptLabel.setText("Company Name must have input");
                exceptCount++;
            }else{
                saveCompanyName = machineCNameTextField.getText();
            }
        }

        if (saveInv > saveMax || saveInv < saveMin){
            invInbetweenExceptionLabel.setText("Inv must be between Min and Max");
            exceptCount++;
        }



        if (exceptCount == 0) {
            if (inHouseRadButton.isSelected()) {
                Inventory.addPart(new InHouse(saveId, saveName, savePrice, saveInv, saveMin, saveMax, saveMachineId));
                sceneSwitch(event, "MainForm-view.fxml");
            } else if (outsourcedRadButton.isSelected()) {
                Inventory.addPart(new Outsourced(saveId, saveName, savePrice, saveInv, saveMin, saveMax, saveCompanyName));
                sceneSwitch(event, "MainForm-view.fxml");
            }
        }
    }



    /** Method for changing the label respective of the part type.
     Label is changed to "Company Name" for outsourced parts.
     @param event The "Outsourced" radio button.
     */
    @FXML
    public void onActionSetCompanyNameLabel(ActionEvent event) {
        machineCompLabel.setText("Company Name");
    }


    /** Method for changing the label respective of the part type.
     Label is changed to "Machine ID" for InHouse parts.
     @param event The "InHouse" radio button.
     */
    @FXML
    public void onActionSetPartMachineIdLabel(ActionEvent event) {
        machineCompLabel.setText("Machine ID");
    }


    /** Method to initialize the Add Part form.
     Form is initialized with the InHouse button selected in order to prevent any input errors and improve user readability.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRadButton.setSelected(true);
    }
}
