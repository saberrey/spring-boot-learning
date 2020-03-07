package com.springboot.learning;

import com.springboot.learning.Ana.EnableLimitedIpAccess;
import com.springboot.learning.entity.Book;
import com.springboot.learning.entity.Msg;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.Cacheable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@EnableLimitedIpAccess
@SpringBootApplication

public class LearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);
    }

    @RequestMapping("/index")
    public String index(Model model){
        Msg msg = new Msg("标题","内容","只对管理员现实");
        model.addAttribute("msg",msg);
        return "home";

    }

    @RequestMapping("/limited")
    public String  limited(){
        return "limited";
    }
}
