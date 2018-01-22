package jupiterpa.weather.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	ApplicationConfig appl;
	
    @Bean
    public Docket productApi() {
    	String basePackage = "jupiterpa." + appl.getName() + ".intf.controller";
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }
    private ApiInfo metaData() {
		@SuppressWarnings("deprecation")
		ApiInfo apiInfo = new ApiInfo(
        		appl.getName(),
        		appl.getDescription(),
                appl.getVersion(),
                "",
                "JupiterPa",
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0"
                );
        return apiInfo;
    }
}