/**
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 02:28
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HmacSha1Util {

    public static final String MAC_NAME = "HmacSHA1";
    public static final String ENCODING = "UTF-8";
    public static final String key = "yimiaoxiehou";

    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        // 转码，转为二进制
        byte[] key = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组和秘钥生成算法生成秘钥
        SecretKey secretKey = new SecretKeySpec(key, MAC_NAME);
        // 生成一个指定 mac 算法的 mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定的秘钥初始化 Mac 对象
        mac.init(secretKey);
        // 转码，转为二进制
        byte[] text = encryptText.getBytes(ENCODING);
        return mac.doFinal(text);
    }

    public static String getSignature(String text) {
        try {
            return byte2hex(HmacSHA1Encrypt(text, key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        String tmp;
        for (byte b : bytes) {
            // 字节转为16进制，并与 0xFF 进行与运算
            tmp = Integer.toHexString(b & 0xFF);
            // 不足 2 位进行补位
            // 一个字节码范围为 2^8, 一位 16 进制数为 2^4
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(getSignature("瓦尔环球网卡"));
    }
}
