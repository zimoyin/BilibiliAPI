package github.zimoyin.bili.collection;

import github.zimoyin.bili.collection.pojo.collection.CollectionJsonRoot;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long id = new VideoCollection("BV19T411A7oX").getSeasonID();
        CollectionJsonRoot pojo = new CollectionInfo().getJsonPojo(22223196, id);
        pojo.getData().getArchives().forEach(archives -> System.out.println(archives.getTitle()));
    }
}
