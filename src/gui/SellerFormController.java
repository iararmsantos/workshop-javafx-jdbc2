
package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

/**
 * FXML Controller class
 *
 * @author Iara Santos
 */
public class SellerFormController implements Initializable {
    //create depency to Seller
    private Seller entity;
    
    //create depency to SellerService
    private SellerService service;
    
    //add dependency DepartmentService
    private DepartmentService departmentService;
    
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
    @FXML
    private Label labelErrorEmail;
    @FXML
    private Label labelErrorBirthDate;
    @FXML
    private Label labelErrorBaseSalary;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtBaseSalary;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private ComboBox<Department> comboBoxDepartment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    } 
    
    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
        initializeComboBoxDepartment();
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
    
    private ObservableList<Department> obsList;
    
    public void setSeller(Seller entity){
        this.entity = entity;
    }
    
    public void setServices(SellerService service, DepartmentService departmentService){
        this.service = service;
        this.departmentService = departmentService;
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
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        
        if(entity.getBirthDate() != null){
            dpBirthDate.setValue(LocalDateTime.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
        }
        if(entity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().selectFirst();
        }
        else
        {
            comboBoxDepartment.setValue(entity.getDepartment());
        
        }
    }
    
    public void loadAssociatedObjects(){
        if(departmentService == null){
            throw new IllegalStateException("DepartmentService was null");
        }
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }
    
    //metodo responsavel por pegar a excessao e lanvcar na label
    private void setErrorMessages(Map<String, String> error){
        Set<String> fields = error.keySet();
        
        if(fields.contains("name")){
            labelErrorName.setText(error.get("name"));
        }
    }
    
    //metodo que transforma dados inserido no form e cria novo objeto no Seller
    private Seller getFormData() {
        Seller obj = new Seller();
        
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
    
    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
