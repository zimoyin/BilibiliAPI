/**
  * Copyright 2022 json.cn 
  */
package github.zimoyin.core.collection.pojo.userlist;

/**
 * Auto-generated: 2022-08-29 22:45:32
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@lombok.Data
public class UserCollectionListJsonRoot {

    private int code;
    private String message;
    private int ttl;
    private Data data;

    public Items_lists getData() {
        return data.getItems_lists();
    }
    @lombok.Data
    public class Data {
        private Items_lists items_lists;
    }
}