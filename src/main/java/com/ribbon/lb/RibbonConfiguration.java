package com.ribbon.lb;


import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/*
Don't need to use @Configuration
already component scanned using SpringBootRibbonClientBalacingApplication

Default Iping - NoOpPing means
(which does not actually ping server instances, but always report
they are stable)

default IRule - ZoneAvoidanceRule means
(which avoids the Amazon EC2 zone that has most malfunctioning servers,
might be difficult to try that here locally)

IPing - PingUrl - will ping the server instances & check the status of server
instances.

IRule - AvailabilityFilteringRule - will use Ribbon's
built-in circuit breaker functionality to filer out any servers
in an "open-circuit" state: if a ping fails to connect to server or
if gets a read failure for the server, Ribbon will consider
that the server is "dead" until it begins to respond normally

 */

//@Configuration
public class RibbonConfiguration {

    @Autowired
    IClientConfig ribbonClientConfig;

    /*@Bean
    public IClientConfig ribbonClientConfig(){
        return new DefaultClientConfigImpl();
    }*/

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
       // return new WeightedResponseTimeRule();
    }

    /*@Bean
    public ServerList<Server> ribbonServerList(IClientConfig config) {
        return new ConfigurationBasedServerList();
    }

    @Bean
    public ServerListFilter<Server> ribbonServerListFilter(IClientConfig config) {
        return new ZonePreferenceServerListFilter();
    }*/

}
