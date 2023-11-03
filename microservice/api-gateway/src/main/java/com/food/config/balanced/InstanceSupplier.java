//package com.food.config.balanced;
//
//import org.springframework.cloud.client.DefaultServiceInstance;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import reactor.core.publisher.Flux;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class InstanceSupplier implements ServiceInstanceListSupplier {
//    private final String serviceId;
//
//    public InstanceSupplier(String serviceId) {
//        this.serviceId = serviceId;
//    }
//
//    @Override
//    public String getServiceId() {
//        return serviceId;
//    }
//
//    @Override
//    public Flux<List<ServiceInstance>> get() {
//        Flux<List<ServiceInstance>> instance = Flux.just(Arrays.asList(new DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8082, false)));
//
//        return instance;
//    }
//}