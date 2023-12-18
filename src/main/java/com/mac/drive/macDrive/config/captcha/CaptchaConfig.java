package com.mac.drive.macDrive.config.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Captcha Configuration
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        // Captcha generator
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // Configuration
        Properties properties = new Properties();
        // Whether there is a border
        properties.setProperty("kaptcha.border", "yes");
        // Set border color
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // Border thickness, default is 1
        // properties.setProperty("kaptcha.border.thickness","1");
        // Captcha
        properties.setProperty("kaptcha.session.key", "code");
        // Captcha text character color, default is black
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // Set font style
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅 黑");
        // Font size, default 40
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // Captcha text character content range, default is abced2345678gfynmnpwx
        // properties.setProperty("kaptcha.textproducer.char.string", "");
        // Character length, default is 5
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // Character spacing, default is 2
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        // Captcha image width, default is 200
        properties.setProperty("kaptcha.image.width", "100");
        // Captcha image height, default is 40
        properties.setProperty("kaptcha.image.height", "40");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
