package github.zimoyin.bili.favorites.pojo.id;

import github.zimoyin.bili.favorites.pojo.conter.Medias;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FavoriteIDList {
    private int code;
    private String message;
    private int ttl;
    private ArrayList<Medias> data;


    /**
     * 获取所有的bv id
     * @return
     */
    public ArrayList<String> getBvIDs(){
        ArrayList<String> bvids = new ArrayList<String>();
        for (Medias datum : data) {
            String bvid = datum.getBvid();
            bvids.add(bvid);
        }
        return bvids;
    }


    /**
     * 获取所有的 id
     * @return
     */
    public ArrayList<String> getID(){
        ArrayList<String> ids = new ArrayList<String>();
        for (Medias datum : data) {
            String id = String.valueOf(datum.getId());
            ids.add(id);
        }
        return ids;
    }
}
