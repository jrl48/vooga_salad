package gameauthoring.creation.subforms;

import java.util.ArrayList;
import java.util.List;
import engine.IGame;
import engine.definitions.SpriteDefinition;
import gameauthoring.util.UIFactory;
import engine.profile.IProfilable;
import gameauthoring.creation.factories.DynamicSFCFactory;


/**
 * This in an abstract class for an SFC that needs to dynamically change its
 * sub-subSFC and sub-subview based on user input
 * 
 * @author Jeremy Schreck
 *
 */
public abstract class DynamicSubFormController<T extends IProfilable> implements ISubFormController<T> {
    private DynamicSubFormView myView;
    private List<ISubFormView> mySubFormViews;
    private List<ISubFormController<T>> mySubFormControllers;
    private ISubFormController<T> myCurrentSubFormController;
    private IGame myGame;
    private DynamicSFCFactory<T> mySFCFactory;

    /**
     * Constructor
     * 
     * @param game The current game object
     * @param sfcFactory A factory class for creating new sub-subforms
     * @param subFormIDs A list of strings containing the IDs to specify which sub-subforms to
     *        create
     */
    public DynamicSubFormController (IGame game,
                                     DynamicSFCFactory<T> sfcFactory,
                                     List<String> subFormIDs) {
        setMyGame(game);
        setMySFCFactory(sfcFactory);
        setUpSubFormControllers(subFormIDs);
        setMyCurrentSFC(0);//TODO: magic number
        setUpSubFormViews(mySubFormControllers);
    }

    /**
     * Creates the sub-SFCs that will be dynamically swapped in and out based on
     * which type the user selects
     * 
     * @param subFormIDs A list of strings identifying which sub-SFCs to create
     */
    protected void setUpSubFormControllers (List<String> subFormIDs) {
        List<ISubFormController<T>> subFormControllers = new ArrayList<>();
        for (String subFormID : subFormIDs) {
            ISubFormController<T> sfc =
                    getMySFCFactory().createSubFormController(subFormID);
            subFormControllers.add(sfc);
        }

        setMySubFormControllers(subFormControllers);
    }

    /**
     * Sets which sub-SFC should be currently active (only used for initialization right now)
     * 
     * TODO: make consistent with view
     */
    protected void setMyCurrentSFC (int index) {
        setMyCurrentSubFormController(getMySubFormControllers().get(0));

    }

    /**
     * Creates the list of sub-subformviews from the sub-subformcontrollers
     * 
     * @param subFormControllers A list of sub-subformcontrollers
     */
    private void setUpSubFormViews (List<ISubFormController<T>> subFormControllers) {
        mySubFormViews = new ArrayList<>();
        for (ISubFormController<T> sfc : subFormControllers) {
            mySubFormViews.add(sfc.getSubFormView());
        }

    }

    /**
     * Event handler for changing selection. Changes which sub-SFC is currently shown
     * 
     * @param comboSelectionIndex The selected index
     */
    protected void changeSelection (int comboSelectionIndex) {
        myCurrentSubFormController = mySubFormControllers.get(comboSelectionIndex);
        myView.changeSubView(comboSelectionIndex);

    }

    @Override
    public void initializeFields () {
        for (ISubFormController<T> subFormController : mySubFormControllers) {
            subFormController.initializeFields();
        }
    }

    @Override
    public void updateItem (T item) {
        myCurrentSubFormController.updateItem(item);

    }

    @Override
    public ISubFormView getSubFormView () {
        return myView;
    }

    // Getters and Setters
    protected List<ISubFormView> getMySubFormViews () {
        return mySubFormViews;
    }

    protected void setMySubFormControllers (List<ISubFormController<T>> subFormControllers) {
        this.mySubFormControllers = subFormControllers;
    }

    protected List<ISubFormController<T>> getMySubFormControllers () {
        return this.mySubFormControllers;
    }

    protected void setMyCurrentSubFormController (ISubFormController<T> subFormController) {
        this.myCurrentSubFormController = subFormController;

    }

    protected void setMyView (DynamicSubFormView view) {
        this.myView = view;
    }

    protected IGame getMyGame () {
        return myGame;
    }

    private void setMyGame (IGame myGame) {
        this.myGame = myGame;
    }

    public void setMySFCFactory (DynamicSFCFactory<T> sfcFactory) {
        this.mySFCFactory = sfcFactory;

    }

    private DynamicSFCFactory<T> getMySFCFactory () {
        return mySFCFactory;
    }

}
