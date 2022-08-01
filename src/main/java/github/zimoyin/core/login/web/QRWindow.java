package github.zimoyin.core.login.web;

import com.google.zxing.WriterException;
import github.zimoyin.core.utils.qrcode.QRCodeUtils;

import javax.swing.*;
import java.io.IOException;

public class QRWindow {

    /**
     * 二维码窗口
     */
    private volatile JFrame frame;

    /**
     * 创建并显示GUI。
     */
    private void createAndShowGUI(String url) throws IOException, WriterException {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);


        // 创建及设置窗口
        frame = new JFrame("Login");
        ImageIcon icon = new ImageIcon(QRCodeUtils.create(url).toByteArray());
        // 添加 "Hello World" 标签
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.getContentPane().add(label);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * 用一个事件线程加载窗体来显示二维码
     */
    public void showQRWindownThread(String url) {
        // 显示应用 GUI
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.currentThread().setName("login-windown");
                createAndShowGUI(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public JFrame getFrame(){
        return frame;
    }

    /**
     * 用一个窗体来显示二维码
     */
    @Deprecated
    private void showQRWindown(String url) throws Exception {
        // 显示应用 GUI
        createAndShowGUI(url);
    }
}
