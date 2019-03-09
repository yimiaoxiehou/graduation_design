/**
 * @Package tk.yimiao.yimiaocloud.common.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-08 16:07
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.constant;

public enum ConstField {
    SYSTEM_NAME("YimiaoMall");

    private String field;

    ConstField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
