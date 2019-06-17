package com.gfg.catalog.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

  @Value("${application.title}")
  private String title;

  @Value("${application.description}")
  private String description;

  @Value("${application.version}")
  private String version;

  @Value("${application.basePackage}")
  private String basePackage;

  @Value("${application.email}")
  private String email;

  @Value("${application.license}")
  private String license;

  @Value("${application.website}")
  private String website;


  private static final String API_PATH = ".*";

  @Bean
  public Docket v1DocPlugin() {
      return new VersionedDocket("v1");
  }

  class VersionedDocket extends Docket {
    public VersionedDocket(String version) {
      super(DocumentationType.SWAGGER_2);
      super.groupName(version)
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(regex("/" + version + API_PATH))
          .build().apiInfo(metaInfo())
          .pathProvider(new BasePathAwareRelativePathProvider("/" +version));
    }
  }

  private ApiInfo metaInfo() {
    return new ApiInfoBuilder()
        .title(title)
        .version(version)
        .contact(new Contact("Shridhar Hitnalli", website, email))
        .description(description)
        .license(license)
        .build();
  }

  private class BasePathAwareRelativePathProvider extends AbstractPathProvider {
      private String basePath;


      public BasePathAwareRelativePathProvider(String basePath){
        this.basePath = basePath;
      }


      @Override
      protected String applicationPath() {
        return basePath;
      }


      @Override
      protected String getDocumentationPath() {
        return "/";
      }


      @Override
      public String getOperationPath(String operationPath) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
        return Paths.removeAdjacentForwardSlashes(
            uriComponentsBuilder.path(operationPath.replaceFirst(basePath, "")).build().toString());
      }
  }
}


