/**
 * @Package tk.yimiao.yimiaocloud.common.model
 * @Description: SM.MS 图床的返回数据模式
 * @author yimiao
 * @date 2019-03-11 17:47
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.model;

import lombok.Data;

@Data
public class SMMSResponse {

    /**
     * 上传文件状态。正常情况为 success。出现错误时为 error
     */
    String code;
    Data data;

    @lombok.Data
    public class Data {
        /**
         * 上传文件时所用的文件名
         */
        String filename;
        /**
         * 上传后的文件名
         */
        String storename;
        /**
         * 文件大小
         */
        int size;
        /**
         * 图片的宽度
         */
        int width;
        /**
         * 图片的高度
         */
        int height;
        /**
         * 随机字符串，用于删除文件
         */
        String hash;
        /**
         * 删除上传的图片文件专有链接
         */
        String delete;
        /**
         * 图片服务器地址
         */
        String url;
        /**
         * 图片的相对地址
         */
        String path;
        /**
         * 上传图片出错时将会出现
         */
        String msg;
    }
}
