
package gui;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

/**
 * FXML Controller class
 *
 * @author Iara Santos
 */
public class DepartmentFormController implements Initializable {
    //create depency to Department
    private Department entity;
    
    //create depency to DepartmentService
    private DepartmentService service;

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
        if(entity == null){
            throw new IllegalStateException("Entity was null.");
        }
        if(service == null){
            throw new IllegalStateException("Service was null.");
        }
        try{            
       
        entity = getFormData();
        
        service.saveOrUpdate(entity);
        
        Utils.currentStage(event).close();
        }catch(DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }
    public void setDepartment(Department entity){
        this.entity = entity;
    }
    
    public void setDepartmentService(DepartmentService service){
        this.service = service;
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
    //metodo que transforma dados inserido no form e cria novo objeto no Department
    private Department getFormData() {
        Department obj = new Department();
        
        //utiliza o metodo parse que criamos no Utils
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        obj.setName(txtName.getText());
        
        return obj;
    }
}
