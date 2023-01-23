package com.newz.api.common;

import com.newz.api.common.auth.AuthenticationInterceptor;
import com.newz.api.common.auth.NewzUserArgumentResolver;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private AuthenticationInterceptor authenticationInterceptor;

  @Override
  public void addCorsMappings(CorsRegistry corsRegistry) {
    corsRegistry.addMapping("/api/**")
        .allowedOrigins("*");
  }

  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(authenticationInterceptor)
        .excludePathPatterns("/api/user/login")
        .excludePathPatterns("/api/user/token/reissue")
        .excludePathPatterns("/api/keyword/fixed/list")
        .excludePathPatterns("/api/news/list")
        .excludePathPatterns("/swagger-ui/**")
        .excludePathPatterns("/swagger-resources/**")
        .excludePathPatterns("/v3/api-docs/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new NewzUserArgumentResolver());
  }

}
