package github.zimoyin.bili.video.info;

import com.alibaba.fastjson.JSONObject;
import github.zimoyin.bili.exception.CodeException;
import github.zimoyin.bili.utils.IDConvert;
import github.zimoyin.bili.utils.net.httpclient.HttpClientResult;
import github.zimoyin.bili.utils.net.httpclient.HttpClientUtils;
import github.zimoyin.bili.video.info.pojo.interact.Choices;
import github.zimoyin.bili.video.info.pojo.interact.InteractVideoRootBean;
import github.zimoyin.bili.video.info.pojo.interact.Questions;
import lombok.Data;


import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 互动视频
 */
@Data
public class InteractVideoInfo {
    /**
     * bvid		稿件bvid	必要
     * graph_version		剧情图id	必要	位于player.so中
     * edge_id		模块编号	非必要	0或留空为起始模块
     */
    private final String URL = "http://api.bilibili.com/x/stein/edgeinfo_v2?bvid=%s&graph_version=%s&edge_id=%s";
    private String graph_version;
    private String bv;
    private long rootCid;

    /**
     * 模块id映射到node，模块id是不会重复的，但是模块内的cid是会与其他模块内的cid重复的，为了防止重复下载
     */
    HashMap<Long, Node> eidMaps = new HashMap<Long, Node>();
    /**
     * cid映射到node
     */
    HashMap<Long, Node> cidMaps = new HashMap<Long, Node>();

    public InteractVideoInfo(String bv) {
        try {
            this.bv = bv;
            this.graph_version = InteractVideoUtil.getGraphVersion(bv);
            this.rootCid = Long.parseLong(IDConvert.BvToCID(bv));
        } catch (Exception e) {
            throw new RuntimeException("获取剧情图ID失败", e);
        }
    }

    /**
     * 获取该模块的信息
     *
     * @param edge_id
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public InteractVideoRootBean getPojo(long edge_id) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String page = getPage(edge_id);
        InteractVideoRootBean bean = JSONObject.parseObject(page, InteractVideoRootBean.class);
        return bean;
    }

    /**
     * 获取该模块的信息
     *
     * @param edge_id
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public String getPage(long edge_id) throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException {
        String url = String.format(URL, bv, graph_version, edge_id);
//        System.out.println(url);
        HttpClientResult result = HttpClientUtils.doGet(url);
        return result.getContent();
    }


    /**
     * 获取所有视频的cid
     *
     * @return
     */
    public HashMap<Long, Node> getCidMaps() {
        if (cidMaps.isEmpty()) {
            try {
                init();
            } catch (Exception e) {
                throw new RuntimeException("遍历互动视频节点失败",e);
            }
        }
        return cidMaps;
    }


    /**
     * 获取所有模块
     *
     * @return
     */
    public HashMap<Long, Node> getEidMaps() {
        if (eidMaps.isEmpty()) {
            try {
                init();
            } catch (Exception e) {
                throw new RuntimeException("遍历互动视频节点失败",e);
            }
        }
        return eidMaps;
    }

    /**
     * 获取所有的模块（节点）
     * 使用广度优先，遍历所有的剧情图，并建立索引
     *
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws URISyntaxException
     * @throws KeyManagementException
     */
    public void init() throws IOException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, KeyManagementException, CodeException {
//        InteractVideoInfo info = new InteractVideoInfo(this.getBv());

        //队列
        Queue<Long> queue = new LinkedList<>();//模块id构成的队列
        Queue<Long> cids = new LinkedList<>();//视频cid构成的队列
        HashMap<Long, Integer> levelMap = new HashMap<>();//视频层级映射，默认根节点和一级节点为0

        queue.offer(0L);//将根输入到队列中
        cids.offer(this.rootCid);//将根输入到队列中
        levelMap.put(0L, 0);
        int level = 0;
        while (!queue.isEmpty()) {//循环到队列空
            //将这个节点弹出队列
            long edgeId = queue.poll();
            Long cid = cids.poll();
            //如果这个key存在就说明这个节点是循环节点,就禁止再次访问该id
            if (this.eidMaps.containsKey(edgeId)) {
                continue;
            }
            //解析节点,并将模块信息添加为节点并映射到map中
            InteractVideoRootBean pojo = this.getPojo(edgeId);
            if (pojo.getCode() != 0) {
                throw new CodeException("服务器响应码不符合程序预期，疑似服务器返回了预期之外的信息,响应信息:"+pojo.getMessage());
            }
            String title = pojo.getData().getTitle();
            Node node = new Node();
            node.setEdge_id(edgeId);
            node.setLevel(levelMap.get(edgeId));
            node.setTitle(title);
            node.setCid(cid);
            List<Questions> questions = pojo.getData().getEdges().getQuestions();
            this.eidMaps.put(edgeId, node);
            this.cidMaps.put(cid, node);
//            System.out.println(edgeId + " : " + node);
            //抵达了根叶子，就进入下一个循环，直到队列为空
            if (questions == null) {
//                System.out.println(queue);
                node.setLeft(true);
                continue;
            }
            //将这个节点的下级节点id压入队列
            for (Questions question : questions) {
                for (Choices choice : question.getChoices()) {
                    long nextCid = choice.getCid();
                    long nextId = choice.getId();
                    //如果这个key存在就说明这个节点是循环节点,就禁止再次访问该id
                    if (this.eidMaps.containsKey(nextId)) {
                        continue;
                    }
                    queue.offer(nextId);
                    cids.offer(nextCid);
                    levelMap.put(nextId, level);
                }
            }
            level++;
        }
    }
}
