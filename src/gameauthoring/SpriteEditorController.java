package gameauthoring;

import java.util.List;

public interface SpriteEditorController {
    /**
     * Has an observable list of sprites
     */
    void setSprites(SpriteHolder sprites);
    
    void showAndEdit();
    
    void createBlankSprite(); // Empty sprite
    
    void deleteSprite();
    
    void editSprite(List<SubFormView> subForms); 
}
