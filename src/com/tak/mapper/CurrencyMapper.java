package com.tak.mapper;
import java.util.List;

import com.tak.pojo.Currency;

public interface CurrencyMapper {
    int addCurrency(Currency c);
    List<Currency> listCurrencies();
    List<String> listTradableCurrencies();
    List<String> listBondTradableCurrencies();
    List<String> listFuturesTradableCurrencies();
    Currency getCurrency(String c_name);
    int addCurrencyTrading(String c_name);
    int removeCurrencyTrading(String c_name);
    int addBondTrading(String c_name);
    int removeBondTrading(String c_name);
    int addFuturesTrading(String c_name);
    int removeFuturesTrading(String c_name);
    int deleteCurrency(String c_name);


}
