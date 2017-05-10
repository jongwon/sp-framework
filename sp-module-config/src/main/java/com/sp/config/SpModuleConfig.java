package com.sp.config;

import com.sp.web.config.ModuleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jongwon on 2017. 5. 10..
 */
@Configuration
//@ComponentScan("com.sp.web.config")
public class SpModuleConfig {

	@Bean
	public ModuleRegistry getModuleRegistry(){
		return new ModuleRegistry();
	}

}
