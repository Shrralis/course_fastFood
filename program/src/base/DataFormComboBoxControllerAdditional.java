package base;

import models.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shrralis on 3/16/17.
 */
public abstract class DataFormComboBoxControllerAdditional extends DataFormComboBoxController {
    public final <T extends Owner> void setObjectToSearch(T object) {
        if (objectToProcess instanceof MealToFiliation) {
            setToObjectToProcess(MealToFiliation.class, object);
        } else if (objectToProcess instanceof DrinkToFiliation) {
            setToObjectToProcess(DrinkToFiliation.class, object);
        } else if (objectToProcess instanceof MealToOrder) {
            setToObjectToProcess(MealToOrder.class, object);
        } else {
            setToObjectToProcess(DrinkToOrder.class, object);
        }
        objectToProcess = object;
    }

    private <T extends Owner, E extends Model> void setToObjectToProcess(Class<E> classOfDest, T value) {
        for (Method method : classOfDest.getDeclaredMethods()) {
            if (method.getName().matches("^set" + value.getClass().getSimpleName() + "(\\d|\\D)*$")) {
                try {
                    method.invoke(objectToProcess, value);
                } catch (IllegalAccessException | InvocationTargetException ignored) {}
            }
        }
    }
}
