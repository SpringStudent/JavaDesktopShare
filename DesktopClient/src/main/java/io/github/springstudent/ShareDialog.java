package io.github.springstudent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.regex.Pattern;

/**
 * @author zhouning
 * @date 2022/03/31 16:36
 */
public class ShareDialog extends JDialog implements ActionListener, WindowListener, CommandTask.CmdListener {

    private JTextField shareTextField;
    private JButton connectButton;
    private CommandTask commandTask;
    private boolean connected;
    public static String ffmpeg;

    @Override
    public void actionPerformed(ActionEvent e) {
        connected = false;
        if (shareTextField.getText() != null && shareTextField.getText().length() > 0) {
            String desktopserver = shareTextField.getText();
            if (Pattern.matches(CommandTask.TEXTFIELD_REGEX, desktopserver)) {
                connectButton.setText("正在连接");
                try {
                    commandTask = new CommandTask(String.format(CommandTask.FFMPEG_DESKTOP_CMD, ffmpeg, shareTextField.getText())).start();
                    commandTask.setCmdListener(this);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "输入参数有误。分享地址格式为http://ip:port/receive?id=xxx");
            }

        } else {
            JOptionPane.showMessageDialog(this, "请输入分享地址");
        }
    }

    public ShareDialog(DesktopClient desktopShare) {
        super(desktopShare);
        shareTextField = new JTextField();
        shareTextField.setPreferredSize(new Dimension(150, 30));
        shareTextField.setForeground(Color.BLUE);
        connectButton = new JButton("分享到");
        connectButton.addActionListener(this);
        add(shareTextField);
        add(connectButton);
        addWindowListener(this);
        setLayout(new FlowLayout());
        setTitle("分享设置");
        setModal(true);
        setSize(300, 190);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (commandTask != null) {
            commandTask.stop();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("windowClosed");
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void notify(CommandTask commandTask) {
        if (commandTask.isRunning()) {
            if (connectButton.isEnabled()) {
                connectButton.setText("连接成功");
                connectButton.setEnabled(false);
            }
        } else {
            if (!connected) {
                JOptionPane.showMessageDialog(this, "桌面分享失败");
                connected = true;
                if (!connectButton.isEnabled()) {
                    connectButton.setEnabled(true);
                    connectButton.setText("连接到");
                }
            }
        }
    }
}
