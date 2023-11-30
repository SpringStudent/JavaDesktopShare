package io.github.springstudent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author zhouning
 * @date 2022/03/31 15:31
 */
public class DesktopClient extends JFrame {

    private ScreenPanel screen;

    public DesktopClient(String ffmpeg) {
        screen = new ScreenPanel();
        ShareDialog.ffmpeg = ffmpeg;
        this.setJMenuBar(createJMenuBar());
        this.add(screen);
        this.setTitle("桌面分享@SpringStudent");
        this.setSize(960, 650);
        this.setLocation(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        new Thread(screen).start();
    }


    private JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("操作");
        JMenuItem menuItem = new JMenuItem("分享屏幕");
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new ShareDialog(DesktopClient.this).setVisible(true);
            }
        });
        menu.add(menuItem);
        //把菜单添加到菜单栏
        menuBar.add(menu);
        return menuBar;
    }

    public static void main(String[] args) {
        //TODO 修改为自己的ffmpeg路径
        String ffmpeg = "D:\\intellij\\workspace-opengauss\\JavaDesktopShare\\ffmpeg.exe";
        if (args != null && args.length > 0) {
            ffmpeg = args[0];
        }
        new DesktopClient(ffmpeg);
    }

}
