package com.adru;

import com.adru.subject.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.adru.spring", "com.adru.service"})
@SpringBootApplication
public class ProviderMain {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProviderMain.class,args);
        new NettyServer("127.0.0.1",8080).startNettyServer();
    }
}
