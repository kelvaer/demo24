package org.example.demo2024.api.sys;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.cfg.ResultBody;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @program: demo2024
 * @description: sys
 * @author: 作者名
 * @create: 2024/02/05
 */
@Slf4j
@RestController
public class SysLogApi {

    @Value("${logging.file.path:/home}")
    private String logPath;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private Integer port;

    //--------------------------------------------------------------------
    /*
http://localhost:8627/iotcenter/alterLogLevel/info
     */
    @PostMapping("/alterSysLogLevel/{level}")
    public ResultBody<Void> alterSysLogLevel(@PathVariable String level){
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger("ROOT").setLevel(Level.valueOf(level));
        return ResultBody.success();
    }


    /*
http://localhost:8627/iotcenter/alterPkgLogLevel?level=info&pkgName=com.xx.xxx
     */

    @PostMapping("/alterPkgLogLevel")
    public ResultBody<Void> alterPkgLogLevel(@RequestParam String level, @RequestParam String pkgName){
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(pkgName).setLevel(Level.valueOf(level));
        return ResultBody.success();
    }
//————————————————
//    版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。
//    原文链接：https://blog.csdn.net/ThinkPet/article/details/131491627



    @GetMapping("/down/log")
    public void downLog(HttpServletResponse response) throws Exception {

        File fileZip = ZipUtil.zip(logPath);
        FileInputStream fis = new FileInputStream(fileZip);
        String currentDate = LocalDateTimeUtil.now().toString();
        String fName = appName+"-"+port+"-诊断日志-"+currentDate+".zip";
        log.info("导出文件:{}",fName);
        response.setHeader("Content-Disposition","attachment;filename="+new String(fName.getBytes(), StandardCharsets.ISO_8859_1));
        response.setContentType("application/x-zip-compressed;charset=utf-8");
        ServletOutputStream ops = response.getOutputStream();
        IoUtil.copy(fis,ops);
        IoUtil.closeIfPosible(fis);
        IoUtil.closeIfPosible(ops);
        FileUtil.del(fileZip);
    }
}
