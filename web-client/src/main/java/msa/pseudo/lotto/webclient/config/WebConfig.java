package msa.pseudo.lotto.webclient.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class WebConfig implements WebFluxConfigurer {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8072/").build();
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }



    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder().filter(lbFunction).baseUrl("lb://hellodocker").build();
//    }

//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder().baseUrl("http://localhost:8091/").build();
//    }



    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("127.0.0.1", "http://localhost:8080")
//                .allowedOrigins("localhost")
//                .allowedMethods("GET", "POST")
//                .maxAge(3600);
//
//
//    }
//
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(false);
//        config.addAllowedOrigin("localhost:8080");
//        config.addAllowedOrigin("http://localhost:8080/");
//        config.addAllowedOrigin("http://127.0.0.1:8080/");
//        config.addAllowedOrigin("http://localhost:8080/users/join");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return new CorsWebFilter(source);
//    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        resolver.setCheckExistence(false);
        return resolver;
    }

    // start
    @Bean
    public ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {
        ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        viewResolver.setResponseMaxChunkSizeBytes(8192);
        return viewResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafReactiveViewResolver());
    }

    // end
//
//    private final ISpringWebFluxTemplateEngine templateEngine;
//
//    @Bean
//    public ThymeleafReactiveViewResolver thymeleafChunkAndDataDrivenViewResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setPrefix("classpath:templates/");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode(TemplateMode.HTML);
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setCacheable(false);
//        resolver.setCheckExistence(false);
////        templateEngine.setTemplateResolver
//
//
//        final ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
//        viewResolver.setTemplateEngine(templateEngine);
//        viewResolver.setOrder(1);
//        viewResolver.setResponseMaxChunkSizeBytes(8192);
//        return viewResolver;
//    }
}
