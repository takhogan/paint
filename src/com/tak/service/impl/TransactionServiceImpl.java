package com.tak.service.impl;

import java.time.ZonedDateTime;
import java.util.List;

import com.tak.pojo.*;
import com.tak.mapper.*;
import com.tak.service.TransactionService;
import com.tak.controller.PaintController;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private BondMapper bondMapper;
    @Autowired
    private FuturesMapper futuresMapper;
    @Autowired
    private CurrencyMapper currencyMapper;

    public int addTrade(Trade trade){return tradeMapper.addTrade(trade);};
    public Trade getTrade(int order_id){return tradeMapper.getTrade(order_id);};
    public List<Trade> listTrades(){return tradeMapper.listTrades();};
    public int deleteTrade(int order_id){return tradeMapper.deleteTrade(order_id);};

    public int addOrder(Order order){return orderMapper.addOrder(order);};
    public Order getOrder(int order_id){return orderMapper.getOrder(order_id);};
    public List<Order> listOrders(){return orderMapper.listOrders();};
    public List<Order> listOrdersByUsername(String owner_name){return orderMapper.listOrdersByUsername(owner_name);};
    public int deleteOrder(int order_id){return orderMapper.deleteOrder(order_id);};
    public List<Order> listBuyOrders(String c_name){return orderMapper.listBuyOrders(c_name);};
    public List<Order> listUniqueBuyOrders(String c_name, String user_name){
        return orderMapper.listUniqueBuyOrders(c_name, user_name);}
    public List<Order> listSellOrders(String c_name){return orderMapper.listSellOrders(c_name);};
    public List<Order> listUniqueSellOrders(String c_name, String user_name){
        return orderMapper.listUniqueSellOrders(c_name, user_name);}
    public double sumBuyOrders(String c_name){
        Double sum = orderMapper.sumBuyOrders(c_name);
        if(sum == null){
            return 0;
        } else {
            return sum;
        }
    }
    public double sumSellOrders(String c_name){
        Double sum = orderMapper.sumSellOrders(c_name);
        if(sum == null){
            return 0;
        } else {
            return sum;
        }
    }
    public double sumOrdersByUsername(String owner_name){
        Double sum = orderMapper.sumOrdersByUsername(owner_name);
        if(sum == null){
            return 0;
        } else {
            return sum;
        }
    }
    public int addWallet(Wallet w){return walletMapper.addWallet(w);}
    //also the initializer
    public List<Wallet> listWallets(String user_name){
        List<Wallet> wlist = walletMapper.listWallets(user_name);
        if(wlist.isEmpty()){
            int usercount = userCount();
            List<String> clist = getCurrencies();
            if(usercount == 0) {
                System.out.println("creating first user: " + user_name);
                //first time creating currency so supply is 0;
                for (String s : clist) {
                    Wallet w = createCurrency(user_name, s);
                    wlist.add(w);
                }
            } else {
                System.out.println("Generating Wallets for: " + user_name);
                for(String s : clist){
                    double csupply = sumSellOrders(s) + sumAccountBalances(s);
                    double usersupply = (2*csupply/usercount)*Math.random();
                    usersupply = validateBalance(usersupply);
                    Wallet w = new Wallet(user_name, s, usersupply);
                    wlist.add(w);
                    addWallet(w);
                }
            }
        }
        return wlist;
    }
    public int updateWallet(int wallet_id, double balance){return walletMapper.updateWallet(wallet_id, balance);}
    public List<String> getCurrencies(){
        List<String> clist = walletMapper.getCurrencies();
        if(clist.isEmpty()){
            clist.add("Bitcoin");
            clist.add("Litecoin");
            clist.add("Ether");
        }
        return clist;
    }
    public int swapOwnership(int wallet_id, String user_name){
        return walletMapper.swapOwnership(wallet_id, user_name);
    }
    public List<String> getNCurrencies(int n){
        //this breaks when getNCurrencies.size()>n
        List<String> rlist = walletMapper.getNCurrencies(n);
        int dcurr = n-rlist.size();
        while(dcurr > 0){
            //I think this will return a list with exactly n but not sure
            for(int i = 0; i < dcurr; i++){
                rlist.add(rlist.get(i));
            }
            dcurr = n-rlist.size();
        }
        return rlist;

    }
    //returns null
    public List<Double> getBalances(String user_name, String c_name){
        List<Double> blist = walletMapper.getBalances(user_name, c_name);
        if(blist.isEmpty()){
            //wallet will not be inserted here!!
            blist.add(0.0);
        }
        return blist;
    }
    public double sumUserBalances(String user_name, String c_name) {
        Double sum = walletMapper.sumUserBalances(user_name, c_name);
        if (sum == null) {
            return 0;
        } else {
            return sum;
        }
    }
    public Wallet getBigWallet(String user_name, String c_name){
        //IF THIS RETURNS THE INPUT WALLET IT MIGHT NOT WORK!!!!
        Wallet rwall = walletMapper.getBigWallet(user_name, c_name);
        System.out.println("rwall = " + rwall);
        if (rwall == null) {
            Wallet w = new Wallet(user_name, c_name, 0);
            walletMapper.addWallet(w);
            return w;
        }
        return rwall;
    }
    public Wallet getSmallWallet(String user_name, String c_name){
        Wallet rwall = walletMapper.getSmallWallet(user_name, c_name);
        if (rwall == null) {
            Wallet w = new Wallet(user_name, c_name, 0);
            walletMapper.addWallet(w);
            return w;
        }
        return rwall;
    }
    public Wallet getWalletByID(int wallet_id){return walletMapper.getWalletByID(wallet_id);}
    public int deleteWallet(int wallet_id){return walletMapper.deleteWallet(wallet_id);}

    public boolean currencyExists(String c_name){return !(walletMapper.getOneWallet(c_name) == null);}


    public List<News> listNews(){return newsMapper.listNews();}
    public int addNews(News news){return newsMapper.addNews(news);}

    public double sumAccountBalances(String c_name){
        Double basesupply = walletMapper.sumAccountBalances(c_name);
        double bs;
        if(basesupply == null){
            bs = 0;
        } else {
            bs = basesupply;
        }
        return bs;
    }

    public Wallet createCurrency(String user_name, String c_name){
        Wallet w = new Wallet(user_name, c_name, Math.random()*1000000);
        addCurrency(new Currency(c_name, 1, 0, 0));
        addWallet(w);
        return w;
    }

    public int userCount(){
        Integer count = walletMapper.userCount();
        if(count == null){
            return 0;
        }
        return count;
    }

    //INCOMPLETE
    public double validateBalance(double input){
        if(input < 1000000){
            return input;
        } else {
            return 999999.99;
        }
    }
    public double sumSupply(String c_name){return sumAccountBalances(c_name)+sumSellOrders(c_name);}
    public List<Player> listPlayers(){return playerMapper.listPlayers();}
    public int addPlayer(Player p){return playerMapper.addPlayer(p);}
    public int removePlayer(int player_id){return playerMapper.removePlayer(player_id);}
    public int addBond(Bond b){return bondMapper.addBond(b);}
    public Bond getBond(int loan_id){return bondMapper.getBond(loan_id);}
    public List<Bond> listBonds(){return bondMapper.listBonds();}
    public List<Bond> listActiveBonds(){return bondMapper.listActiveBonds();}
    public List<Bond> listInActiveBonds(){return bondMapper.listInActiveBonds();}
    public List<Bond> listLendRequests(String c_name){return bondMapper.listLendRequests(c_name);};
    public List<Bond> listBorrowRequests(String c_name){return bondMapper.listBorrowRequests(c_name);};
    public int updateBond(Bond b){return bondMapper.updateBond(b);}
    public int makeActive(int loan_id){
        //also updates lastpayment
        return bondMapper.makeActive(loan_id, ZonedDateTime.now());
    }
    public int makePending(int loan_id){return bondMapper.makePending(loan_id);}
    public int addBorrower(int loan_id, String borrower_name){return bondMapper.addBorrower(loan_id, borrower_name);}
    public int addLender(int loan_id, String owner_name){return bondMapper.addLender(loan_id, owner_name);}
    public int deleteBond(int loan_id){return bondMapper.deleteBond(loan_id);}

    public int addContract(FuturesContract fc){return futuresMapper.addContract(fc);}
    public int getContract(int contract_id){return futuresMapper.getContract(contract_id);}
    public List<FuturesContract> listContracts(){return futuresMapper.listContracts();}
    public List<FuturesContract> listActiveContracts(){return futuresMapper.listActiveContracts();}
    public List<FuturesContract> listShortFutures(String c_name){return futuresMapper.listShortFutures(c_name);};
    public List<FuturesContract> listLongFutures(String c_name){return futuresMapper.listLongFutures(c_name);};
    public int deleteContract(int contract_id){return futuresMapper.deleteContract(contract_id);}

    public double getLatestRate(String wantname, String givename){
        //returns the latest wantname/givename rate
        Trade t = tradeMapper.getLatestRate(wantname, givename);
        if(t==null){
            return 1;
        } else{
            if(t.getWantname().equals(wantname)){
                return t.getWantamount()/t.getGiveamount();
            } else {
                return t.getGiveamount()/t.getWantamount();
            }
        }
    }
    public int addCurrency(Currency c){return currencyMapper.addCurrency(c);};
    public List<Currency> listCurrencies(){return currencyMapper.listCurrencies();};
    public List<String> listTradableCurrencies(){return currencyMapper.listTradableCurrencies();};
    public List<String> listBondTradableCurrencies(){return currencyMapper.listBondTradableCurrencies();};
    public List<String> listFuturesTradableCurrencies(){return currencyMapper.listFuturesTradableCurrencies();};
    public Currency getCurrency(String c_name){return currencyMapper.getCurrency(c_name);};
    public int addCurrencyTrading(String c_name){return currencyMapper.addCurrencyTrading(c_name);};
    public int removeCurrencyTrading(String c_name){return currencyMapper.removeCurrencyTrading(c_name);};
    public int addBondTrading(String c_name){return currencyMapper.addBondTrading(c_name);};
    public int removeBondTrading(String c_name){return currencyMapper.removeBondTrading(c_name);};
    public int addFuturesTrading(String c_name){return currencyMapper.addFuturesTrading(c_name);};
    public int removeFuturesTrading(String c_name){return currencyMapper.removeFuturesTrading(c_name);};
    public int deleteCurrency(String c_name){return currencyMapper.deleteCurrency(c_name);};

    public int resetAll(){
        bondMapper.deleteAll();
        currencyMapper.deleteAll();
        futuresMapper.deleteAll();
        newsMapper.deleteAll();
        orderMapper.deleteAll();
        playerMapper.deleteAll();
        tradeMapper.deleteAll();
        walletMapper.deleteAll();
        return 1;
    }

}
