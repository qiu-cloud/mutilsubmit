package com.bosssoft.mutilsubmit;

import com.bosssoft.mutilsubmit.interceptor.AccessLimitInterceptor;
import com.bosssoft.mutilsubmit.interceptor.ApiIdempotentInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//在SpringBoot中集成MyBatis，可以在mapper接口上添加@Mapper注解，将mapper注入到Spring,
// 但是如果每一给mapper都添加@mapper注解会很麻烦，这时可以使用@MapperScan注解来扫描包。


@SpringBootApplication
//@MapperScan("com.bosssoft.mapper")
//@EnableScheduling
public class TestApplication  extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	/**
	 * 跨域
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 接口幂等性拦截器
		registry.addInterceptor(apiIdempotentInterceptor());
		// 接口防刷限流拦截器
		registry.addInterceptor(accessLimitInterceptor());

		super.addInterceptors(registry);
	}

	@Bean
	public ApiIdempotentInterceptor apiIdempotentInterceptor() {
		return new ApiIdempotentInterceptor();
	}

	@Bean
	public AccessLimitInterceptor accessLimitInterceptor() {
		return new AccessLimitInterceptor();
	}

}
