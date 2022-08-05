package login;

import com.google.zxing.WriterException;
import github.zimoyin.core.cookie.Cookie;
import github.zimoyin.core.cookie.GlobalCookie;
import github.zimoyin.core.utils.qrcode.QRCodeUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoginWindowsQR extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        //获取URL
        LoginURL loginURL = new LoginURL();
        String url = loginURL.getLoginUrl();

        //图像控件
        InputStream inputStream = new ByteArrayInputStream(getQRImage(url));
        ImageView imageView = new ImageView(new Image(inputStream));
        //布局
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(imageView);
        stage.setScene(new Scene(flowPane));
        //显示
        stage.setWidth(imageView.getImage().getWidth()+25);
        stage.setHeight(imageView.getImage().getHeight()+50);
        stage.show();
        //监听是否登录
        new Thread(() -> {
            try {
                Thread.currentThread().setName("LoginThread");
                long ts = loginURL.getTs();
                String key = loginURL.getKEY();
                LoginListen loginListen = new LoginListen(stage, key, ts);
                Cookie cookie = loginListen.listen();
                //设置全局第二Cookie
                GlobalCookie.getInstance().setCookie(cookie);
                //卸载窗体
                Platform.runLater(() -> stage.close());
            }catch (Exception e){
                throw new RuntimeException("无法登录",e);
            }
        }).start();

    }


    /**
     * 获取这个URL的图片，通常用来生成一个二维码图像
     * @param url
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public byte[] getQRImage(String url) throws WriterException, IOException {
        return  QRCodeUtils.create(url).toByteArray();
    }
}
