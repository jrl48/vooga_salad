package gameauthoring.creation.subforms.costs;

import java.util.ResourceBundle;
import splash.LocaleManager;
import engine.AuthorshipData;
import engine.definitions.concrete.AttributeDefinition;
import gameauthoring.creation.entryviews.NumberEntryView;
import gameauthoring.creation.entryviews.SingleChoiceEntryView;
import gameauthoring.creation.subforms.SubFormView;
import gameauthoring.tabs.AuthoringView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Implementation of ICostSFV with HBox arrangement and combobox layout
 * 
 * @author Tommy
 *
 */
public class CostSFV extends SubFormView implements ICostSFV {


    private static final int SPACING = 5;
    private String myAttributeChoicesKey;
    private String myCostKey;
    private SingleChoiceEntryView<AttributeDefinition> myAttributes;
    private NumberEntryView myCost;
    private HBox myContainer;
    private CheckBox myCheckBox;
    private ResourceBundle myLabel;
    private TitledPane myTitledPane;

    public CostSFV (AuthorshipData data) {
        setResourceBundleAndKey();

        myAttributes =
                new SingleChoiceEntryView<AttributeDefinition>(myAttributeChoicesKey,
                                                               data.getMyCreatedGlobals()
                                                                       .getItems(),
                                                               AuthoringView.DEFAULT_ENTRYVIEW);
        myCost =
                new NumberEntryView(myCostKey, 60, 20, AuthoringView.DEFAULT_ENTRYVIEW);

        initView();
    }

    private void setResourceBundleAndKey () {
        myLabel = ResourceBundle.getBundle("languages/labels", LocaleManager
                .getInstance().getCurrentLocaleProperty().get());
        myAttributeChoicesKey = myLabel.getString("ResourceChoice");
        myCostKey = myLabel.getString("CostKey");
    }

    @Override
    public Node draw () {
        return myTitledPane;
    }

    @Override
    protected void initView () {
        myContainer = getMyUIFactory().makeHBox(SPACING, Pos.CENTER, drawFields());
        myTitledPane = getMyUIFactory().makeCheckBoxTitledPane(myLabel.getString("CostCheck"), myContainer, false);
        
    }

    private Node drawFields () {
        HBox box = new HBox(SPACING);
        box.getChildren().addAll(myAttributes.draw(),
                                 myCost.draw());
        return box;
    }

    public AttributeDefinition getSelectedAttribute () {
        return myAttributes.getSelected();
    }

    @Override
    public double getCost () {
        return myCost.getData();
    }
    
    @Override

    public boolean costChecked () {
        return myTitledPane.isExpanded();
    }

    @Override
    public void populateWithData (AttributeDefinition attribute, double cost) {
        myAttributes.setSelected(attribute);
        myCost.setData(cost);
    }
}
