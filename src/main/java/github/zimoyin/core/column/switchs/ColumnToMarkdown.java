package github.zimoyin.core.column.switchs;

import github.zimoyin.core.column.info.Column;
import github.zimoyin.core.column.info.Paragraph;
import org.jsoup.nodes.Attributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 专栏转md
 */
public class ColumnToMarkdown {
    //转标题
    //转段落
    //排序
    //样式生成
    private String cvid;
    private Column column;

    public ColumnToMarkdown(String cvid) {
        this.cvid = cvid;
        init(cvid);
    }

    public ColumnToMarkdown() {
    }

    private Column init(String cv) {
        try {
            this.column = new Column(cvid);
        } catch (Exception e) {
            throw new RuntimeException("无法获取到专栏内容", e);
        }
        return column;
    }

    /**
     * 解析全文
     * @param column
     */
    private String par(Column column){
        StringBuffer par = new StringBuffer();
        par.append("#").append(" ").append(column.getTitle()).append("  ").append("\r\n");
        par.append(">").append(" 作者：").append(column.getAuthorName()).append("  ").append("\r\n");
        par.append(">").append(" 文章ID：").append(column.getCv()).append("  ").append("\r\n");
        par.append("  ").append("\r\n");
        ArrayList<Paragraph> paragraphs = column.getParagraphs();
        for (Paragraph paragraph : paragraphs) {
            String text = paragraph.getText();
            if (text == null || text.isEmpty()) {
                par.append("  ").append("\r\n");
                continue;
            }
            //样式与文字处理
            ArrayList<String> boldText = paragraph.getBoldText();
            if (boldText != null) for (String s : boldText) text = text.replaceAll(s, "**" + s + "**");
            ArrayList<String> colourText = paragraph.getColourText();
            if (colourText != null) for (String s : colourText) text = text.replaceAll(s, "==" + s + "==");
            if (paragraph.isTitle()) par.append("### ").append(" ").append(text).append("  ").append("\r\n");
            else par.append(text).append("  ").append("\r\n");
            //图片处理
            ArrayList<String> imageSrc = paragraph.getImageSrc();
            ArrayList<String> imageFerSrc = paragraph.getImageFerSrc();
            if (imageSrc!=null)for (String s : imageSrc) par.append("![图片](").append(s).append(")").append("  ").append("\r\n");
            if (imageFerSrc!=null)for (String s : imageFerSrc) par.append("![图片](").append(s).append(")").append("  ").append("\r\n");
        }
        par.append("----").append("  ").append("\r\n");
        par.append("文章地址：").append(column.getUrl()).append("  ").append("\r\n");
        System.out.println(par.toString());
        return par.toString();
    }


    public void save(String cv,String path) throws RuntimeException {
        File file = new File(path + column.getTitle().replaceAll("[~.?/:<>|*\"\\\\]","")+".md");
        file.getParentFile().mkdirs();
        Column init = init(cv);
        String par = par(init);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(par.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void save(String path) throws RuntimeException {
        FileOutputStream fileOutputStream = null;
        File file = new File(path + column.getTitle().replaceAll("[~.?/:<>|*\"\\\\]","")+".md");
        file.getParentFile().mkdirs();
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(par(column).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sort(List<String> par) {
        //同理升序的话应该为a.length() - b.length()，因为这里要求倒叙，所以前面加上-号
        Comparator<String> compByLength = (a, b) -> -(a.length() - b.length());
        par = par.stream().sorted(compByLength).collect(Collectors.toList());
        System.out.println(par);
    }

    public static void main(String[] args) {
        ColumnToMarkdown markdown = new ColumnToMarkdown("cv269177");
        markdown.save("./");
    }
}
