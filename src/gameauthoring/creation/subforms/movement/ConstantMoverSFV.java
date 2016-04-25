package gameauthoring.creation.subforms.movement;

import gameauthoring.creation.entryviews.IEntryView;
import gameauthoring.creation.entryviews.NumberEntryView;
import gameauthoring.creation.subforms.SubFormView;
import gameauthoring.tabs.AuthoringView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;


/**
 * View representing a sub form that creates the information required to build a constant mover
 * module
 * 
 * @author Joe Lilien
 * @author Dhrumil
 *
 */
public class ConstantMoverSFV extends SubFormView implements IConstantMoverSFV {

    private HBox myPane;
    private String mySpeedKey = "Speed: ";
    private String myOrientationKey = "Initial Orientation: ";
    private IEntryView mySpeed;
    private IEntryView myOrientation;

    public ConstantMoverSFV () {
        mySpeed =
                new NumberEntryView(mySpeedKey, this.getData(), 150, 30,
                                    AuthoringView.DEFAULT_ENTRYVIEW);
        myOrientation =
                new NumberEntryView(myOrientationKey, this.getData(), 150, 30,
                                    AuthoringView.DEFAULT_ENTRYVIEW);
        initView();
    }

    @Override
    public Node draw () {
        return myPane;
    }

    @Override
    protected void initView () {
        myPane = getMyUIFactory().makeHBox(20, Pos.CENTER, mySpeed.draw(), myOrientation.draw());
        myPane.getStyleClass().add("mover");
    }

    @Override
    public String getMySpeedKey () {
        return mySpeedKey;
    }

    @Override
    public String getMyOrientationKey () {
        return myOrientationKey;
    }

}