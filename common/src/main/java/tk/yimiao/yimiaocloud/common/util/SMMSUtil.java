/**
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-11 17:47
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import tk.yimiao.yimiaocloud.common.model.SMMSResponse;

import java.io.*;
import java.util.Base64;

public class SMMSUtil {

    static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";

    public static SMMSResponse imageUpload(Long userId, String token, File f) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            FileBody bin = new FileBody(f);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("smfile", bin)
                    .build();
            HttpPost request = new HttpPost("https://sm.ms/api/upload");
            request.addHeader(new BasicHeader("user-agent", USER_AGENT));
            request.setEntity(reqEntity);
            HttpResponse result = httpClient.execute(request);
            if (result.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            System.out.println(json);
            return JSON.parseObject(json, SMMSResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static SMMSResponse imageUpload(Long userId, String token, String imgStr) throws IOException {

        Base64.Decoder decoder = Base64.getMimeDecoder();

        byte[] bytes = decoder.decode(imgStr);

        File imageFile = new File("temp.jpg");
        if (!imageFile.exists()) {
            imageFile.createNewFile();
        }
        try (OutputStream imageStream = new FileOutputStream(imageFile)) {
            imageStream.write(bytes);
            imageStream.flush();
        }
        System.out.println(imageFile.getName());
        ;

        return imageUpload(userId, token, imageFile);
    }


    public static void main(String[] args) throws IOException {
        //待处理的图片
        String imgFile = "/Volumes/Data/[森萝财团]SSR-009[83P]/001.jpg";
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Base64.Encoder encoder = Base64.getMimeEncoder();

        String s = encoder.encodeToString(data);
        System.out.println(s);

        System.out.println(JSON.toJSONString(imageUpload(12L, "", s)));
    }
}
