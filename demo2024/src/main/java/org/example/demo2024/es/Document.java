package org.example.demo2024.es;

import lombok.Data;
import org.dromara.easyes.annotation.IndexName;

/**
 * @program: demo2024
 * @description: 文档
 * @author: 作者名
 * @create: 2024/02/04
 */
@Data
@IndexName
public class Document {
    /**
     * es中的唯一id
     */
    private String id;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档内容
     */
    private String content;
}

