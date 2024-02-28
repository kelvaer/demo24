package org.example.demo2024.cfg;

/**
 * @program: demo2024
 * @description: Knife4j配置
 * @author: 作者名
 * @create: 2024/02/04
 */
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfiguration {

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public Knife4jConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(true)
                .groupName("系统模块")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.demo2024.api"))
                .paths(PathSelectors.any())
                .build().extensions(openApiExtensionResolver.buildSettingExtensions());
    }




    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("MongoPlus测试工程")
                .contact(new Contact("jiachaoyang", "https://gitee.com/anwena", "j15030047216@163.com"))
                .termsOfServiceUrl("https://gitee.com/anwena/mongo-plus")
                .version("v1.0")
                .title("MongoPlus测试工程")
                .build();
    }
}
