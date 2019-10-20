
package gui;

import application.Main;
import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

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
    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();
            
            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());
            
            T controller = loader.getController();
            initializingAction.accept(controller);
        }catch(IOException e){
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void onMenuItemSellerAction(ActionEvent event) {
        loadView("/gui/SellerList.fxml", (SellerListController controller) ->{
        controller.setSellerService(new SellerService());
        controller.updateTableView();
        });
    }

    @FXML
    private void onMenuItemDepartmentAction(ActionEvent event) {
        loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) ->{
        controller.setDepartmentService(new DepartmentService());
        controller.updateTableView();
        });
    }

    @FXML
    private void onMenuItemAboutAction(ActionEvent event) {
        loadView("/gui/About.fxml", x -> {});
    }   
    
}
