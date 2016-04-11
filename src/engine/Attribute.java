package engine;

import java.util.ArrayList;
import java.util.List;
import engine.effects.DefaultAffectable;
import engine.effects.IEffect;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.TimeDuration;


/**
 * This class serves to hold values that the user labels as attributes. This class works with the
 * Sprite class
 * to give user created sprites the notion of attributes that are affected by conditions and events.
 *
 */

public class Attribute extends DefaultAffectable implements IAttribute {
    private static final double DEFAULT_STARTING_VALUE = 0;
    private DoubleProperty myValue;
    private DoubleProperty myMaxValue;
    private DoubleProperty myMinValue;
    private BooleanProperty myIsGlobal;
    private AttributeType myType;
    private ObservableList<ObjectProperty<IEffect>> myEffects;
    

    public Attribute (List<Double> listOfValues, boolean isGlobal, AttributeType type){
        myMaxValue = new SimpleDoubleProperty(listOfValues.get(0));
        myMinValue = new SimpleDoubleProperty(listOfValues.get(1));
        myIsGlobal = new SimpleBooleanProperty(isGlobal);
    }
    
    public Attribute (AttributeType type) {
        this(DEFAULT_STARTING_VALUE, type);
    }

    public Attribute (double value, AttributeType type) {
        myValue = new SimpleDoubleProperty(value);
        myType = type;
        myEffects = FXCollections.observableArrayList();
    }

    @Override
    public void applyEffect (IEffect effect) {
        myEffects.add(new SimpleObjectProperty<>(effect));
    }

    @Override
    public AttributeType getType () {
        return myType;
    }

    @Override
    public DoubleProperty getValueProperty () {
        return myValue;
    }

    @Override
    public void setValue (double valueToSet) {
        myValue.set(valueToSet);
    }


    @Override

    public List<IAttribute> getAttributes () {
        List<IAttribute> attributes = new ArrayList<>();
        attributes.add(this);
        return attributes;
    }

    @Override
    public void update (TimeDuration duration) {
        myEffects.forEach(e -> e.get().applyToAttribute(this));
        myEffects.forEach(e -> e.get().update(duration));
        removeCompletedEffects(duration);

        System.out.print(myType.getType() + " ");
        System.out.println(myValue.get());
    }

    /**
     * Removes time or condition dependent effects that are invalid or have
     * expired
     *
     * @param duration frame rate specified by the level
     */
    private void removeCompletedEffects (TimeDuration duration) {
        myEffects.removeIf(e -> e.get().hasCompleted());
    }

    @Override
    public ObservableList<ObjectProperty<IEffect>> getEffects () {
        return myEffects;
    }

}
