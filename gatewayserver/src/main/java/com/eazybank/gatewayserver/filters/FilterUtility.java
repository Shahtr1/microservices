package com.eazybank.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {
    public static final String CORRELATION_ID = "eazybank-correlation-id";

    public String getCorrelationId(HttpHeaders httpHeaders){
        if(httpHeaders.get(CORRELATION_ID) != null){
            List<String> requestHeaderList = httpHeaders.get(CORRELATION_ID);
            assert requestHeaderList != null;
            return requestHeaderList.stream().findFirst().orElse(null);
        }else{
            return null;
        }
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value){
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId){
        return setRequestHeader(exchange,CORRELATION_ID, correlationId);
    }
}
