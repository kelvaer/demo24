package org.example.demo2024.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.example.demo2024.cfg.ResultBody;
import org.example.demo2024.dto.SysFileUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: demo2024
 * @description: sfs-api
 * @author: 作者名
 * @create: 2024/02/04
 */
@Slf4j
@RestController
public class FileDetailController {

    @Autowired
    private FileStorageService fileStorageService;//注入实列

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public FileInfo upload(@RequestPart MultipartFile file) {
        String name = file.getName();
        log.info("name:{}",name);
        String contentType = file.getContentType();
        log.info("contentType:{}",contentType);
        String originalFilename = file.getOriginalFilename();
        log.info("originalFilename:{}",originalFilename);
        return fileStorageService.of(file).upload();
    }

    @PostMapping("/upload3")
    public FileInfo upload3(@RequestPart MultipartFile file , @RequestParam String ext){

        String originalFilename = file.getOriginalFilename();
        log.info("originalFilename:{}",originalFilename);
        String mf = originalFilename+"."+ ext;
        log.info("mf:{}",mf);
        return fileStorageService.of(file)
                .putAttr("mf",mf)
                .upload();
    }


    @ApiOperation(value = "批量签名文件导入")
    @ApiImplicitParam(name ="files",required = true,dataType="MultipartFile",allowMultiple = true,paramType = "query")
    @PostMapping(value = "/uploadBatch")
    public ResultBody<List<FileInfo>>uploadBatch(@RequestParam(value = "files") MultipartFile[] files) {
        if (files==null || files.length==0){
            return ResultBody.error("files不能为空");
        }
        List<FileInfo> res = new ArrayList<>();
        for (MultipartFile file : files) {
            String name = file.getName();
            log.info("name:{}",name);
            String contentType = file.getContentType();
            log.info("contentType:{}",contentType);
            String originalFilename = file.getOriginalFilename();
            log.info("originalFilename:{}",originalFilename);
            FileInfo upload = fileStorageService.of(file).upload();
            res.add(upload);
        }
        return ResultBody.success(res);
    }










    /**
     * 上传文件，成功返回文件 url
     */
    @PostMapping("/upload2")
    public String upload2(@RequestPart MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath("upload/") //保存到相对路径下，为了方便管理，不需要可以不写
                .setObjectId("0")   //关联对象id，为了方便管理，不需要可以不写
                .setObjectType("0") //关联对象类型，为了方便管理，不需要可以不写
                .putAttr("role","admin") //保存一些属性，可以在切面、保存上传记录、自定义存储平台等地方获取使用，不需要可以不写
                .upload();  //将文件上传到对应地方
        return fileInfo == null ? "上传失败！" : fileInfo.getUrl();
    }

    /**
     * 上传图片，成功返回文件信息
     * 图片处理使用的是 https://github.com/coobird/thumbnailator
     */
    @PostMapping("/upload-image")
    public FileInfo uploadImage(@RequestPart MultipartFile file) {
        return fileStorageService.of(file)
                .image(img -> img.size(1000,1000))  //将图片大小调整到 1000*1000
                .thumbnail(th -> th.size(200,200))  //再生成一张 200*200 的缩略图
                .upload();
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/upload-platform")
    public FileInfo uploadPlatform(@RequestPart MultipartFile file) {
        return fileStorageService.of(file)
                .setPlatform("aliyun-oss-1")    //使用指定的存储平台
                .upload();
    }

    /**
     * 直接读取 HttpServletRequest 中的文件进行上传，成功返回文件信息
     * 使用这种方式有些注意事项，请查看文档 基础功能-上传 章节
     */
    @PostMapping("/upload-request")
    public FileInfo uploadPlatform(HttpServletRequest request) {
        return fileStorageService.of(request).upload();
    }
}
