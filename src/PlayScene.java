import Util.CommonUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;

public class PlayScene {

    JFrame frame;
    CommonUtils commonUtils;
    SquareGrid squareGrid;
    Snake snake;
    Food food;
    Timer timer;
    KeyBoardListener keyBoardListener;
    int delay = 200;
    boolean bugFlag = false;

    public PlayScene() {
        initFrame();
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private void initFrame() {
        frame = new JFrame();
        commonUtils = new CommonUtils();
        squareGrid = new SquareGrid(commonUtils);
        frame.setTitle("贪吃蛇");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        keyBoardListener = new KeyBoardListener();
        JButton startButton = new JButton("开始游戏");
        JButton endButton = new JButton("结束游戏");
        JButton saveButton = new JButton("保存游戏");
        JButton loadButton = new JButton("恢复游戏");
        JButton bugButton = new JButton("无敌模式");
        JButton upButton = new JButton("向上");
        JButton downButton = new JButton("向下");
        JButton leftButton = new JButton("向左");
        JButton rightButton = new JButton("向右");
        JButton pauseButton = new JButton("暂停");
        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(endButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(pauseButton);
        panel.add(bugButton);
        JPanel panel2 = new JPanel();
        panel2.add(upButton);
        panel2.add(downButton);
        panel2.add(leftButton);
        panel2.add(rightButton);

        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, squareGrid);
        frame.getContentPane().add(BorderLayout.SOUTH, panel2);

        startButton.addActionListener(new StartButtonListener());
        endButton.addActionListener(new EndButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        loadButton.addActionListener(new LoadButtonListener());
        pauseButton.addActionListener(new PauseButtonListener());
        bugButton.addActionListener(new BugButtonListener());

        upButton.addActionListener(new UpButtonListener());
        downButton.addActionListener(new DownButtonListener());
        leftButton.addActionListener(new LeftButtonListener());
        rightButton.addActionListener(new RightButtonListener());

        frame.addKeyListener(keyBoardListener);
        squareGrid.addKeyListener(keyBoardListener);
        startButton.addKeyListener(keyBoardListener);
        endButton.addKeyListener(keyBoardListener);
        saveButton.addKeyListener(keyBoardListener);
        loadButton.addKeyListener(keyBoardListener);
        upButton.addKeyListener(keyBoardListener);
        downButton.addKeyListener(keyBoardListener);
        leftButton.addKeyListener(keyBoardListener);
        rightButton.addKeyListener(keyBoardListener);
        pauseButton.addKeyListener(keyBoardListener);
        bugButton.addKeyListener(keyBoardListener);
    }

    private void snakeInit() {
        snake = new Snake(commonUtils);
    }

    private void foodInit() {
        food = new Food(commonUtils);
        food.creatFood();
    }

    private void play() {
        if (timer == null) {
            timer = new Timer(delay, new TimerListener());
        }
        timer.start();
    }

    private boolean isEat() {
        ArrayList<Integer> header = snake.getHeader();
        ArrayList<Integer> foodLocation = food.getFoodLocation();
        return header.get(0).equals(foodLocation.get(0)) && header.get(1).equals(foodLocation.get(1));
    }


    private void win() {
        snake = null;
        food = null;
        commonUtils.clearElement();
        JOptionPane.showMessageDialog(frame, "You win");
    }

    private void lose() {
        snake = null;
        food = null;
        commonUtils.clearElement();
        JOptionPane.showMessageDialog(frame, "You Lose");
    }

    class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            snake.changeDirection();
            snake.move(bugFlag);
            if (isEat()) {
                food.creatFood();
            } else {
                snake.deleteTail();
            }
            if (snake.isWin()) {
                win();
                timer.stop();
            }
            if (!bugFlag && snake.isLose()) {
                lose();
                timer.stop();
            }
            frame.repaint();
        }
    }

    class StartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (timer == null || !timer.isRunning()) {
                if (snake == null) {
                    snakeInit();
                }
                if (food == null) {
                    foodInit();
                }
                play();
            }
        }
    }

    class EndButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (timer == null) {
                return;
            }
            timer.stop();
            frame.repaint();
            commonUtils.clearElement();
            food = null;
            snake = null;
        }
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (timer == null) {
                return;
            }
            if (timer.isRunning()) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Please pause first");
                return;
            }
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream os = null;
            try {
                fileOutputStream = new FileOutputStream("gamedata.ser");
                os = new ObjectOutputStream(fileOutputStream);
                os.writeObject(commonUtils);
                os.writeObject(snake);
                os.writeObject(food);
                os.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Save failed");
            }

        }
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Please pause first");
                return;
            }
            FileInputStream fileInputStream = null;
            ObjectInputStream is = null;
            try {
                fileInputStream = new FileInputStream("gamedata.ser");
                is = new ObjectInputStream(fileInputStream);
                commonUtils = (CommonUtils) is.readObject();
                squareGrid.setCommonUtils(commonUtils);
                snake = (Snake) is.readObject();
                food = (Food) is.readObject();
                is.close();
                frame.repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Load failed");
            }
        }
    }

    class PauseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            timer.stop();
        }
    }

    class BugButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            bugFlag = !bugFlag;
        }
    }

    class UpButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (!timer.isRunning()) {
                return;
            }
            snake.writeDirection('w');
        }
    }

    class DownButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (!timer.isRunning()) {
                return;
            }
            snake.writeDirection('s');
        }
    }


    class LeftButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (!timer.isRunning()) {
                return;
            }
            snake.writeDirection('a');
        }
    }

    class RightButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (!timer.isRunning()) {
                return;
            }
            snake.writeDirection('d');
        }
    }

    class KeyBoardListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //System.out.println(e.getKeyChar());
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println(e.getKeyChar());
            if (timer == null) {
                return;
            }
            if (!timer.isRunning()) {
                return;
            }
            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                timer.stop();
                return;
            } else {
                if (e.getKeyChar() == 'q') {
                    delay = (delay - 100 > 0) ? delay - 100 : 100;
                    timer.setDelay(delay);
                }
                if (e.getKeyChar() == 'e') {
                    delay += 100;
                    timer.setDelay(delay);
                }
            }
            snake.writeDirection(e.getKeyChar());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println(e.getKeyChar());
        }
    }
}
