package github.zimoyin.core.exception;



import github.zimoyin.core.utils.JsonSerializeUtil;

import java.util.HashMap;


/**
 * 判断接口是否请求成功，如果没有就抛出异常
 */
public class Code extends HashMap<String,String> {

    public Code() {
    }

    /**
     * 判断code是否为0，如果不是就抛出异常
     * @param content 响应原文
     * @throws CodeException
     */
    public static void throwCodeException(String content) throws CodeException {
        if (!"0".equalsIgnoreCase(JsonSerializeUtil.getJsonPath().read(content, "/code"))) {
            throw new CodeException("服务器接口异常 code 不为 0 (成功)，响应原文 ："+content);
        }
    }

    /**
     * 判断code是否为0，如果不是就抛出异常
     * @param content 响应原文
     * @throws CodeException
     */
    public static void throwCodeException(String content,String message) throws CodeException {
        if (!"0".equalsIgnoreCase(JsonSerializeUtil.getJsonPath().read(content, "/code"))) {
            throw new CodeException(message+" 响应原文 ："+content);
        }
    }

    /**
     * 判断code是否为0，如果不是就抛出异常
     * @param content 响应原文
     * @throws CodeException
     */
    public static void throwCodeException(String content,String message,Exception e) throws CodeException {
        if (!"0".equalsIgnoreCase(JsonSerializeUtil.getJsonPath().read(content, "/code"))) {
            throw new CodeException(message+" 响应原文 ："+content,e);
        }
    }

    /**
     * 判断code是否为200，如果不是就抛出异常
     * @param code http响应码
     * @throws CodeException
     */
    public static void throwCodeException(int code) throws CodeException {
        if (code != 200) {
            throw new CodeException("Http Client 访问接口响应码为："+code);
        }
    }


    /**
     * 判断code是否为200，如果不是就抛出异常
     * @param code http响应码
     * @throws CodeException
     */
    public static void throwCodeException(int code,String message) throws CodeException {
        if (code != 200) {
            throw new CodeException("Http Client 访问接口响应码为："+code+"; "+message);
        }
    }

    /**
     * 根据设置的code与code信息对照表来抛出异常
     * @param content 响应原文
     * @throws CodeException
     */
    public void throwCodeExceptions(String content) throws CodeException {
        String code = JsonSerializeUtil.getJsonPath().read(content, "/code");
        for (String key : keySet()) {
            if (code.trim().equalsIgnoreCase(key)) {
                throw new CodeException("服务器接口异常 code 为"+code+" : "+get(key));
            }
        }
        //如果code为0，并且code的message没在map；里时抛出
        if (!"0".equalsIgnoreCase(code)) {
            throw new CodeException("服务器接口异常 code 不为 0 (成功)，响应原文 ："+content);
        }
    }
}
