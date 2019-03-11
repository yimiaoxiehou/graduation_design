/**
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-10 15:44
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.util.Hexadecimals.byteToHexString;

public class MD5Util {

    public static final String ENCODING = "utf-8";
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String MD5Encode(String source) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(source.getBytes(ENCODING));
            return byteArrayToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(MD5Encode("111"));
    }
}
