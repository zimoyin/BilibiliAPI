package github.zimoyin.core.collection;

import github.zimoyin.core.collection.pojo.collection.CollectionJsonRoot;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long id = new VideoCollection("BV19T411A7oX").getSeasonID();
        CollectionJsonRoot pojo = new CollectionInfo().getJsonPojo(22223196, id);
        System.out.println(pojo.getData().getPage().getPage_num());
    }
}
