package com.nguyenphucthienan.msscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// @Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer connectTimeout;
    private final Integer ioThreadCount;
    private final Integer soTimeout;
    private final Integer defaultMaxPerRoute;
    private final Integer maxTotal;

    public NIORestTemplateCustomizer(@Value("${npta.connectiontimeout}") Integer connectTimeout,
                                     @Value("${npta.iothreadcount}") Integer ioThreadCount,
                                     @Value("${npta.sotimeout}") Integer soTimeout,
                                     @Value("${npta.defaultmaxperroute}") Integer defaultMaxPerRoute,
                                     @Value("${npta.maxtotal}") Integer maxTotal) {
        this.connectTimeout = connectTimeout;
        this.ioThreadCount = ioThreadCount;
        this.soTimeout = soTimeout;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.maxTotal = maxTotal;
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(clientHttpRequestFactory());
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
        final DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(IOReactorConfig
                .custom()
                .setConnectTimeout(connectTimeout)
                .setIoThreadCount(ioThreadCount)
                .setSoTimeout(soTimeout)
                .build());

        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setMaxTotal(maxTotal);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients
                .custom()
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }
}
