
package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
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
    
    //aceitar inscricao dos elementos que irao receber event
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

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
        
        //metodo notifica listeners
        notifyDataChangeListeners();
        
        Utils.currentStage(event).close();
        
        }
        catch(ValidationException e){
            setErrorMessages(e.getErrors());
        }        
        catch(DbException e){
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
    
    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
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
    //metodo responsavel por pegar a excessao e lanvcar na label
    private void setErrorMessages(Map<String, String> error){
        Set<String> fields = error.keySet();
        
        if(fields.contains("name")){
            labelErrorName.setText(error.get("name"));
        }
    }
    
    //metodo que transforma dados inserido no form e cria novo objeto no Department
    private Department getFormData() {
        Department obj = new Department();
        
        ValidationException exception = new ValidationException("Validation Exception");
        
        //utiliza o metodo parse que criamos no Utils
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        
        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());
        
        if(exception.getErrors().size() > 0){
            throw exception;
        }
        
        return obj;
    }

    private void notifyDataChangeListeners() {
        for(DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }
}
