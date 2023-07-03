package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//@FeignClient(name="currency-exchange", url="localhost:8000")
//@FeignClient(name="currency-exchange")

//CHANGE-KUBERNETES
//@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000") //se a variavel de ambiente CURRENCY_EXCHANGE_SERVICE_HOST nao existir aí será usado localhost. Essa variavel é usada no kubernets e o lcoal host no ambiente local
@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_URI:http://localhost}:8000") // Fazemos isso pois caso o currency-exchange fique Down a VAR fornecida pelo kubernets - CURRENCY_EXCHANGE_SERVICE_HOST ficará indisponível. PAra resolver esta situação criamos uma variável nossa - CURRENCY_EXCHANGE_URI
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to);

}
