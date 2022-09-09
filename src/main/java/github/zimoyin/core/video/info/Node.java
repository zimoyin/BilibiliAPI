package github.zimoyin.core.video.info;


import github.zimoyin.core.video.info.pojo.interact.InteractVideoRootBean;

@lombok.Data
public class Node {
    /**
     * 节点信息
     */
    private InteractVideoRootBean.Data nodeInfo;

    private boolean isLeft;

    private long cid;
    private long edge_id;
    private String title;

    /**
     * 当前节点所在的第几层
     */
    private int level;
}
