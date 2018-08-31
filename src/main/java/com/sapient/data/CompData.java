package com.sapient.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;

public class CompData {
	Map<String, String> tickers;
	List<String> compName;
	
	public CompData() {
		super();
		this.tickers = new HashMap<String, String>();
		this.compName = new ArrayList<String>();
	}
	
	
	public void addData(List<ExchangeSymbol> esList) {
		esList.stream().forEach(es -> this.add(es));
		
	}
	
	public void add(ExchangeSymbol es) {
		this.tickers.put(es.getName().toLowerCase(), es.getSymbol());
		this.compName.add(es.getName().toLowerCase());
	}
	
	public List<String> findTickers(String cName) {
		List<String> results = this.compName.stream().filter(comp -> comp.contains(cName.toLowerCase()))
				.collect(Collectors.toList());
		if(results.size() > 10) {
			results = results.subList(0, 10);
		}
		return results;
	}
	
	public String findTicker(String name) {
		List<String> results = this.findTickers(name);
		return this.tickers.get(results.get(0));
	}
	
}
