package ori.loproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		addResourceHandler : 스프링부트에서 확인할 폴더 위치 설정 (img 폴더 밑에 아무거나 다 들어갈 수 있다)
//		addResourceLocations : 실제 시스템의 폴더 위치, 윈도우 시스템의 경우 'file:///경로' 형태로 사용

        registry.addResourceHandler("/item/**").addResourceLocations("file:///C:/shop/item/");

        /* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
        /* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
        /* '/img/**'로 호출하는 자원은 '/static/img/' 폴더 아래에서 찾는다. */
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365);
        /* '/font/**'로 호출하는 자원은 '/static/font/' 폴더 아래에서 찾는다. */
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365);
    }
}