import Util.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class Snake implements Serializable {

    public final int UP = 1;
    public final int DOWN = -1;
    public final int LEFT = 2;
    public final int RIGHT = -2;

    int size;
    int direction = LEFT;
    int willChangedDirection = LEFT;
    CommonUtils commonUtils;

    public Snake(CommonUtils _common_utils) {
        commonUtils = _common_utils;
        size = commonUtils.getSize();
        ArrayList<Integer> body = new ArrayList<Integer>();
        body.add(size / 2 + 2);
        body.add(size / 2);
        commonUtils.addElement(body);

        body = new ArrayList<Integer>();
        body.add(size / 2 + 1);
        body.add(size / 2);
        commonUtils.addElement(body);

        body = new ArrayList<Integer>();
        body.add(size / 2);
        body.add(size / 2);
        commonUtils.addElement(body);
    }

    public ArrayList<Integer> getHeader() {
        ArrayList<ArrayList<Integer>> snakeBody = commonUtils.getSnakeBody();
        return snakeBody.get(snakeBody.size() - 1);
    }

    public void writeDirection(char s) {
        int tempDirection;
        switch (s) {
            case 'w':
            case 'W':
                tempDirection = UP;
                break;
            case 's':
            case 'S':
                tempDirection = DOWN;
                break;
            case 'a':
            case 'A':
                tempDirection = LEFT;
                break;
            case 'd':
            case 'D':
                tempDirection = RIGHT;
                break;
            default:
                tempDirection = 0;
                break;
        }
        if (tempDirection != 0 && tempDirection + direction != 0) {
            willChangedDirection = tempDirection;
        }
    }

    public void changeDirection() {
        if (willChangedDirection != 0 && direction + willChangedDirection != 0) {
            direction = willChangedDirection;
        }
    }

    public void move(boolean flag) {
        ArrayList<Integer> header = getHeader();
        ArrayList<Integer> newHeader = new ArrayList<Integer>();
        switch (direction) {
            case UP:
                newHeader.add(header.get(0));
                newHeader.add(header.get(1) - 1);
                break;
            case DOWN:
                newHeader.add(header.get(0));
                newHeader.add(header.get(1) + 1);
                break;
            case LEFT:
                newHeader.add(header.get(0) - 1);
                newHeader.add(header.get(1));
                break;
            case RIGHT:
                newHeader.add(header.get(0) + 1);
                newHeader.add(header.get(1));
                break;
            default:
                break;
        }
        if (flag) {
            if (newHeader.get(0) < 0)
            {
                newHeader.set(0, newHeader.get(0) + size);
            }
            if (newHeader.get(1) < 0)
            {
                newHeader.set(1, newHeader.get(1) + size);
            }
            newHeader.set(0, newHeader.get(0) % size);
            newHeader.set(1, newHeader.get(1) % size);
        }
        commonUtils.addElement(newHeader);
    }

    public void deleteTail() {
        commonUtils.deleteFirstElement();
    }

    public boolean isWin() {
        if (commonUtils.getSnakeBody().size() == (size - 1) * (size - 1)) {
            return true;
        }
        return false;
    }

    public boolean isLose() {
        ArrayList<ArrayList<Integer>> snakeBody = commonUtils.getSnakeBody();
        ArrayList<Integer> header = getHeader();
        if (header.get(0) == 0 || header.get(0) == size - 1 || header.get(1) == 0 || header.get(1) == size - 1) {
            return true;
        }

        for (ArrayList<Integer> element : snakeBody) {
            if (element == header) {
                continue;
            }
            if (header.get(0).equals(element.get(0)) && header.get(1).equals(element.get(1))) {
                return true;
            }
        }

        return false;
    }
}
