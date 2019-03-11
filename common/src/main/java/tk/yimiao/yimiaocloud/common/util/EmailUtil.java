/**
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 00:56
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.yimiao.yimiaocloud.common.constant.EmailSubjectEnum;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;

public class EmailUtil {
    public static final String SMTP_HOST = "smtp.163.com";
    public static final String EMAIL_ACCOUNT = "13192269856@163.com";
    public static final String NICK = "yimiao";
    public static final String EMAIL_AUTH_CODE = "yimiao163";
    public static final String SYSTEM_NAME = " [YimiaoMail] ";
    private static Logger log = LoggerFactory.getLogger(EmailUtil.class);

    public static boolean sendMail(String emailAddress, String content, EmailSubjectEnum emailSubjectEnum) {
        try {
            HtmlEmail email = new HtmlEmail();
            // smtp 地址
            email.setHostName(SMTP_HOST);
            email.setCharset("UTF-8");
            // 此处填邮箱地址和用户名,用户名可以任意填写
            email.setFrom(EMAIL_ACCOUNT, NICK);
            email.setAuthentication(EMAIL_ACCOUNT, EMAIL_AUTH_CODE);
            // 收件地址
            email.addTo(emailAddress);
            // 邮件主题和内容
            email.setSubject(SYSTEM_NAME + emailSubjectEnum.getMessage());
            switch (emailSubjectEnum) {
                case ACTIVATE:
                    email.setMsg("打开下面网址即可以完成注册" + content);
                    break;
                case RESETPASSWORD:
                    break;
                case PAY_CHECK:
                    email.setMsg(content);
            }
            email.send();
            log.info(String.format("邮件发送成功。 收件人 : { %s }, 主题 : { %s }, 内容 : { %s }", emailAddress, email.getSubject(), content));
            return true;

        } catch (EmailException e) {
            throw new BusinessException(GlobalErrorCodeEnum.EMAIL_ERROR);
        }
    }


//    public static void main(String[] args) {
//
//        EmailUtil.sendMail("yimiaoxiehou@gmail.com", "1231234", EmailSubjectEnum.ACTIVATE);
//
//    }

}
