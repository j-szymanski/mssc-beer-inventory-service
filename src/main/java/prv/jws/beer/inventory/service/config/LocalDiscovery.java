package prv.jws.beer.inventory.service.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Jerzy Szymanski on 04.10.2020 at 16:19
 */
@Slf4j
@Profile("local-discovery")
@EnableDiscoveryClient
@Configuration
public class LocalDiscovery {
    public LocalDiscovery(@Value("${eureka.client.serviceUrl.defaultZone}") String eurekaUrl) {
        log.info("    >>>>    LOCAL DISCOVERY: eureka {}", eurekaUrl);
    }
}
