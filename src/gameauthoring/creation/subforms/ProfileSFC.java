package gameauthoring.creation.subforms;

import engine.profile.IProfilable;
import gameauthoring.creation.entryviews.IFormDataManager;


/**
 * Controls definition of profile for all objects that can be created in authoring, designates a
 * name, description and image for a given profilable object
 * 
 * @author Joe Lilien
 * @author Jeremy Shreck
 *
 * @param <T>
 */
public class ProfileSFC<T extends IProfilable> implements ISubFormController<T> {

    /**
     * **Implementation still up for discussion
     * 
     * We could either have a third party XML reader associate all controllers and views or instead
     * do it like this and just have a get view method so the FormView can just get a list of views.
     * 
     * Hidden Dependencies VS Flexibility
     * 
     * Maybe best solution is to interface or superclass ProfileSubFormView and just have it take
     * any
     * implementation of that in constructor, but still issues to work out (current Implementation)
     * 
     */
    private ProfileSFV myView;
    private IFormDataManager myFormData;
    private String myDefaultName = "<Name>"; // TODO: move strings to resource file
    private String myDefaultDescription = "<Description>";
    private String myDefaultImage = "images/square.png";
    private double myDefaultImageWidth = 50;
    private double myDefaultImageHeight = 50;

    public ProfileSFC () {
        this.myView = new ProfileSFV();
        this.myFormData = myView.getData();
    }

    @Override
    public void initializeFields () {
        populateViewsWithData(myDefaultName, myDefaultDescription, myDefaultImage, myDefaultImageWidth, myDefaultImageHeight);

    }

    private void populateViewsWithData (String name, String desc, String url, double width, double height) {
        myFormData.set(myView.getMyNameKey(), name);
        myFormData.set(myView.getMyDescriptionKey(), desc);
        myFormData.set(myView.getMyImageKey(), url);
        myFormData.set(myView.getMyImageWidthKey(), Double.toString(width));
        myFormData.set(myView.getMyImageHeightKey(), Double.toString(height));
    }

    @Override
    public void updateItem (T item) {
        String name = myFormData.getValueProperty(myView.getMyNameKey()).get();
        String desc = myFormData.getValueProperty(myView.getMyDescriptionKey()).get();
        String url = myFormData.getValueProperty(myView.getMyImageKey()).get();
        double width =
                Double.parseDouble(myFormData.getValueProperty(myView.getMyImageWidthKey()).get());
        double height =
                Double.parseDouble(myFormData.getValueProperty(myView.getMyImageHeightKey()).get());
        item.getProfile().setNew(name, desc, url, width, height);

    }

    @Override
    public ISubFormView getSubFormView () {
        return myView;
    }

}