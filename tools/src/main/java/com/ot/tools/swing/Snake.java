//package com.ot.tools.swing;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//
//public class Snake {
//    private int x, y; // 蛇头坐标
//    private int direction; // 蛇移动方向
//
//    public Snake() {
//        x = 0;
//        y = 0;
//        direction = 0;
//    }
//
//    public void update() {
//        // 根据方向更新蛇头位置
//        switch (direction) {
//            case 0: // 上
//                y--;
//                break;
//            case 1: // 右
//                x++;
//                break;
//            case 2: // 下
//                y++;
//                break;
//            case 3: // 左
//                x--;
//                break;
//        }
//    }
//
//    public void draw(Graphics2D g2d) {
//        g2d.setColor(Color.GREEN);
//        g2d.fillRect(x, y, 10, 10); // 绘制蛇头
//    }
//}
