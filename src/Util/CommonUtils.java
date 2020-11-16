package Util;

import java.io.Serializable;
import java.util.ArrayList;

public class CommonUtils implements Serializable {

    public static final int size = 20;
    ArrayList<ArrayList<Integer>> snakeBody;
    ArrayList<Integer> foodLocation;

    public CommonUtils() {
        snakeBody = new ArrayList<ArrayList<Integer>>();
    }

    public int getSize() {
        return size;
    }

    public ArrayList<ArrayList<Integer>> getSnakeBody() {
        return snakeBody;
    }

    public void addElement(ArrayList<Integer> addArray) {
        snakeBody.add(addArray);
    }

    public void deleteFirstElement() {
        snakeBody.remove(0);
    }

    public void clearElement() {
        snakeBody.clear();
        foodLocation = null;
    }

    public void setFoodLocation(ArrayList<Integer> _foodLocation) {
        foodLocation = _foodLocation;
    }

    public ArrayList<Integer> getFoodLocation() {
        return foodLocation;
    }
}
