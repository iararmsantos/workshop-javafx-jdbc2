
package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 *
 * @author Iara Santos
 */
public class Utils {
    public static Stage currentStage(ActionEvent event){
       return (Stage)((Node)event.getSource()).getScene().getWindow();
    }
}
