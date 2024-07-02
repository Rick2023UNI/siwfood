package it.uniroma3.siwfood.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigController implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path imagesUploadDir = Paths.get("./images");
		String imagesUploadPath = imagesUploadDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/images/**").addResourceLocations("file:/"+imagesUploadPath + "/");
		
		//Immagini del sito
		imagesUploadDir = Paths.get("./src/main/resources/static/image");
		imagesUploadPath = imagesUploadDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/image/**").addResourceLocations("file:/"+imagesUploadPath + "/");

	}
}
