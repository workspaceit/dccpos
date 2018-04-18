package com.workspaceit.dccpos.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.*;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("com.workspaceit.dccpos.*")
public class WebConfig implements WebMvcConfigurer {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());
        converters.add(byteArrayHttpMessageConverter());
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    @Bean
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
        registry.addResourceHandler("/common/**").addResourceLocations("file://"+this.env.getCommonFilePath()+"/");
    }
    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.IMAGE_PNG);
        list.add(MediaType.IMAGE_JPEG);
        return list;
    }
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Bean(name = "fadeInList")
    public List<Double> fadeInList(){
        List<Double> list = new ArrayList<>();
        for(int i=0;i<=4;i++){
            list.add((double)i);
        }
        return list;
    }

    @Bean(name = "fadeOutList")
    public List<Double> fadeOutList(){
        List<Double> list = new ArrayList<>();
        for(int i=0;i<=4;i++){
            list.add((double)i);
        }
        return list;
    }


    @Bean(name="durationList")
    public  Set<Integer> Durations(){
        Set<Integer> durations = new HashSet<>();
        for(int i=1;i<=5;i++){
            durations.add(i);
        }

        return durations;
    }

    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate4Module to support lazy objects
        mapper.registerModule(new Hibernate5Module());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        messageConverter.setObjectMapper(mapper);

        return messageConverter;

    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setThreadNamePrefix("posTaskExecutor");
        executor.initialize();

        return executor;
    }

    @Bean
    public VelocityEngine getVelocityEngine(){

        VelocityEngine ve = new VelocityEngine();
        Properties props = new Properties();
        props.put("file.resource.loader.path", WebConfig.class.getResource("/email-template/").getPath());
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        ve.init(props);
        return ve;
    }


}
