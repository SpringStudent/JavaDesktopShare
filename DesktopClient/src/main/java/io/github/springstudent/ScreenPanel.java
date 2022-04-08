package io.github.springstudent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author zhouning
 * @date 2022/03/31 16:34
 */
public class ScreenPanel extends JPanel implements Runnable {
    private BufferedImage cimage;
    private Dimension screenSize;
    private Rectangle rectangle;
    private Robot robot;


    public void run() {
        while (true) {
            try {
                cimage = robot.createScreenCapture(rectangle);
                repaint();
                Thread.sleep(40);
            } catch (InterruptedException e) {
                //收到中断，关闭线程
                return;
            }
        }
    }

    public ScreenPanel() {
        super(true);
        this.setLayout(null);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        rectangle = new Rectangle(screenSize);
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cimage == null) {
            return;
        }

        double scale = getScale();
        double scaledWidth = cimage.getWidth() * scale;
        double scaledHeight = cimage.getHeight() * scale;

        g.drawImage(cimage, 0, 0, (int) scaledWidth, (int) scaledHeight, null);
    }

    private double getScale() {
        double scaleX = (double) getWidth() / cimage.getWidth();
        double scaleY = (double) getHeight() / cimage.getHeight();
        return Math.min(scaleX, scaleY);
    }
}
