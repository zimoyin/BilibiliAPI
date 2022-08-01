package github.zimoyin.core.application.download.ui;

import github.zimoyin.core.application.download.ui.node.SearchBox;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //设置窗体宽高
        stage.setWidth(1200);
        stage.setHeight(800);
        //禁用窗体大小调整
        stage.setResizable(false);
        //设置窗体标题
        stage.setTitle("Bilibili视频下载  作者：子墨");
        //设置ico
        stage.getIcons().add(new Image("http://q.qlogo.cn/headimg_dl?dst_uin=2556608754&spec=4&img_type=jpg"));
        //设置半透明
        stage.setOpacity(0.92);
        stage.show();

        //方位布局
        HBox hBox = new HBox();
        hBox.setPrefHeight(800);
        hBox.setPrefWidth(1200);
        SearchBox search = new SearchBox();
        hBox.getChildren().add(search);
        //居中对齐
        hBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene = new Scene(hBox);
        stage.setScene(scene);
    }
}
