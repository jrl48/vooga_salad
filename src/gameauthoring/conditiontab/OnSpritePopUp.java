package gameauthoring.conditiontab;

import engine.ICondition;
import engine.IGame;
import javafx.collections.ObservableList;

public class OnSpritePopUp extends ConditionPopUp {

    private IGame myGame;
    
    public OnSpritePopUp (ObservableList<ICondition> conditionList, IGame game) {
        
        super(conditionList);
        myGame = game;
        initStage();
    }

    @Override
    protected void initializeDisplay () {
        // TODO Auto-generated method stub

    }

    @Override
    protected ICondition createCondition () {
        // TODO Auto-generated method stub
        return null;
    }

}
