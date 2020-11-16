import Util.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author FengJiale
 * @date 2020/11/15 11:09
 */

public class Food implements Serializable {

    CommonUtils commonUtils;
    ArrayList<Integer> foodLocation;
    int size;

    public Food(CommonUtils _commonUtils) {
        commonUtils = _commonUtils;
        size = commonUtils.getSize();
    }

    public void creatFood() {
        ArrayList<ArrayList<Integer>> snakeBody = commonUtils.getSnakeBody();
        while (true) {
            boolean flag = true;
            int x = (int) (Math.random() * (size - 2) + 1);
            int y = (int) (Math.random() * (size - 2) + 1);
            for (ArrayList<Integer> element : snakeBody) {
                if (x == element.get(0) && y == element.get(1)) {
                    flag = false;
                }
            }
            if (flag) {
                if (foodLocation == null) {
                    foodLocation = new ArrayList<Integer>();
                } else {
                    foodLocation.clear();
                }
                foodLocation.add(x);
                foodLocation.add(y);
                addFoodToUtil();
                break;
            }
        }
    }

    public void addFoodToUtil() {
        commonUtils.setFoodLocation(foodLocation);
    }

    public ArrayList<Integer> getFoodLocation() {
        return commonUtils.getFoodLocation();
    }

}
