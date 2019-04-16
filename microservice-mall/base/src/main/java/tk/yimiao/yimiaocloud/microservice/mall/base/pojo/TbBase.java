package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;

import lombok.Data;

@Data
public class TbBase {
    private Integer id;

    private String webName;

    private String keyWord;

    private String description;

    private String sourcePath;

    private String uploadPath;

    private String copyright;

    private String countCode;

    private Integer hasLogNotice;

    private String logNotice;

    private Integer hasAllNotice;

    private String allNotice;

    private String notice;

    private String updateLog;

    private String frontUrl;

    public void setWebName(String webName) {
        this.webName = webName == null ? null : webName.trim();
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath == null ? null : sourcePath.trim();
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath == null ? null : uploadPath.trim();
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright == null ? null : copyright.trim();
    }

    public void setCountCode(String countCode) {
        this.countCode = countCode == null ? null : countCode.trim();
    }

    public void setLogNotice(String logNotice) {
        this.logNotice = logNotice == null ? null : logNotice.trim();
    }

    public void setAllNotice(String allNotice) {
        this.allNotice = allNotice == null ? null : allNotice.trim();
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog == null ? null : updateLog.trim();
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl == null ? null : frontUrl.trim();
    }
}