package com.sapient.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapient.data.CompData;
import com.sapient.models.Company;
import com.sapient.repos.MarketDataRepo;

import pl.zankowski.iextrading4j.api.exception.IEXTradingException;
import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.SymbolsRequestBuilder;


@Controller
public class MarketControl {
	
	CompData cd = new CompData();

	{final IEXTradingClient iexTradingClient = IEXTradingClient.create();
	final List<ExchangeSymbol> exchangeSymbolList = iexTradingClient.executeRequest(new SymbolsRequestBuilder()
			.build());
	cd.addData(exchangeSymbolList);
	}

	@Autowired
	MarketDataRepo repo;

	@RequestMapping(path="/find", method=RequestMethod.GET)
	public String findComp(Model model, @RequestParam("ticker") String tick, @RequestParam("dropdown") String drop){
		Company comp = null;
		if(drop.equals("ticker")) {
			try {
				comp = repo.findCompTick(tick);
			}catch(IEXTradingException e ) {
				System.out.println("hello");
			}

		}else {
			tick = cd.findTicker(tick);
			System.out.println(tick);
			try {
				comp = repo.findCompTick(tick);
			}catch(IEXTradingException e ) {
				System.out.println("hello");
			}
		}

		model.addAttribute("company", comp);
		return "stockinfo";
	}

	@RequestMapping(path="/seeMore", method=RequestMethod.GET)
	public String details(Model model,  @RequestParam("ticker") String tick){
		Company comp = null;
		try {
			comp = repo.findCompTick(tick);
		}catch(IEXTradingException e ) {
			System.out.println("hello");
		}
		model.addAttribute("company", repo.detailedInfo(comp));
		return "detailedinfo";
	}
	
	@RequestMapping(path="/search", method=RequestMethod.GET)
	public String search(Model model,  @RequestParam("name") String name){
		List<String> comps = new ArrayList<String>();
		List<Company> compList = new ArrayList<Company>();
		try {
			comps = cd.findTickers(name).stream().map(comp -> cd.findTicker(comp)).collect(Collectors.toList());
			compList = comps.stream().map(c -> repo.findCompTick(c)).collect(Collectors.toList());
		}catch(IEXTradingException e ) {
			System.out.println("hello");
		}
		model.addAttribute("companies", compList);
		return "search";
	}


}
