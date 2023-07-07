package com.ot.tools.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class SnakeGame extends JFrame {
    private JPanel gamePanel;

    public SnakeGame() {
        setTitle("贪吃蛇游戏");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // 初始化游戏面板
        gamePanel = new JPanel();
        getContentPane().add(gamePanel);

        setVisible(true);
    }


    public static void main(String[] args) {
        new SnakeGame();
    }
}
