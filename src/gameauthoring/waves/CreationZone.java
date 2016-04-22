package gameauthoring.waves;

import gameauthoring.util.Glyph;
import gameauthoring.util.UIFactory;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class CreationZone implements Glyph {

    private HBox myPane = new HBox();
    private UIFactory myFactory = new UIFactory();
    private TextField myWaveName;
    private Button myActionButton;
    private Button mySaveButton;

    public CreationZone () {
        init();
    }

    private void init () {

        myWaveName = myFactory.createTextField();
        myActionButton = myFactory.createButton("Add wave");
        mySaveButton = myFactory.createButton("Save");
        mySaveButton.setVisible(false);
        myPane.getChildren().add(myWaveName);
        myPane.getChildren().add(myActionButton);
        myPane.getChildren().add(mySaveButton);

    }

    @Override
    public Node draw () {
        return myPane;
    }

    public String getText () {
        return myWaveName.getText();
    }

    public void setButtonAction (EventHandler<MouseEvent> event) {
        myActionButton.setOnMouseClicked(event);        
    }
    
    public void setSaveButtonAction (EventHandler<MouseEvent> event) {
        mySaveButton.setOnMouseClicked(event);        
    }

    public void enterEdit () {
       myActionButton.setText("Exit edit mode"); 
       mySaveButton.setVisible(true);
    }
    
    public void exitEdit () {
        myActionButton.setText("Add wave");    
        mySaveButton.setVisible(false);
     }

}
