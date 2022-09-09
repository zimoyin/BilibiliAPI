package github.zimoyin.core.test.pojo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        List<String> name = FindClass.getClazzName("github.zimoyin.core.test.pojo.res", true);
        for (String str : name) {
            Class<?> cls = Class.forName(str);
            System.out.println(cls);
            for (Field declaredField : cls.getDeclaredFields()) {
                System.out.println("val "+declaredField.getName()+":"+ FieldToType(declaredField.getType().getName())+"?,");
            }
            System.out.println();
            System.out.println("--------------------------------");
        }
    }


    private static String FieldToType(String type){
        if (type.contains(".")) {
            return type.substring(type.lastIndexOf(".")+1);
        }

        // get First letter of the string
        String firstLetStr = type.substring(0, 1);
        // Get remaining letter using substring
        String remLetStr = type.substring(1);

        // convert the first letter of String to uppercase
        firstLetStr = firstLetStr.toUpperCase();

        // concantenate the first letter and remaining string
        String firstLetterCapitalizedName = firstLetStr + remLetStr;
        return firstLetterCapitalizedName;
    }
}
