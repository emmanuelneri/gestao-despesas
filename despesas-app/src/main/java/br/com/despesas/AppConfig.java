package br.com.despesas;

import br.com.despesas.controller.DespesaRestController;
import br.com.despesas.model.Despesa;
import br.com.despesas.repository.DespesaRepository;
import br.com.despesas.service.DespesaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackageClasses = {Despesa.class, Jsr310JpaConverters.class})
@ComponentScan(basePackageClasses = {DespesaService.class, DespesaRestController.class})
@EnableJpaRepositories(basePackageClasses = {DespesaRepository.class})
public class AppConfig {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AppConfig.class, args);
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configurarCors(source);
        return new FilterRegistrationBean(new CorsFilter(source));
    }

    private void configurarCors(UrlBasedCorsConfigurationSource source) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
    }
}
