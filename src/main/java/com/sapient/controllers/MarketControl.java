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
	
	/** repo takes care of communication with the IEX api. */
	@Autowired
	MarketDataRepo repo;
	
	/** cd holds the data of all the company names listed on IEX. */
	CompData cd = new CompData();
	List<String> compNames = new ArrayList<String>();
	
	/***********************Initialization Block******************************/

	{
	final IEXTradingClient iexTradingClient = IEXTradingClient.create();
	final List<ExchangeSymbol> exchangeSymbolList = iexTradingClient.
			executeRequest(new SymbolsRequestBuilder().build());
	cd.addData(exchangeSymbolList);
	compNames = cd.allCompNames(exchangeSymbolList);
	}
	
	/**********************************Methods********************************/
	
	/**
	 * takes in the parameters of tick and drop and finds a list of companies
	 * depending on the company's name or it's ticker and puts the 
	 * companies' information in model for the JSP file to read.
	 * 
	 * @param model
	 * 		  Holds information to be used in the JSP files.
	 * 
	 * @param tick
	 * 		  Either the company's name of it's ticker.
	 * 
	 * @param drop
	 * 		  Either "ticker" or "company".
	 * 
	 * @return string for the JSP file
	 */
	@RequestMapping(path="/find", method=RequestMethod.GET)
	public String findComp(Model model, @RequestParam("ticker") String tick, 
			@RequestParam("dropdown") String drop){	
		List<String> comps = new ArrayList<String>();
		List<Company> compList = new ArrayList<Company>();
		try {
			if(drop.equals("ticker")) {
				comps = cd.tickers(tick);
				compList = comps.stream().map(c -> repo.findCompTick(c)).
						collect(Collectors.toList());
			
			}else {
				comps = cd.findTickers(tick).stream().
						map(comp -> cd.findTicker(comp)).
						collect(Collectors.toList());
				compList = comps.stream().map(c -> repo.findCompTick(c)).
						collect(Collectors.toList());
			}
		}catch(IEXTradingException e ) {
			System.out.println("hello");
			return "invalid";
		}
		model.addAttribute("companies", compList);
		model.addAttribute("companyNames", compNames);
		return "SearchPageResults";
	}

	/**
	 * takes in the parameter of the ticker of a company and finds 
	 * more information about the company and adds it to model for 
	 * the JSP file to read.
	 * 
	 * @param model
	 * 		  Holds information to be used in the JSP files.
	 * 
	 * @param tick
	 * 		  ticker of the company in search.
	 * 
	 * @return string for the JSP file
	 */
	@RequestMapping(path="/seeMore", method=RequestMethod.GET)
	public String details(Model model,  @RequestParam("ticker") String tick,
			@RequestParam("dropdown") String drop){
		Company comp = null;
		List<String> comps = new ArrayList<String>();
		try {
			if(drop.equals("ticker")) {
				comp = repo.findCompTick(tick);
			
			}else {
				comps = cd.findTickers(tick).stream().
						map(c -> cd.findTicker(c)).
						collect(Collectors.toList());
				comp = comps.stream().map(c -> repo.findCompTick(c)).
						collect(Collectors.toList()).get(0);
			}
		}catch(IEXTradingException e ) {
			System.out.println("hello");
			return "invalid";
		}
		model.addAttribute("company", repo.detailedInfo(comp));
		model.addAttribute("companyNames", compNames);
		return "SearchPageResultsDetails";
	}
	
	
	/**
	 * Adds list of company names an goes to the search page.
	 * 
	 * @param model
	 * 		  Holds information to be used in the JSP files.
	 * 
	 * @param name
	 * 		  partial or full name/ticker of the company to search
	 * 
	 * @return string for the JSP file
	 */
	@RequestMapping(path="/start", method=RequestMethod.GET)
	public String search(Model model) {
		model.addAttribute("companies", compNames);
		return "SearchPage";
	}

}
