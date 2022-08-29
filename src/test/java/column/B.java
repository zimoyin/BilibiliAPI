package column;

import github.zimoyin.core.column.info.Column;

public class B {
    public static void main(String[] args) {
        Column column = new Column("cv18084058");
        System.out.println(column.getTitle());
        System.out.println(column.getText());
//        for (Paragraph paragraph : column.getParagraphs()) {
//            System.out.println(paragraph.getText());
//        }
    }
}
