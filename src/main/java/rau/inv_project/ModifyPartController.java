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

/** Controller for the Inventory's Modify Part form. */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TextField idTextField;

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


    /** Method for saving a part's data modifications to the Inventory.
     Sequence of if/else statements and exception handlers are utilized to ensure proper data values are entered into the proper text fields.
     An exception counter is utilized in each exception event to prevent any number of improper data entries.
     If there is no exceptions, one final if/else statement is utilized in order to determine the proper constructor for Outsourced vs Inhouse parts.
     Initial object is deleted and replaced with a new object with the updated data.
     Exception labels are reset upon each event to reflect any changes in user input.
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

        int partId = Integer.parseInt(idTextField.getText());
        String saveName = "";
        int saveInv = 0;
        double savePrice = 0;
        int saveMax = 0;
        int saveMin = 0;
        int saveMachineId = 0;
        String saveCompanyName = "";

        if (Objects.equals(nameTextField.getText(), "")) {
            nameExceptionLabel.setText("Name must have input");
            exceptCount++;
        } else {
            saveName = nameTextField.getText();
        }

        try {
            saveInv = Integer.parseInt(invTextField.getText());
        } catch (Exception e) {
            invExceptionLabel.setText("Inv must be an integer");
            exceptCount++;
        }

        try {
            savePrice = Double.parseDouble(priceTextField.getText());
        } catch (Exception e) {
            priceExceptionLabel.setText("Price must be a decimal number");
            exceptCount++;
        }

        try {
            saveMax = Integer.parseInt(maxTextField.getText());
        } catch (Exception e) {
            maxExceptionLabel.setText("Max must be an integer");
            exceptCount++;
        }

        try {
            saveMin = Integer.parseInt(minTextField.getText());
        } catch (Exception e) {
            minExceptionLabel.setText("Min must be an integer");
            exceptCount++;
        }

        if (saveMin > saveMax) {
            lessThanExceptionLabel.setText("Max must be greater than Min");
            exceptCount++;
        }

        if (saveInv > saveMax || saveInv < saveMin) {
            invInbetweenExceptionLabel.setText("Inv must be between Min and Max");
            exceptCount++;
        }

        if (inHouseRadButton.isSelected()) {
            try {
                saveMachineId = Integer.parseInt(machineCNameTextField.getText());
            } catch (Exception e) {
                machineCNameExceptLabel.setText("Machine ID must be an integer");
                exceptCount++;
            }
        }

        if (outsourcedRadButton.isSelected()) {
            if (Objects.equals(machineCNameTextField.getText(), "")) {
                machineCNameExceptLabel.setText("Company Name must have input");
                exceptCount++;
            } else {
                saveCompanyName = machineCNameTextField.getText();
            }
        }



        if (exceptCount == 0) {
            if (inHouseRadButton.isSelected()) {
                Inventory.deletePart(Inventory.lookUpPart(partId));
                Inventory.addPart(new InHouse(partId, saveName, savePrice, saveInv, saveMin, saveMax, saveMachineId));
                Inventory.retainPartIdCounter();
                sceneSwitch(event, "MainForm-view.fxml");
            } else {
                Inventory.deletePart(Inventory.lookUpPart(partId));
                Inventory.addPart(new Outsourced(partId, saveName, savePrice, saveInv, saveMin, saveMax, saveCompanyName));
                Inventory.retainPartIdCounter();
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


    /** Method for initializing the Modify part form with a specific part's data.
     Utilized by the MainFormController in order to send information from the selected part in the partTableView to the Modify Part form.
     If/else statement is utilized to distinguish between Inhouse and Outsourced parts.
     @param part The chosen part from the Main Form.
     */
    public void sendPart(Part part){
        idTextField.setText(String.valueOf(part.getId()));
        nameTextField.setText(String.valueOf(part.getName()));
        invTextField.setText(String.valueOf(part.getStock()));
        priceTextField.setText(String.valueOf(part.getPrice()));
        maxTextField.setText(String.valueOf(part.getMax()));
        minTextField.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse){
            machineCNameTextField.setText((String.valueOf (((InHouse) part).getMachineId())));
            inHouseRadButton.setSelected(true);
        }else if(part instanceof Outsourced){
            machineCNameTextField.setText(((Outsourced) part).getCompanyName());
            outsourcedRadButton.setSelected(true);
            machineCompLabel.setText("Company Name");
        }
    }


    /** Method to initialize the Modify Part form.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
