package github.zimoyin.core.application.download.ui.node;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class SearchBox extends FlowPane {
    public SearchBox() {
        TextField search = new TextField();
        search.setPromptText("请输入bv、ep、ssid");
        this.getChildren().add(search);


        Button start = new Button("搜索");
        start.setFocusTraversable(true);
        this.getChildren().add(start);
    }
}
