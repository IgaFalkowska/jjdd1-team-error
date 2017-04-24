package com.infoshareacademy.jjdd1.teamerror;

import com.infoshareacademy.jjdd1.teamerror.file_loader.*;
import com.infoshareacademy.jjdd1.teamerror.trendy_engine.Trendy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by krystianskrzyszewski on 19.04.17.
 */

@WebServlet(urlPatterns = "/calc")
public class InitialServlet extends HttpServlet {

    private final Logger LOGGER = LoggerFactory.getLogger(InitialServlet.class);

    Trendy trendy;
    TripFullCost cost;
    FilesContent filesContent;
    CountryAndCurrency countryAndCurrency;
    Map<String, String> countryAndCurrencyList;
    PromotedCountries promotedCountries;

    public InitialServlet() {
        super();
        LOGGER.info("InitialServlet initialisation");

        trendy = new Trendy();

        filesContent = new OnDemandFilesContent();
        CurrencyFileFilter currencyFileFilter = new CurrencyFileFilter();
        PetrolFileFilter petrolFileFilter = new PetrolFileFilter();
        currencyFileFilter.setFilesContent(filesContent);
        petrolFileFilter.setFilesContent(filesContent);
        cost = new TripFullCost();
        cost.setTripFullCost(filesContent, petrolFileFilter, currencyFileFilter);
        cost.setCountryAndCurrency(new CountryAndCurrency());
        trendy.setCurrencyFileFilter(currencyFileFilter);
        trendy.setPetrolFileFilter(petrolFileFilter);
        countryAndCurrency = new CountryAndCurrency();
        LOGGER.info("InitialServlet initialised");
        promotedCountries = new PromotedCountries();
        promotedCountries.setFilesContent(filesContent);


    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("servlet request");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");
        filesContent.getPetrolDataFile();
        filesContent.getCurrencyInfoFile();

        countryAndCurrency.setFilesContent(filesContent);
        countryAndCurrencyList = countryAndCurrency.getCountriesAndCurrency();

        File petrolFile = new File(System.getProperty("java.io.tmpdir")+"/files/" + "iSA-PetrolPrices.csv");
        File currencyInfoFile = new File(System.getProperty("java.io.tmpdir")+"/files/" + "omeganbp.lst.txt");
        File currencyZipFile = new File(System.getProperty("java.io.tmpdir")+"/files/" + "omeganbp.zip");
        if(!petrolFile.exists() || !currencyInfoFile.exists() || !currencyZipFile.exists()) {
            req.setAttribute("missingFile",  "yes");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/missingFiles.jsp");
            dispatcher.forward(req, resp);
        }

        if (req.getParameter("start") != null || req.getParameter("initialData") != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/initialData.jsp");
            req.setAttribute("countryList", promotedCountries.getOrderedPromotedCountries());
            dispatcher.forward(req, resp);
        }
        else if (req.getParameter("initialization") != null) {
            String country = req.getParameter("country").toUpperCase();
            String fuelType = req.getParameter("fuelType");

            cost.setCountry(country);
            cost.setFuelType(fuelType);

            LOGGER.info("servlet req params: {} {}", country, cost.getFuelType());
            req.setAttribute("title", "Menu");
            req.setAttribute("country", cost.getCountry());
            req.setAttribute("currency", cost.getCurrency());
            req.setAttribute("fuelType", cost.getFuelType());

            RequestDispatcher dispatcher = req.getRequestDispatcher("/menu.jsp");
            dispatcher.forward(req, resp);
        }
        else if (req.getParameter("trendy") != null) {

            req.setAttribute("title", "Optimal time for trip");
            req.setAttribute("country", cost.getCountry());
            req.setAttribute("currency", cost.getCurrency());
            req.setAttribute("fuelType", cost.getFuelType());


            trendy.setTrendy(cost.getCurrency(), cost.getFuelType(), cost.getCountry());
            LOGGER.info("calculated trend for trip: {},{},{}", cost.getCountry(), cost.getCurrency(), cost.getFuelType());
            req.setAttribute("petrolTrendy", trendy.getPetrolTrendy());
            req.setAttribute("currencyTrendy", trendy.getCurrencyTrendy());
            req.setAttribute("conclusion", trendy.getConclusion());

            RequestDispatcher dispatcher = req.getRequestDispatcher("/trendy.jsp");
            dispatcher.forward(req, resp);
        }
        else if (req.getParameter("additionalData") != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/tripOtherData.jsp");
            dispatcher.forward(req, resp);
        }
        else if (req.getParameter("tripCost") != null) {

            try {
                String date1 = req.getParameter("date1");
                String date2 = req.getParameter("date2");
                String fuelUsage = req.getParameter("fuelUsage");
                String fullDistance = req.getParameter("fullDistance");

                String fuelUsageString;
                try{
                    if(fuelUsage.toString().equals("")){
                        fuelUsageString = "No input recorded";
                    }
                    else {
                        cost.setFuelUsage(fuelUsage);
                        fuelUsageString = cost.getFuelUsage().toString();
                    }
                }catch(Exception e){
                    fuelUsageString = fuelUsage + " is a wrong fuel usage input. ";
                }

                String fullDistanceString;
                try{
                    if(fullDistance.toString().equals("")){
                        fullDistanceString = "No input recorded";
                    }
                    else{
                        cost.setDistance(fullDistance);
                        fullDistanceString = cost.getDistance().toString();
                    }
                }catch(Exception e){
                    fullDistanceString = fullDistance + " is a wrong distance input. ";
                }

                String date1String;
                try{
                    if(date1.toString().equals("")){
                        date1String = "No input recorded";
                    }
                    else {
                        cost.setDate1(date1);
                        date1String = cost.getDate1().toString();
                    }
                }catch(Exception e){
                    date1String = date1 + " is a wrong date.";
                }

                String date2String;
                try{
                    if(date2.toString().equals("")){
                        date2String = "No input recorded";
                    }
                    else {
                        cost.setDate2(date2);
                        date2String = cost.getDate1().toString();
                    }
                }catch(Exception e){
                    date2String = date2 + " is a wrong date.";
                }

                String fullCostString;
                try{
                    Double costCount = cost.costCount();
                    fullCostString = String.valueOf(costCount) + " PLN";
                }catch(Exception e){
                    fullCostString = "Something went wrong. Please check your input (above)";
                }

                req.setAttribute("title", "Menu");
                req.setAttribute("currency", cost.getCurrency());
                req.setAttribute("country", cost.getCountry());
                req.setAttribute("fuelType", cost.getFuelType());
                req.setAttribute("date1", date1String);
                req.setAttribute("date2", date2String);
                req.setAttribute("fuelUsage", fuelUsageString);
                req.setAttribute("fullDistance", fullDistanceString);
                req.setAttribute("fullCost", fullCostString);

                RequestDispatcher dispatcher = req.getRequestDispatcher("/tripCost.jsp");
                dispatcher.forward(req, resp);

            }catch(Exception e){
                RequestDispatcher dispatcher = req.getRequestDispatcher("/exceptionHandling.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }
}
