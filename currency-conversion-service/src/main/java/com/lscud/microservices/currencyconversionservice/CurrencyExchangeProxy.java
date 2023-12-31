package com.lscud.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange", url="localhost:8000")
@FeignClient(name="currency-exchange") //apenas dessa forma este microserviço irá consultar o naming-server para saber qual instancia do currency-exchange está diponivel.
public interface CurrencyExchangeProxy {

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion   retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
