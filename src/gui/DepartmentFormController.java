
package gui;

import gui.util.Constraints;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

/**
 * FXML Controller class
 *
 * @author Iara Santos
 */
public class DepartmentFormController implements Initializable {
    //create dependy to Department
    private Department entity;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private Label labelErrorName;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    } 
    
    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    @FXML
    private void onBtSaveAction(ActionEvent event) {
        System.out.println("onBtSaveAction");
    }

    @FXML
    private void onBtCancelAction(ActionEvent event) {
        System.out.println("onBtCancelAction");
    }
    public void setDepartment(Department entity){
        this.entity = entity;
    }
    
    //method to populate form
    public void updateFormDate(){
        //defensive action
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        //convert int to int to string
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}
