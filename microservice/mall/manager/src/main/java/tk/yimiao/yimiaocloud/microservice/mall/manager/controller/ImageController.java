/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.manager.controller
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-12 18:29
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins = "*")
@Api(description = "图片上传统一接口")
public class ImageController {

//    @RequestMapping(value = "/image/imageUpload",method = RequestMethod.POST)
//    @ApiOperation(value = "WebUploader图片上传")
//    public Result<Object> uploadFile(@RequestParam("file") MultipartFile files,
//                                     HttpServletRequest request){
//
//        String imagePath=null;
//        // 文件保存路径
//        String filePath = request.getSession().getServletContext().getRealPath("/upload")+"\\"
//                + QiniuUtil.renamePic(files.getOriginalFilename());
//        // 转存文件
//        try {
//            //保存至服务器
//            File file=new File((filePath));
//            files.transferTo(file);
//            //上传七牛云服务器
//            imagePath= QiniuUtil.qiniuUpload(filePath);
//            if(imagePath.contains("error")){
//                throw new XmallUploadException("上传失败");
//            }
//            // 路径为文件且不为空则进行删除
//            if (file.isFile() && file.exists()) {
//                file.delete();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResultUtil<Object>().setData(imagePath);
//    }
//
//    @RequestMapping(value = "/kindeditor/imageUpload",method = RequestMethod.POST)
//    @ApiOperation(value = "KindEditor图片上传")
//    public KindEditorResult kindeditor(@RequestParam("imgFile") MultipartFile files, HttpServletRequest request){
//
//        KindEditorResult kindEditorResult=new KindEditorResult();
//        // 文件保存路径
//        String filePath = request.getSession().getServletContext().getRealPath("/upload")+"\\"
//                + QiniuUtil.renamePic(files.getOriginalFilename());
//        //检查文件
//        String message=QiniuUtil.isValidImage(request,files);
//        if(!message.equals("valid")){
//            kindEditorResult.setError(1);
//            kindEditorResult.setMessage(message);
//            return kindEditorResult;
//        }
//        // 转存文件
//        try {
//            //保存至服务器
//            File file=new File((filePath));
//            files.transferTo(file);
//            //上传七牛云服务器
//            String imagePath=QiniuUtil.qiniuUpload(filePath);
//            if(imagePath.contains("error")){
//                kindEditorResult.setError(1);
//                kindEditorResult.setMessage("上传失败");
//                return kindEditorResult;
//            }
//            // 路径为文件且不为空则进行删除
//            if (file.isFile() && file.exists()) {
//                file.delete();
//            }
//            kindEditorResult.setError(0);
//            kindEditorResult.setUrl(imagePath);
//            return kindEditorResult;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        kindEditorResult.setError(1);
//        kindEditorResult.setMessage("上传失败");
//        return kindEditorResult;
//    }
}