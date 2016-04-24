package gameauthoring.creation.subforms.fire;

import engine.definitions.FirerDefinition;
import engine.definitions.SpriteDefinition;
import gameauthoring.creation.subforms.ISubFormControllerSprite;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


/**
 * 
 * 
 * @author Joe Lilien
 *
 */
public abstract class RemovableSpriteSFC implements ISubFormControllerSprite {

    private SpriteDefinition mySpriteDefinition;
    private RemoveOption myView;

    public RemovableSpriteSFC (FiringSFCmult sfc) {
        myView = new RemoveOption(e->sfc.removeSFC(this));
    }


    public void removeModule (FirerDefinition myFireDef) {
        if (mySpriteDefinition != null) {
            if (mySpriteDefinition.getModuleDefinitions().contains(myFireDef)) {
                mySpriteDefinition.remove(myFireDef);
            }
        }
    }

    public abstract FirerDefinition getFirerDefinition ();

    protected RemoveOption getRemoveMenu(){
        return myView;
    }
    
    protected void setMySpriteDefinition (SpriteDefinition mySpriteDefinition) {
        this.mySpriteDefinition = mySpriteDefinition;
    }

}
