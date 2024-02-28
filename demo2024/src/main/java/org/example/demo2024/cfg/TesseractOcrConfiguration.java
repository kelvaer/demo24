package org.example.demo2024.cfg;

/**
 * @program: demo2024
 * @description: ocr
 * @author: 作者名
 * @create: 2024/02/06
 */
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @作者:
 * @日期: 2023/10/12 22:58
 * @描述:
 */
@Configuration
public class TesseractOcrConfiguration {

    @Value("${tss4j.datapath}")
    private String dataPath;

    @Bean
    public Tesseract tesseract() {

        Tesseract tesseract = new Tesseract();
        // 设置训练数据文件夹路径
        tesseract.setDatapath(dataPath);
        // 设置为中文简体
        tesseract.setLanguage("chi_sim");
        return tesseract;
    }
}
