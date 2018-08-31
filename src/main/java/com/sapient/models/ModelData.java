package com.sapient.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.SymbolsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

public class ModelData {
	final static IEXTradingClient iexTradingClient = IEXTradingClient.create();
	
	public static void main(String[] args) {
		
		final List<ExchangeSymbol> exchangeSymbolList = iexTradingClient.executeRequest(new SymbolsRequestBuilder()
				.build());
//		for(ExchangeSymbol e: exchangeSymbolList ) {
//			System.out.println(e);
//		}
		
		List<String> tickers = new ArrayList<String>();
		
		for(int i = 0; i< 10 ; i++) {
			ExchangeSymbol es = exchangeSymbolList.get(i);
			tickers.add(es.getSymbol());
		}
		
		List<Quote> res = tickers.stream().map(t -> ModelData.get(t)).collect(Collectors.toList());
		for(Quote q:res) {
			System.out.println(q);
		}
			
	}
	
	public static Quote get(String tick) {
		return iexTradingClient.executeRequest(new QuoteRequestBuilder()
		        .withSymbol(tick)
		        .build());
	}
	
}
