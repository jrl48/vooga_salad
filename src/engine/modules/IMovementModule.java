package engine.modules;

import engine.IPositionable;

/**
 * This interface represents a module of a sprite that handles its movement and positioning
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 *
 */
public interface IMovementModule extends IModule {

  
    public IPositionable getParent ();
}
