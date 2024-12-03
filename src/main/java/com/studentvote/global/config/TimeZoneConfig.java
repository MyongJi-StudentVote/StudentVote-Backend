package com.studentvote.global.config;

import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeZoneConfig {

    @Bean
    public TimeZone timeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        return TimeZone.getDefault();
    }
}
