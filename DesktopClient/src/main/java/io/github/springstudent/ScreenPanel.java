package io.github.springstudent;

import javax.swing.*;
import java.awt.*;

/**
 * @author zhouning
 * @date 2022/03/31 16:34
 */
public class ScreenPanel extends JPanel implements Runnable {
    private Image cimage;
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
        super();
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

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(cimage, 0, 0, null);
    }
}
