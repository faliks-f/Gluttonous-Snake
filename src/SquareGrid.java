import Util.CommonUtils;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class SquareGrid extends JPanel implements Serializable {

    CommonUtils commonUtils;

    int width = this.getWidth();
    int height = this.getHeight();
    int size;
    int xStart = 0, xEnd, yStart = 0, yEnd;
    int step;

    public SquareGrid(CommonUtils _Common_utils) {
        commonUtils = _Common_utils;
    }

    public void setCommonUtils(CommonUtils _commonUtils) {
        commonUtils = _commonUtils;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.black);

        width = this.getWidth();
        height = this.getHeight();
        size = commonUtils.getSize();
        width = this.getWidth();
        height = this.getHeight();

        setStep();
        drawGrid(g2D);
        drawEdge(g2D);
        drawSnake(g2D);
        drawFood(g2D);
    }

    private void setStep() {
        if (width > height) {
            int correction = (width - height) / 2;
            xStart = correction;
            step = (width - 2 * correction) / size;
        } else {
            int correction = (height - width) / 2;
            yStart = correction;
            step = (height - 2 * correction) / size;
        }
        xEnd = xStart + size * step;
        yEnd = yStart + size * step;
    }

    private void drawGrid(Graphics2D g) {
        for (int i = 0; i <= size; ++i) {
            g.drawLine(xStart, yStart + i * step, xEnd, yStart + i * step);
            g.drawLine(xStart + i * step, yStart, xStart + i * step, yEnd);
        }
    }

    private void drawEdge(Graphics2D g) {
        for (int i = 0; i < size; ++i) {
            g.fillRect(xStart, yStart + i * step, step, step);
            g.fillRect(xEnd - step, yStart + i * step, step, step);
            g.fillRect(xStart + i * step, yStart, step, step);
            g.fillRect(xStart + i * step, yEnd - step, step, step);
        }
    }

    private void drawSnake(Graphics2D g) {
        ArrayList<ArrayList<Integer>> arrayLists = commonUtils.getSnakeBody();
        if (arrayLists == null) {
            return;
        }
        for (ArrayList<Integer> element : arrayLists) {
            if (element == arrayLists.get(arrayLists.size() - 1)) {
                g.setColor(Color.red);
                g.fillRect(xStart + element.get(0) * step, yStart + element.get(1) * step, step, step);
                g.setColor(Color.black);
                continue;
            }
            g.fillRect(xStart + element.get(0) * step, yStart + element.get(1) * step, step, step);
        }
    }

    private void drawFood(Graphics2D g) {
        ArrayList<Integer> foodLocation = commonUtils.getFoodLocation();
        g.setColor(Color.blue);
        if (foodLocation != null) {
            g.fillRect(xStart + foodLocation.get(0) * step, yStart + foodLocation.get(1) * step, step, step);
        }
        g.setColor(Color.black);
    }
}
