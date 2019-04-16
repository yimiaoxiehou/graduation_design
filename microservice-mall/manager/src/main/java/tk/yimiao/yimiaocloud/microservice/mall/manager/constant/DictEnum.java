/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-11 23:37
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.constant;

public enum DictEnum {

    /**
     * 词典库修改标识
     */
    EXT_KEY("DICT_EXT_KEY"),

    /**
     * 词典库修改标识
     */
    STOP_KEY("DICT_STOP_KEY"),

    /**
     * 扩展词库缓存key
     */
    LAST_MODIFIED("Last-Modified"),

    /**
     * 停用词库缓存key
     */
    ETAG("ETAG");

    /**
     * 扩展词库
     */
    public static final int DICT_EXT = 1;

    /**
     * 停用词库
     */
    public static final int DICT_STOP = 0;

    private String key;

    DictEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
