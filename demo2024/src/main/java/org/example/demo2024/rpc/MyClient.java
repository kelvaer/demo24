package org.example.demo2024.rpc;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.callback.OnProgress;
import com.dtflys.forest.extensions.DownloadFile;

import java.io.File;

public interface MyClient {

//    @Get("https://tool.lu/netcard")
//    String helloForest();

    /**
     * 在方法上加上@DownloadFile注解
     * dir属性表示文件下载到哪个目录
     * filename属性表示文件下载成功后以什么名字保存，如果不填，这默认从URL中取得文件名
     * OnProgress参数为监听上传进度的回调函数
     */
    @Get(url = "https://tool.lu/netcard")
    @DownloadFile(dir = "${0}", filename = "${1}")
    File downloadFile(String dir, String filename, OnProgress onProgress);

}

