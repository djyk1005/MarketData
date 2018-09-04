package com.sapient.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.SymbolsRequestBuilder;

public class JsonWriter {
	JSONObject obj = new JSONObject();
	JSONArray arr = new JSONArray();
	CompData cd = new CompData();
	List<String> companies = new ArrayList<String>();
	
	
	@SuppressWarnings({ "unchecked", "resource" })
	public void writeJson() throws IOException {
		FileWriter file = new FileWriter("C:\\Users\\patcrowl\\Desktop\\JSONListings\\json_companies.json");
		final IEXTradingClient iexTradingClient = IEXTradingClient.create();
		final List<ExchangeSymbol> exchangeSymbolList = iexTradingClient.
				executeRequest(new SymbolsRequestBuilder().build());
		companies = cd.allCompNames(exchangeSymbolList);
		for(String name: companies) {
			String key = "Name";
			obj.put(key, name);
			file.write(obj.toJSONString());
		}
		
	}
	public void writeToText() throws IOException {
		final IEXTradingClient iexTradingClient = IEXTradingClient.create();
		final List<ExchangeSymbol> exchangeSymbolList = iexTradingClient.
				executeRequest(new SymbolsRequestBuilder().build());
		companies = cd.allCompNames(exchangeSymbolList);
		FileWriter file = new FileWriter("C:\\Users\\patcrowl\\Desktop\\JSONListings\\companies.txt");
		for(String company:companies) {
			file.write(company);
		}
		file.close();
	}
	
/*	public static void main(String[]args) throws IOException {
		JsonWriter jw = new JsonWriter();
		//jw.writeJson();
		jw.writeToText();
	}*/
	
}
