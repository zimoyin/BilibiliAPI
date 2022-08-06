package github.zimoyin.core.comment.info;

import github.zimoyin.core.Incompleteness;
import github.zimoyin.core.comment.enums.CommentType;
import github.zimoyin.core.utils.IDConvert;
import github.zimoyin.core.utils.JsonSerializeUtil;
import github.zimoyin.core.utils.net.httpclient.HttpClientResult;
import github.zimoyin.core.utils.net.httpclient.HttpClientUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * 评论区的评论总数
 */
@Incompleteness
public class CommentCount {
    private static final String URL = "http://api.bilibili.com/x/v2/reply/count";
    /**
     * 获取评论区的评论总数
     * @param bv bv号
     * @return
     * @throws IOException
     */
    public int getVideoCount(String bv) throws IOException {
        return getCount(CommentType.AV_ID, String.valueOf(IDConvert.BvToAvNumber(bv)));
    }

    /**
     * 获取评论区的评论总数
     * @param type 评论区类型
     * @param oid 评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @return
     * @throws IOException
     */
    public int getCount(CommentType type, String oid) throws IOException {
        String page = getPage(type, oid);
        int count = -1;
        try {
            String read = JsonSerializeUtil.getJsonPath().read(page,"/data/count");
            count = Integer.parseInt(read);
        }catch (Exception e){
            return count;
        }
        return count;
    }

    /**
     * 获取评论区的评论总数（源json）
     * @param type 评论区类型
     * @param oid 评论区的oid，每个type对影一个oid，oid具体是什么见该type的注释中“：”后面的内容
     * @return
     * @throws IOException
     */
    public String getPage(CommentType type, String oid) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", type.toString());
        params.put("oid", oid);

        HttpClientResult result = HttpClientUtils.doGet(URL, params);
        return result.getContent();
    }
}
