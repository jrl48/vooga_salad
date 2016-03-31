package engine;

import javafx.beans.property.SimpleObjectProperty;


/**
 * This interface imposes the ability for a class to have a drawing component that is capable of
 * generating a visual representation.
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 *
 */
public interface Drawable {

    /**
     * @return the component for this class that is capable of generating a visual representation
     *         for it.
     */
    SimpleObjectProperty<IGraphicModule> getDrawer ();
}