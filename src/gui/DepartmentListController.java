
package gui;

import application.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

/**
 * FXML Controller class
 *
 * @author Iara Santos
 */
public class DepartmentListController implements Initializable {

    @FXML
    private TableView<Department> tableViewDepartment;
    @FXML
    private Button btNew;
    @FXML
    private TableColumn<Department , Integer> tableColumnId;
    @FXML
    private TableColumn<Department, String> tableColumnName;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void onBtNewAction(ActionEvent event) {
        System.out.println("onBtNewAction");
    }
    
    private void initializeNode() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }
    
}
