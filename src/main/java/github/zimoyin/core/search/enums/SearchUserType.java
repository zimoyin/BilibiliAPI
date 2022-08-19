package github.zimoyin.core.search.enums;

/**
 * (非必要) 用户分类筛选:仅用于搜索用户
 */
public enum SearchUserType {
    /**
     * (默认)所有用户
     */
    All("0"),
    /**
     * up主
     */
    Up("1"),
    /**
     * 普通用户
     */
    Ordinary("2"),
    /**
     * 认证用户
     */
    Authentication("3")
    ;
    private String type;


    SearchUserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
