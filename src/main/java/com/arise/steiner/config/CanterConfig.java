package com.arise.steiner.config;

import com.arise.canter.Registry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.arise.canter.Defaults.CMD_PRINT;
import static com.arise.canter.Defaults.EVT_FAIL;
import static com.arise.canter.Defaults.EVT_SUCCESS;

@Configuration
public class CanterConfig {

    @Bean
    public Registry registry(){
        return new Registry()
                .addEvent(EVT_FAIL)
                .addEvent(EVT_SUCCESS)
                .setAsyncExecutor(CanterExecutor.class)
                .addCommand(CMD_PRINT);
    }
}
