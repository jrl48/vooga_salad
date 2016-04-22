package gameauthoring.levels.sprites;

import engine.definitions.SpriteDefinition;
import engine.rendering.LevelRenderer;
import engine.rendering.UnscaledFactory;
import gameauthoring.creation.cellviews.ProfileCellView;
import gameauthoring.levels.SceneController;
import gameauthoring.util.UIFactory;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;


/**
 * ListView ListCell that allows for dragging of sprites to the screen. Is used by both
 * the authoring environment and the engine for dragging new sprites onto the screen. Eventually
 * this will be extended for the engine to incorporate costs
 * 
 * @author Tommy
 *
 */
public class DraggableSpriteCell extends ProfileCellView<SpriteDefinition> implements Draggable {
    private static final String DRAG_STRING = "Sprite";

    private LevelRenderer myTarget;
    private SceneController myController;

    public DraggableSpriteCell (LevelRenderer target, SceneController controller) {
        myTarget = target;
        myController = controller;
    }

    @Override
    protected Node createSpriteCell (SpriteDefinition profile) {
        Node node = super.createSpriteCell(profile);
        this.setActions(node);
        return node;
    }

    @Override
    public void setOnDragDetected (MouseEvent e, Node node) {
        Dragboard db = node.startDragAndDrop(TransferMode.COPY);
        db.setContent(this.createClipboard(DRAG_STRING));

        db.setDragView(getSpriteImage());

        myTarget.getPane().setOnDragOver(event -> setOnDragOver(event));
        myTarget.getPane().setOnDragDropped(event -> setOnDragDropped(event));
    }
    
    protected SceneController getController () {
        return myController;
    }

    /**
     * Helper to get a the correct image from the sprite
     * 
     * @return
     */
    private Image getSpriteImage () {
        Node spriteNode =
                getProfilable().getProfile().getImage()
                        .getVisualRepresentation(new UnscaledFactory());
        return new UIFactory().getImageFromNode(spriteNode);
    }

    @Override
    public void setOnDragOver (DragEvent e) {
        e.acceptTransferModes(TransferMode.COPY);
    }

    @Override
    public void setOnDragDropped (DragEvent e) {
        if (checkPlaceable(e)) {
            Dragboard db = e.getDragboard();
            if (db.hasString()) {
                myController.addSprite(e.getX(), e.getY(), getProfilable());
                myTarget.render();
            }
        }
        // else
        // "You can't place there. please change placeable view"
    }

    private void printPlaceableArea () {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                System.out
                        .print((myController.getLevel().getPlaceableManager().getPlaceableArea().getBitMap()[i][j]) ? "1"
                                                                                                   : "0");
            }
            System.out.println("");
        }
    }

    // TODO: TEST THIS
    public boolean checkPlaceable (DragEvent e) {
        double halfHeight = (0.5) * (getProfilable().getProfile().getImageHeight().doubleValue());
        double halfWidth = (0.5) * (getProfilable().getProfile().getImageWidth().doubleValue());
        printPlaceableArea();
        for (int r = (int) (e.getY() - halfHeight); r < (int) (e.getY() + halfHeight); r++) {
            for (int c = (int) (e.getX() - halfWidth); c < (int) (e.getX() + halfWidth); c++) {
                if (myController.getLevel().getPlaceableManager().getPlaceableArea().getBitMap()[r][c])
                    return false;
                System.out.println("Row: " + r + " Column: " + c);
                System.out.println(myController.getLevel().getPlaceableManager().getPlaceableArea()
                        .getBitMap()[r][c]);
            }
        }
        System.out.println("You can place it!");
        return true;
    }
}
