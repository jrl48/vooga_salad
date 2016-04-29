package gameauthoring.creation.subforms.fire;

import java.util.List;
import gameauthoring.creation.subforms.ClickAndFillView;
import gameauthoring.creation.subforms.ISubFormView;
import javafx.scene.control.Label;



/**
 * Implementation of IFiringSFVmult with button display
 * 
 * @author Joe Lilien
 *
 */
public class FiringSFV extends ClickAndFillView {

    public FiringSFV (List<String> options) {
        super(options);
        initView();
    }

    @Override
    public void addOrSetSFV (ISubFormView subFormView) {
        super.getMyPaneContent().getChildren().add(subFormView.draw());
    }

    @Override
    public void showDefaultMessage () {
        getMyPaneContent().getChildren().add(new Label("ADD HERE"));//TODO: resource lang file
    }
    
   

}
