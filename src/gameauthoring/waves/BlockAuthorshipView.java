package gameauthoring.waves;

import java.util.ResourceBundle;
import engine.IGame;
import engine.definitions.concrete.SpriteDefinition;
import engine.definitions.spawnerdef.WaveBlockDefinition;
import engine.definitions.spawnerdef.WaveDefinition;
import gameauthoring.util.Glyph;
import gameauthoring.util.UIFactory;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import util.StringParser;


public class BlockAuthorshipView implements Glyph {

    private UIFactory myFactory = new UIFactory();
    private ResourceBundle myStyle = ResourceBundle.getBundle("defaults/styling_class");
    private GridPane myPane = new GridPane();
    private ObservableList<WaveBlockDefinition> myBlockList;
    private TextField myCount;
    private Slider myGap;
    private Button myCreate;
    private ComboBox<SpriteDefinition> mySpriteChoices;

    public BlockAuthorshipView (IGame game, ObservableList<WaveBlockDefinition> list) {
        myPane.getStyleClass().add(myStyle.getString("Block"));
        myCreate = new Button("Create");
        factoryGenerate(game);
        myBlockList = list;
        init();
        new BlockAuthorshipController(this);
    }

    private void factoryGenerate (IGame game) {
        mySpriteChoices = myFactory.createCombo(game.getAuthorshipData().getAllCreatedSprites());
        myCount = myFactory.createTextField();
        myGap = myFactory.createSlider();
    }

    private void init () {
        myPane.add(myFactory.createLabel("Block Wave Creation"), 0, 0);
        myPane.add(myCount, 3, 1);
        myPane.add(mySpriteChoices, 0, 1);
        myPane.add(myGap, 2, 1);
        myPane.add(myCreate, 3, 2);
    }

    @Override
    public Node draw () {
        return myPane;
    }

    public void setOnButtonAction (EventHandler<MouseEvent> event) {
        myCreate.setOnMouseClicked(event);
    }

    public void addToList () {

        try {
            myBlockList.add(getWaveBlockDefinition());
        }
        catch (NumberFormatException e) {
            return;
        }
    }

    private WaveBlockDefinition getWaveBlockDefinition () throws NumberFormatException {
        WaveBlockDefinition myBlock = new WaveBlockDefinition(getSpriteDef(), getCount(), getGap());
        return myBlock;
    }

    private double getGap () {
        return myGap.getValue();
    }

    private SpriteDefinition getSpriteDef () {
        return mySpriteChoices.getSelectionModel().getSelectedItem();
    }

    private int getCount () throws NumberFormatException {
        return new StringParser().parseInt(myCount.getText());
    }

}
