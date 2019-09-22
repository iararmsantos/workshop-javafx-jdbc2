
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author Iara Santos
 */
public class MainViewController implements Initializable {

    @FXML
    private MenuItem MenuItemSeller;
    @FXML
    private MenuItem MenuItemDepartment;
    @FXML
    private MenuItem MenuItemAbout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void onMenuItemSellerAction(ActionEvent event) {
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    private void onMenuItemDepartmentAction(ActionEvent event) {
        System.out.println("onMenuItemDepartmentAction");
    }

    @FXML
    private void onMenuItemAboutAction(ActionEvent event) {
        System.out.println("onMenuItemAboutAction");
    }
    
}
