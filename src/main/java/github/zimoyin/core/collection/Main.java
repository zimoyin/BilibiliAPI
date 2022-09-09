package github.zimoyin.core.collection;

import github.zimoyin.core.collection.pojo.collection.Archives;
import github.zimoyin.core.collection.pojo.collection.CollectionJsonRoot;

import java.io.IOException;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws IOException {
        long id = new VideoCollection("BV19T411A7oX").getSeasonID();
        CollectionJsonRoot pojo = new CollectionInfo().getJsonPojo(22223196, id);
        pojo.getData().getArchives().forEach(archives -> System.out.println(archives.getTitle()));
    }
}
