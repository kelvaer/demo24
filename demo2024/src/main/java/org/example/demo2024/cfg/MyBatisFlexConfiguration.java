package org.example.demo2024.cfg;

/**
 * @program: demo2024
 * @description: cfg2
 * @author: 作者名
 * @create: 2024/02/01
 */
import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisFlexConfiguration {

    private static final Logger logger = LoggerFactory
            .getLogger("mybatis-flex-sql");


    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                logger.info("{} ,执行耗时{}ms", auditMessage.getFullSql()
                        , auditMessage.getElapsedTime())
        );
    }
}
