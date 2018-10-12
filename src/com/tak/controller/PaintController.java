package com.tak.controller;

import com.tak.service.TransactionService;
import com.tak.pojo.*;
import com.tak.webobjects.*;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class PaintController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping("/create")
    public ModelAndView CreateServlet(@CookieValue("username") String user){
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        mav.addObject("clist", transactionService.getCurrencies());
        mav.setViewName("create");
        return mav;
    }

    @RequestMapping("/status")
    public ModelAndView StatusServlet(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("status");
        return mav;
    }

    @RequestMapping("/settings")
    public ModelAndView SettingsServlet(@CookieValue("username") String user){
        ModelAndView mav = new ModelAndView();
        List<Player> playerlist = transactionService.listPlayers();
        mav.addObject("playerlist", playerlist);
        mav.setViewName("settings");
        mav.addObject("user", user);
        return mav;
    }
    @RequestMapping("/currencymarket")
    public ModelAndView CurrencyMarketServlet(HttpServletRequest req,
                                              @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView("forward:/market");
        mav.addObject("user",user);
        ArrayList<String> clist = new ArrayList<>(transactionService.listTradableCurrencies());
        LinkedList<MarketContainer> mlist = new LinkedList<>();
        for(String c : clist){

            ArrayList<FinanceObject> selllist =
                    Order.toFinanceObjectArr(new ArrayList<>(transactionService.listSellOrders(c)));
            ArrayList<FinanceObject> buylist =
                    Order.toFinanceObjectArr(new ArrayList<>(transactionService.listBuyOrders(c)));

            MarketContainer m = new MarketContainer(c, buylist, selllist);
            mlist.add(m);
        }
        req.setAttribute("typeName","currency");
        req.setAttribute("mlist",mlist);
        req.setAttribute("otherlist", transactionService.listTrades());
        String[] varNames = Order.getVarNames();
        req.setAttribute("buyVarNames", varNames);
        req.setAttribute("sellVarNames",varNames);
        req.setAttribute("nBuyVars",varNames.length);
        req.setAttribute("nSellVars",varNames.length);
        return mav;
    }

    @RequestMapping(value = "/market")
    public ModelAndView MarketServlet(HttpServletRequest req,
                                      @CookieValue("username") String user){  // JDK 1.6 and above only
        ModelAndView mav = new ModelAndView();

        mav.addObject("user",user);
        mav.addObject("typeName", req.getAttribute("typeName"));
        mav.addObject("mlist", req.getAttribute("mlist"));
        mav.addObject("otherlist", req.getAttribute("otherlist"));
        mav.addObject("buyVarNames", req.getAttribute("buyVarNames"));
        mav.addObject("nBuyVars", req.getAttribute("nBuyVars"));
        mav.addObject("sellVarNames",req.getAttribute("sellVarNames"));
        mav.addObject("nSellVars", req.getAttribute("nSellVars"));

        mav.setViewName("market");
        return mav;
    }
    @RequestMapping(value = "/bondmarket")
    public ModelAndView BondMarketServlet(HttpServletRequest req,
                                          @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView("forward:/market");
        mav.addObject("user",user);
        ArrayList<String> clist = new ArrayList<>(transactionService.listBondTradableCurrencies());
        LinkedList<MarketContainer> mlist = new LinkedList<>();
        //buy/sell is from the perspective of the bond buyer/seller (so borrow offers are bond sales
        for(String c : clist){
            //might've gotten the buy & sell mixed up idk
//            List<Integer> even = numbers.stream()
//                    .map(s -> Integer.valueOf(s))
//                    .filter(number -> number % 2 == 0)
//                    .collect(Collectors.toList());

            ArrayList<FinanceObject> buylist = transactionService.listBorrowRequests(c).stream()
                    .map(s -> s.toBorrowFinanceObject(""))
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<FinanceObject> selllist = transactionService.listLendRequests(c).stream()
                    .map(s -> s.toLendFinanceObject(""))
                    .collect(Collectors.toCollection(ArrayList::new));
            MarketContainer m = new MarketContainer(c, buylist, selllist);
            mlist.add(m);
        }
        req.setAttribute("typeName","bond");
        req.setAttribute("mlist", mlist);
        req.setAttribute("otherlist", transactionService.listActiveBonds());
        String[] buyVarNames = Bond.getBorrowVarNames();
        req.setAttribute("buyVarNames",buyVarNames);
        req.setAttribute("nBuyVars",buyVarNames.length);
        String[] sellVarNames = Bond.getLendVarNames();
        req.setAttribute("sellVarNames",sellVarNames);
        req.setAttribute("nSellVars",sellVarNames.length);

        return mav;
    }
    //INCOMPLETE
    @RequestMapping(value = "/futuresmarket")
    public ModelAndView FuturesMarketServlet(HttpServletRequest req,
                                             @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView("forward:/market");
        mav.addObject("user", user);
        ArrayList<String> clist = new ArrayList<>(transactionService.listFuturesTradableCurrencies());
        LinkedList<MarketContainer> mlist = new LinkedList<>();
        for(String c : clist){
            ArrayList<FinanceObject> selllist = transactionService.listShortFutures(c).stream()
                    .map(s -> s.toShortFinanceObject(""))
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<FinanceObject> buylist = transactionService.listLongFutures(c).stream()
                    .map(s -> s.toLongFinanceObject(""))
                    .collect(Collectors.toCollection(ArrayList::new));
            MarketContainer m = new MarketContainer(c,buylist,selllist);
            mlist.add(m);
        }
        req.setAttribute("typeName","futures");
        req.setAttribute("mlist", mlist);
        req.setAttribute("otherlist", transactionService.listActiveContracts());
        String[] buyVarNames = FuturesContract.getLongVarNames();
        req.setAttribute("buyVarNames",buyVarNames);
        req.setAttribute("nBuyVars",buyVarNames.length);
        String[] sellVarNames = FuturesContract.getShortVarNames();
        req.setAttribute("sellVarNames",sellVarNames);
        req.setAttribute("nSellVars",sellVarNames.length);
        return mav;
    }

    @RequestMapping(value = "/portfolio")
    public ModelAndView PortfolioServlet(@RequestParam(value = "username", required = false) String user,
                                         @CookieValue(value = "username", required = false) String cook) {
        //cookie is unused (cookie becomes invalid after restarting computer for some reason)
        ModelAndView mav = new ModelAndView();
        if(user == null){
            user = cook;
        }
        List<Wallet> userwallets = transactionService.listWallets(user);
        mav.addObject("wallets", userwallets); //needs to also be able to
        List<Order> olist = transactionService.listOrdersByUsername(user);
        mav.addObject("orders", olist);
        mav.addObject("user",user);
        List<News> nlist = transactionService.listNews();
        mav.addObject("news",nlist);
        mav.setViewName("portfolio");
        return mav;
    }
    @RequestMapping(value = "/trade")
    public ModelAndView TradeServlet(HttpServletRequest req,
                                     @CookieValue("username") String user){  // JDK 1.6 and above only
        ModelAndView mav = new ModelAndView();
        mav.addObject("user",user);

        mav.addObject("inputFields", req.getAttribute("inputFields"));
        mav.addObject("varNames", req.getAttribute("varNames"));
        mav.addObject("wlist",transactionService.listWallets(user));
        mav.setViewName("trade");
        return mav;
    }

    @Transactional
    @RequestMapping(value = "/execute")
    public ModelAndView ExecutionServlet(HttpServletRequest req,
                                         @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        String[] varNames = (String[])req.getAttribute("varNames");
        mav.addObject("nNames", varNames.length);
        mav.addObject("varNames", varNames);
        mav.addObject("flist", req.getAttribute("flist"));
        mav.setViewName("execute");
        return mav;

    }
    //INCOMPLETE
    @RequestMapping(value = "/futuresexec")
    public ModelAndView FuturesExecutionServlet(HttpServletRequest req,
                                                @CookieValue("username") String user,
                                                @RequestParam("contract_id") Integer[] cid){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user"); //INCOMPLETE we have a bunch of user object name mismatches it's gonna be a problem
        //here is where we will take into account create type
        return mav;
    }

    //UNTESTED
    @RequestMapping("/bondexec")
    public ModelAndView BondExecutionServlet(HttpServletRequest req,
                                             @CookieValue("username") String user,
                                             @RequestParam("bond_id") Integer[] bid){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user");
        LinkedList<FinanceObject> flist = new LinkedList<FinanceObject>();
        for(Integer b : bid) {
            Bond bond = transactionService.getBond(b);
            //check margin requirement
            String c_name = bond.getLoan_currency();

            //if borrower (someone is offering to lend and you are accepting)
            if(bond.getBorrower_name() == null){
                //wallets
                Wallet receiver = transactionService.getBigWallet(user, c_name);
                Wallet giver = transactionService.getBigWallet(bond.getOwner_name(), c_name);

                System.out.println("user is borrower");
                System.out.println("bond.getInitial_margin() = " + bond.getInitial_margin());
                System.out.println("receiver.getBalance() = " + receiver.getBalance());
                //if valid:
                if (receiver.getBalance() >= bond.getInitial_margin()) {
                    int active = bond.getActive();
                    //if inactive
                    if (active == 0) {
                        //make pending
                        transactionService.makePending(b);
                        flist.add(bond.toFinanceObject("request pending"));
                    }
                    //if pending
                    else if (active == 2) {
                        //transfer loan amount from lender to borrower
                        String status = balanceTransfer(receiver, c_name, bond.getLoan_amount(), giver);
                        if (status == null) {
                            flist.add(bond.toFinanceObject("bond activated"));
                            //make loan active
                            transactionService.addBorrower(b, user);
                            transactionService.makeActive(b);
                        } else {
                            flist.add(bond.toFinanceObject("insufficient lender funds"));
                        }
                    }
                }
                //else return invalid
                else {
                    flist.add(bond.toFinanceObject("insufficient borrower funds"));
                }

            }
            //if lender (someone is offering to borrow)
            else if(bond.getOwner_name() == null){
                //wallets
                Wallet receiver = transactionService.getBigWallet(bond.getBorrower_name(), c_name);
                Wallet giver = transactionService.getBigWallet(user, c_name);

                //if valid:
                System.out.println("user is lender");
                System.out.println("bond.getInitial_margin() = " + bond.getInitial_margin());
                System.out.println("receiver.getBalance() = " + receiver.getBalance());
                if (receiver.getBalance() >= bond.getInitial_margin()) {
                    //transfer loan amount from lender to borrower
                    String status = balanceTransfer(receiver, c_name, bond.getLoan_amount(), giver);
                    if (status == null) {
                        flist.add(bond.toFinanceObject("bond activated"));
                        //make loan active
                        transactionService.addLender(b, user);
                        transactionService.makeActive(b);
                    } else {
                        flist.add(bond.toFinanceObject("insufficient lender funds"));
                    }
                }
                //else return invalid
                else {
                    flist.add(bond.toFinanceObject("insufficient borrower funds"));
                }
            } else{ //might be a reactivation (both not null)
                //INCOMPLETE
                flist.add(bond.toFinanceObject("bond already active"));
            }


            req.setAttribute("varNames", Bond.getVarNames());
            req.setAttribute("flist", flist);
        }

        return mav;
    }
    @RequestMapping("/walletcombo")
    public ModelAndView WalletCombinationServlet(HttpServletRequest req,
                                                 @CookieValue("username") String user,
                                                 @RequestParam("wallet_id") Integer[] wid){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user");
        LinkedList<FinanceObject> flist = new LinkedList<FinanceObject>();
        //if there are multiple error messages, they may get overwritten
        if (wid != null && wid[0] != null) {
            for (int i : wid) {
                System.out.println("i = " + i);
            }
            Wallet w = transactionService.getWalletByID(wid[0]);
            String combinename = w.getC_name();
            LinkedList<PendingOrder> pendinglist = new LinkedList<>();
            flist.addAll(walletCombo(wid, 0, combinename, user));
        } else {
            flist.add(new PendingOrder(new Order(), "no wallets selected").toFinanceObject());
        }
        req.setAttribute("varNames", Order.getVarNames()); //should change this
        req.setAttribute("flist",flist);
        return mav;
    }

    @RequestMapping("/cryptogen")
    public ModelAndView CurrencyGenerationServlet(HttpServletRequest req,
                                                  @CookieValue("username") String user,
                                                  @RequestParam(value = "c_name") String[] c_name,
                                                  @RequestParam(value = "c_supply") Double[] c_supply){

        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user");
        LinkedList<FinanceObject> flist = new LinkedList<FinanceObject>();
        boolean isvalid = true;
        String wantname;
        if (c_name != null && c_name[0] != null) {
            wantname = c_name[0];
        } else {
            wantname = "";
            isvalid = false;
        }
        double wantamount;
        if (c_supply != null && c_supply[0] != null) {
            wantamount = c_supply[0];
            if (wantamount <= 0) {
                isvalid = false;
            }
        } else {
            wantamount = 0;
            isvalid = false;
        }

        if (isvalid) {
            flist.addAll(walletGen(user, wantname, wantamount));
            transactionService.addNews(new News(user + " launches new currency: " + wantname, ZonedDateTime.now()));
        } else {
            Order createorder = new Order(user, wantname, wantamount, "n/a", 0);
            PendingOrder p = new PendingOrder(createorder, "invalid inputs");
            flist.add(p.toFinanceObject());
        }
        req.setAttribute("varNames", Wallet.getVarNames()); //should change this
        req.setAttribute("flist",flist);
        return mav;
    }
    @RequestMapping("/ordercancel")
    public ModelAndView OrderCancellationServlet(HttpServletRequest req,
                                                 @CookieValue("username") String user,
                                                 @RequestParam(value = "tid") Integer[] tid){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user");
        LinkedList<FinanceObject> flist = new LinkedList<FinanceObject>();
        PendingOrder p;
        if (tid == null) {
            p = new PendingOrder(new Order(), "no order selected");
            flist.add(p.toFinanceObject());
        } else {
            for (int i = 0; i < tid.length; i++) {

                int transaction_id = tid[i];
                Order o = transactionService.getOrder(transaction_id);

                String transaction_owner = o.getOwner_name();
                String givename = o.getGivename();
                double giveamount = o.getGiveamount();

                Wallet togivewallet = transactionService.getSmallWallet(transaction_owner, givename);

                String status = balanceTransfer(togivewallet, givename, giveamount, null);
                if (status != null) {
                    p = new PendingOrder(o, status);
                } else {
                    transactionService.deleteOrder(transaction_id);
                    p = new PendingOrder(o, "order cancelled");
                }
                flist.add(p.toFinanceObject());
            }
            mav.addObject("varNames", Order.getVarNames());
        }
        req.setAttribute("e_type", 1);
        req.setAttribute("flist", flist);
        req.setAttribute("varNames", Order.getVarNames());
        return mav;
    }

    @RequestMapping("/orderexec")
    public ModelAndView OrderExecutionServlet(HttpServletRequest req,
                                              @CookieValue("username") String user,
                                              @RequestParam(value = "tid") Integer[] tid){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user");
        LinkedList<FinanceObject> flist = new LinkedList<>();
        PendingOrder p;
        if (tid == null) {
            p = new PendingOrder(new Order(), "no order selected");
            flist.add(p.toFinanceObject());
        } else {
            for (int i = 0; i < tid.length; i++) {
                Order o = transactionService.getOrder(tid[i]);
                p = OrderExecution(o, user);
                flist.add(p.toFinanceObject());
            }
        }
        req.setAttribute("varNames", Order.getVarNames());
        req.setAttribute("flist",flist);
        req.setAttribute("e_type", 2);
        return mav;
    }
    @RequestMapping("/futurestrade") //INCOMPLETE (remember to redirect execution page & do it for all the other trade pages)
    public ModelAndView FuturesTradeServlet(HttpServletRequest req,
                                         @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView("forward:/trade");
        mav.addObject(user);

        ArrayList<String> clist = new ArrayList<>(transactionService.getCurrencies());
        req.setAttribute("inputFields", FuturesContract.getInputFields(clist));
        req.setAttribute("varNames", FuturesContract.getInputVarNames());
        req.setAttribute("formName", FuturesContract.getFormName());
        return mav;
    }


    @RequestMapping("/bondtrade") //INCOMPLETE (remember to redirect execution page & do it for all the other trade pages)
    public ModelAndView BondTradeServlet(HttpServletRequest req,
                                             @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView("forward:/trade");
        mav.addObject(user);

        ArrayList<String> clist = new ArrayList<>(transactionService.getCurrencies());
        req.setAttribute("inputFields", Bond.getInputFields(clist));
        req.setAttribute("varNames", Bond.getInputVarNames());
        req.setAttribute("formName", Bond.getFormName());
        return mav;
    }

    @RequestMapping("/currencytrade")
    public ModelAndView CurrencyTradeServlet(HttpServletRequest req,
                                             @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView("forward:/trade");
        mav.addObject(user);

        ArrayList<String> clist = new ArrayList<String>(transactionService.getCurrencies());
        req.setAttribute("inputFields", Order.getInputFields(clist));
        req.setAttribute("varNames", Order.getInputVarNames());
        req.setAttribute("formName", Order.getFormName());
        return mav;
    }

    @RequestMapping("/orderedit")
    public ModelAndView OrderEditServlet(HttpServletRequest req,
                                         @CookieValue("username") String user,
                                         @RequestParam(value = "tidvalue") Integer[] tidvalue){

        ModelAndView mav = new ModelAndView("forward:/trade");
        mav.addObject(user);

        int tid = tidvalue[0];
        Order o = transactionService.getOrder(tid);
        transactionService.deleteOrder(tid);
        ArrayList<String> clist = new ArrayList<String>(transactionService.getCurrencies());
        req.setAttribute("inputFields", Order.getInputFields(clist,
                o.getWantname(), o.getWantamount(),
                o.getGivename(), o.getGiveamount()));
        req.setAttribute("varNames", Order.getInputVarNames());
        return mav;
    }

    @RequestMapping("/currencygen")
    public ModelAndView OrderGenerationServlet(HttpServletRequest req,
                                               @CookieValue("username") String user,
                                               @RequestParam(value = "wantamount") Double[] wantamount,
                                               @RequestParam(value = "giveamount") Double[] giveamount,
                                               @RequestParam(value = "want") String[] want,
                                               @RequestParam(value = "give") String[] give){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject(user);
        LinkedList<FinanceObject> flist = new LinkedList<>();
        for (int j = 0; j < want.length; j++) {
            PendingOrder p;
            boolean isvalid = true;
            double wam;
            double gam;
            //invalid wantamount
            if (wantamount[j] == null) {
                isvalid = false;
                wam = 0;
            } else {
                wam = wantamount[j];
            }
            //invalid sellamount
            if (wantamount[j] == null) {
                gam = 0;
                isvalid = false;
            } else {
                gam = giveamount[j];
            }
            String wname = want[j];
            String gname = give[j];
            //trading coin for same coin

            Order neworder = new Order(user, wname, wam, gname, gam);
            p = insertProcessing(neworder, isvalid);
            flist.add(p.toFinanceObject());
        }
        req.setAttribute("flist", flist);
        req.setAttribute("varNames", Order.getVarNames());
        return mav;
    }
    @RequestMapping("/futuresgen") //INCOMPLETE
    public ModelAndView FuturesGenerationServlet(HttpServletRequest req,
                                                 @CookieValue("username") String user,
                                                 @RequestParam("cr_type") Integer[] cr_type,
                                                 @RequestParam("wantname") String[] wantname,
                                                 @RequestParam("wantamount") Double[] wantamount,
                                                 @RequestParam("givename") String[] givename,
                                                 @RequestParam("giveamount") Double[] giveamount,
                                                 @RequestParam("expiration_value") Integer[] expiration_value){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user", user);
        LinkedList<FinanceObject> flist = new LinkedList<>();

        for(int i = 0; i < cr_type.length; i++) {
            int create_type = cr_type[i];

            //INCOMPLETE might switch between wantamount and giveamount depending on long/short idk
            String wname = wantname[i];
            double wam = wantamount[i];
            String gname = givename[i];
            double gam = giveamount[i];

            System.out.println("wname = " + wname);
            System.out.println("gname = " + gname);


            //check validity
            Wallet originator;
            String status;
            originator = transactionService.getBigWallet(user, gname);
            double latestrate = transactionService.getLatestRate(gname, wname);
            System.out.println("The latest rate for was "+latestrate+" "+gname+"/"+wname);
            status = FuturesContract.verifyMargin(originator.getBalance(), wantamount[i], latestrate);


            //INCOMPLETE will prob change the limit on max value later

            if(wam < 0 || wam > 999999.99){
                status = "invalid wantamount";
            }

            if(gam < 0 || gam > 999999.99){
                status = "invalid wantamount";
            }
            ZonedDateTime now = ZonedDateTime.now();
            long expiration_seconds = expiration_value[i]*3600;
            if(expiration_seconds <= 0){
                status = "invalid expiration value";
            }
            ZonedDateTime expiry = now.plusSeconds(expiration_seconds);
            expiry = expiry.truncatedTo(ChronoUnit.HOURS);
            //add to database
            FuturesContract fc = new FuturesContract(create_type, user, wname, wam, gname, gam, expiry);
            if(status == null) {
                transactionService.addContract(fc);
                status = "succesful contract creation";
            }
            flist.add(fc.toFinanceObject(status));
        }
        req.setAttribute("flist", flist);
        req.setAttribute("varNames", FuturesContract.getVarNames());
        return mav;

    }

    @RequestMapping("/bondgen") //note that the datebase name is loans
    public ModelAndView BondGenerationServlet(HttpServletRequest req,
                                              @CookieValue("username") String user,
                                              @RequestParam("create_type") Integer[] cr_type,
                                              @RequestParam("c_name") String[] c_name,
                                              @RequestParam("c_amount") Double[] c_amount,
                                              @RequestParam("initial_margin") Double[] initial_margin,
                                              @RequestParam("freq_mult") Integer[] freq_mult,
                                              @RequestParam("i_amount") Double[] i_amount,
                                              @RequestParam("frequency") Integer[] frequency,
                                              @RequestParam("loan_type") Integer[] loan_type,
                                              @RequestParam("expiration_value") Integer[] exp_val,
                                              @RequestParam("expiration_units") Integer[] exp_units){
        ModelAndView mav = new ModelAndView("forward:/execute");
        mav.addObject("user", user);
        LinkedList<FinanceObject> flist = new LinkedList<>();
        for(int i = 0; i < cr_type.length; i++) {
            //check that bond object is valid
            String status = null;
            int create_type = cr_type[i];
            double currency_amount = c_amount[i];
            String currency_name = c_name[i];
            int freq = 0;
            if (frequency[i] > 0) {
                if (freq_mult != null) {
                    freq = frequency[i] * freq_mult[i];
                } else {
                    freq = frequency[i];
                }
            } else {
                status = "invalid frequency value";
            }
            if (!(c_amount[i] > 0 && c_amount[i] < 1000000)) {
                status = "invalid c_amount";
            }
            int expiry = exp_val[i] * exp_units[i] * 60; //stored as seconds
            if (!(expiry > 0)) {
                status = "invalid expiration period";
            }
            //create bond object
            Bond b = new Bond(user, create_type, currency_name, currency_amount, initial_margin[i], freq,
                    loan_type[i], currency_name, i_amount[i], expiry);
            if (status == null) {
                double sum = transactionService.sumUserBalances(user, c_name[i]);
                //if lender
                if (create_type == 1) {
                    //check that lender has loan amount available
                    if (!(sum >= currency_amount)) {
                        status = "insufficient funds: need at least " + currency_amount + " " + currency_name;
                    }
                }
                //if borrower (nothing)
            }

            //if tests pass, (error messages will overwrite eachother)
            if (status == null) {
                //add loan object to database
                status = "succesful loan creation";
                transactionService.addBond(b);
            }
            System.out.println("b = " + b);
            flist.add(b.toFinanceObject(status));
        }
        req.setAttribute("flist", flist);
        req.setAttribute("varNames", Bond.getVarNames());
        return mav;
    }
    @RequestMapping("/generate") //mode should be between 1 & 100 (add constraint later)
    public ModelAndView GenerationServlet(@RequestParam(value = "player_name", required = false) String pname,
                                    @RequestParam(value = "mode", required = false) Integer mode,
                                    @RequestParam(value = "player_id", required = false) Integer pid,
                                    @RequestParam("g_type") Integer g_type,
                                    @CookieValue("username") String user){
        ModelAndView mav = new ModelAndView("redirect:portfolio");
        if(g_type == 1) { //add player
            transactionService.addPlayer(new Player(pname, mode));
            transactionService.listWallets(pname);
        } else if (g_type == 2) { //remove player
            transactionService.removePlayer(pid);
        } else if (g_type == 3) { //operate players
            operate_players();
        } else if (g_type == 4) { //create loan (actually it has been redirected)

        }
        System.out.println("user = " + user);
        mav.addObject(user);
        return mav;
    }
    @RequestMapping("/operate")
    public void OperationServlet(@CookieValue("username") String user){
        operate_players();
        manage_bonds();
        manage_futures();
    }
    @RequestMapping("/manage")
    public void ManagementServlet(){
        manage_bonds();
        manage_futures();
    }
    //UNTESTED
    public void manage_futures(){
        ArrayList<FuturesContract> contrlist = new ArrayList<>(transactionService.listActiveContracts());
        for(FuturesContract fc : contrlist){
            ZonedDateTime expiry = fc.getExpiration();
            ZonedDateTime now = ZonedDateTime.now();

            int contract_type = fc.getContract_type();


            String buyer;
            String buycurrency;
            Wallet buyerwallet;
            double buyamount;
            String seller;
            String sellcurrency;
            Wallet sellerwallet;
            double sellamount;
            if(contract_type == 1) {
                buyer = fc.getCreator();
                buycurrency = fc.getWantname();
                buyerwallet = transactionService.getBigWallet(buyer, buycurrency);
                buyamount = fc.getWantamount();

                seller = fc.getConsumer();
                sellcurrency = fc.getGivename();
                sellerwallet = transactionService.getBigWallet(seller, sellcurrency);
                sellamount = fc.getGiveamount();
            } else if(contract_type == 2) {
                buyer = fc.getConsumer();
                buycurrency = fc.getWantname();
                buyerwallet = transactionService.getBigWallet(buyer, buycurrency);
                buyamount = fc.getWantamount();

                seller = fc.getCreator();
                sellcurrency = fc.getGivename();
                sellerwallet = transactionService.getBigWallet(seller, sellcurrency);
                sellamount = fc.getGiveamount();
            } else {
                System.out.println("fc = " + fc);
                System.out.println("unimplemented contract type!");
                return;
            }

            double wantgiverate = transactionService.getLatestRate(buycurrency, sellcurrency);

            if(ChronoUnit.SECONDS.between(now, expiry)<0){
                settle_contract(fc);
            } else {
                System.out.println("buycurrency = " + buycurrency);
                System.out.println("sellcurrency = " + sellcurrency);
                System.out.println("buyerwallet = " + buyerwallet);
                System.out.println("sellerwallet = " + sellerwallet);
                System.out.println("buyamount = " + buyamount);
                System.out.println("sellamount = " + sellamount);
                String sellerstatus = FuturesContract.verifyMargin(sellerwallet.getBalance(),sellamount, wantgiverate);
                String buyerstatus = FuturesContract.verifyMargin(buyerwallet.getBalance(), buyamount, (1/wantgiverate));
                System.out.println("sellerstatus = " + sellerstatus);
                System.out.println("buyerstatus = " + buyerstatus);
            }

        }
    }

    public void settle_contract(FuturesContract fc){
        System.out.println("Settling Contract:");
        System.out.println("fc = " + fc);
    }

    public void activate_bond(int loan_id){
        transactionService.makeActive(loan_id);
        System.out.println("loan_id = " + loan_id + " is now active.");
    }

    public void bankrupt(String lender, String borrower){ //bankruptcy = all assets seized
        //UNTESTED
        ArrayList<Wallet> wlist = new ArrayList<>(transactionService.listWallets(borrower));
        for(Wallet w : wlist){
            transactionService.swapOwnership(w.getWallet_id(), lender);
        }
        transactionService.addNews(new News(borrower + " goes bankrupt! " +
                lender + " seizes all of " + borrower +"'s assets!", ZonedDateTime.now()));
    }




    public void manage_bonds(){
        //UNTESTED (should do more testing but I think it is working)
        ArrayList<Bond> blist = new ArrayList<>(transactionService.listActiveBonds());
        for(Bond b : blist){
            System.out.println("b = " + b);

            //generate expiry vals
            long oldexpiry = b.getExpiration();
            ZonedDateTime lastPayment = b.getLast_payment();
            ZonedDateTime expiry = lastPayment.plusSeconds(oldexpiry);
            ZonedDateTime now = ZonedDateTime.now();
            long newexpiry = ChronoUnit.SECONDS.between(now, expiry);

            System.out.println("lastPayment = " + lastPayment);
            System.out.println("oldexpiry = " + oldexpiry);
            System.out.println("newexpiry = " + newexpiry);

            //generate payments due
            long freq = b.getInterest_frequency()*60;
            int paymentsdue = (int)((int)(oldexpiry-newexpiry)/freq);

            System.out.println("paymentsdue = " + paymentsdue);
            System.out.println("freq = " + freq);

            //generate string vals
            String c_name = b.getLoan_currency();
            String owner = b.getOwner_name();
            String borrower = b.getBorrower_name();

            //wallets
            Wallet payer = transactionService.getBigWallet(borrower, c_name);
            Wallet receiver = transactionService.getSmallWallet(owner, c_name);

            System.out.println("payer = " + payer);
            System.out.println("receiver = " + receiver);

            //pay interest
            int type = b.getLoan_type();
            double c_amount = b.getInterest_amount();
            if(type == 1) { //1 = bond
                //pay interest
                String status = null;
                for(int i = 0; i < paymentsdue; i++) {
                    status = balanceTransfer(receiver, c_name, c_amount, payer);
                    if(status != null){
                        bankrupt(owner, borrower);
                    }
                }
            }
            if(type == 2) {//2 = constant repayment (interest payments subtract from loan amount)
                double payamount = b.getLoan_amount();
                String status = null;
                for(int i = 0; i < paymentsdue; i++) {
                    status = balanceTransfer(receiver, c_name, c_amount, payer);
                    payamount -= c_amount;
                    if(status != null){
                        bankrupt(owner, borrower);
                    }
                }
                System.out.println("payamount = " + payamount);
                if(payamount < 0){ //because loan_amount is unsigned
                    payamount = 0;
                }
                b.setLoan_amount(payamount);
            } else { //custom (not implemented yet)
                //INCOMPLETE
                //create a PaymentPlan class
            }

            //if expired, close bond. else, update bond
            if(newexpiry<0){
                double payamount = b.getLoan_amount();
                String status = balanceTransfer(receiver, c_name, payamount, payer);
                if(status == null) {
                    transactionService.addNews(new News(borrower + " repaid " + c_name + " loan worth " +
                            payamount + " to " + owner, now));
                    transactionService.deleteBond(b.getLoan_id());
                } else {
                    bankrupt(owner, borrower);
                }
            } else {
                //update bond
                b.setLast_payment(now);
                b.setExpiration(newexpiry);
                transactionService.updateBond(b);
            }


        }
    }

    public LinkedList<FinanceObject> walletGen(String user_name, String c_name, double c_amount){
        double maxval = Wallet.MAXVAL();
        LinkedList<FinanceObject> flist = new LinkedList<>();
        while(c_amount > 0) {
            if (c_amount < 1000000) {
                Wallet w = new Wallet(user_name, c_name, c_amount);
                transactionService.addWallet(w);
                flist.add(w.toFinanceObject("succesful wallet creation"));
                break;
            } else {
                c_amount -= maxval;
                Wallet w = new Wallet(user_name, c_name, maxval);
                transactionService.addWallet(w);
                flist.add(w.toFinanceObject("succesful wallet creation"));
            }
        }
        return flist;
    }

    //INCOMPLETE exec pages need to lead to the right place, market pages need to lead to the right place, cancelation & edit need to lead ot the right place, trade pages etc (order & currency are confused)
    //inputs are always in order (because the wallet table is in order)
    public LinkedList<FinanceObject> walletCombo(Integer[] wid, int index, String combinename, String user){
        System.out.println("walletCombo called with index of = " + index);
        double combinebal = 0;
        int widlen = wid.length;
        LinkedList<FinanceObject> flist = new LinkedList<>();
        for(int i = index; i < widlen; ++i) {
            System.out.println("i = " + i);
            System.out.println("widlen = " + widlen);
            System.out.println("wid[i] = " + wid[i]);
            Wallet w = transactionService.getWalletByID(wid[i]);
            System.out.println("w = " + w);
            if(w.getBalance()==0){
                System.out.println("attempt delete");
                //if balance is 0, delete
                transactionService.deleteWallet(wid[i]);
                PendingOrder po = new PendingOrder(new Order(user, "n/a", 0,
                        combinename, 0), "succesful deletion");
                flist.add(po.toFinanceObject());
            } else {
                System.out.println("attempt combine");
                //if names of wallets match
                String cname = w.getC_name();
                if(combinename.equals(cname)){
                    System.out.println("names match");
                    //add balance to combined balance
                    double wbal = w.getBalance();
                    double oldbal = combinebal;
                    combinebal += wbal;
                    if(combinebal < 1000000){
                        //if new balance does not exceed limit, delete old wallet
                        transactionService.deleteWallet(wid[i]);
                        String status;
                        if(combinebal == wbal){
                            status = "successful creation";
                        } else {
                            status ="successful combination";
                        }
                        flist.add(new PendingOrder(new Order(user, combinename, oldbal,
                                combinename, wbal), status).toFinanceObject());
                        System.out.println("combination");
                    } else {
                        //if it does exceed limit, stop combination & skip to next
                        combinebal = combinebal - wbal;
                        PendingOrder po = new PendingOrder(new Order(user, combinename, combinebal,
                                combinename, wbal), "invalid order");
                        flist.add(po.toFinanceObject());
                        System.out.println("limit exceeded");
                    }
                } else {
                    System.out.println("recurse");
                    flist.addAll(walletCombo(wid, i, cname, user));
                    break;
                }

            }
        }
        if(combinebal>0) {
            System.out.println("combinebal = " + combinebal);
            System.out.println("adding wallet");
            transactionService.addWallet(new Wallet(user, combinename, combinebal));
        }
        return flist;

    }

    public String balanceTransfer(Wallet receiver, String c_name, double c_amount, Wallet giver){
        if(giver != null) {
            double userwval = giver.getBalance();
            if (userwval >= c_amount) {
                double newgbal = (userwval - c_amount);
                transactionService.updateWallet(giver.getWallet_id(), newgbal);
            } else {
                return "insufficient funds";
            }
        }
        if(receiver != null){
            double towval = receiver.getBalance();
            if((towval + c_amount) < 1000000) {
                double newrbal = (towval + c_amount);
                transactionService.updateWallet(receiver.getWallet_id(), newrbal);
            } else {
                walletGen(receiver.getUser_name(), c_name, c_amount);
            }
        }
        System.out.println(giver+" is giving "+c_amount+" of "+c_name+" to "+receiver);
        return null;

    }

    public PendingOrder OrderExecution(Order o, String username){
        double wantamount = o.getWantamount();
        double giveamount = o.getGiveamount();
        String wantname = o.getWantname();
        String givename = o.getGivename();
        String transaction_owner = o.getOwner_name();
        int transaction_id = o.getOrder_id();

        Wallet towantwallet = transactionService.getSmallWallet(transaction_owner, wantname);
        Wallet usrgivewallet = transactionService.getSmallWallet(username, givename);
        Wallet usrwantwallet = transactionService.getBigWallet(username, wantname);
        if(!username.equals(transaction_owner)) { //effectively a cancelled order
            String status1 = balanceTransfer(towantwallet, wantname, wantamount, usrwantwallet);
            if (status1 != null) {
                return new PendingOrder(o, status1);
            }
        }
        String status2 = balanceTransfer(usrgivewallet, givename, giveamount, null);
        if(status2 != null){
            return new PendingOrder(o, status2);
        }
        transactionService.addTrade(new Trade(o, username));
        transactionService.deleteOrder(transaction_id);
        System.out.println("Executed Order: " + o);
        return new PendingOrder(o, "order executed");
    }

    public void operate_players(){ //will only operate 1 player (otherwise too expensive)
        LinkedList<Player> cblist = new LinkedList<>(transactionService.listPlayers());
        operate(cblist.get((int)(Math.random()*cblist.size())));

    }

    public double[] factorList(int len){
        double base = 1.0/len;
        double[] ratio = new double[len];
        double[] factor = new double[len];

        for(int i = 0; i < len; i++){
            ratio[i] = base;
            double newfactor = Math.random();
            factor[i] = ratio[i]*newfactor;
            ratio[i] = (1-newfactor)*ratio[i]; //take a chunk from current value

        }
        ratio[0] += factor[len-1];
        double sum = ratio[0];
        for(int i = 1; i < len; i++){
            ratio[i] += factor[i-1];
            sum+=ratio[i];
        }
        if(sum!=1){
            ratio[0] +=(1-sum);
        }
        return ratio;
    }

    public PendingOrder insertProcessing(Order o, boolean isvalid){
        String user_name = o.getOwner_name();
        String givename = o.getGivename();
        String wantname = o.getWantname();
        double giveamount = o.getGiveamount();
        if((int)(giveamount*100) <= 0 || !(giveamount < 1000000)){
            isvalid = false;
        }
        double wantamount = o.getWantamount();
        if((int)(wantamount*100) <= 0 || !(wantamount < 1000000)){
            isvalid = false;
        }
        Wallet givewallet = transactionService.getBigWallet(user_name, givename);
        double usergval = givewallet.getBalance();
        if(wantname.equals(givename)){
            isvalid = false;
        }
        System.out.println("Creating Order: = " + o);
        if(!isvalid){
            return new PendingOrder(o, "invalid order");
        }
        if (usergval >= giveamount) {
            double newgbal = (usergval - giveamount);
            transactionService.updateWallet(givewallet.getWallet_id(), newgbal);
            transactionService.addOrder(o);
            return new PendingOrder(o, "order placed");

        } else {
            return new PendingOrder(o, "insufficient funds");

        }
    }



    public String generateName(){
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        String[] consonants = {"b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","y","z"};
        String[] blends = {"bl", "br", "ch", "cl", "cr", "dr", "fl", "fr", "gl", "gr", "pl", "pr", "sc", "sh", "sk",
                "sl", "sm", "sn", "sp", "st", "sw", "th", "tr", "tw", "wh", "wr"}; //25
        NormalDistribution norman = new NormalDistribution();
        int prelen = (int)(norman.sample()*3); //(1stdev 3, 2stdev 6, 3stdev 9)
        int len;
        if(prelen > 12){ //ie if 13
            prelen = 12;
        } else if (-2 > prelen) {
            prelen = -2;
        }
        len = (prelen+3); //maxval = 15
        int order = (int)(Math.random()*2);
        String currname = "";
        for (int i = 0; i < len; i++) {
            //System.out.println("i = " + i);
            if((order+i)%2==0){
                currname += vowels[(int)(Math.random()*5)];
            } else if (order+i%3==0){
                currname += blends[(int)(Math.random()*25)];
            } else {
                currname += consonants[(int)(Math.random()*21)];
            }
        }
        return currname + "coin"; //maxval = 19
    }

    public String generateUniqueName(){
        String c_name = generateName();
        while(transactionService.currencyExists(c_name)){
            System.out.println("c_name = " + c_name + " already exists...creating new name");
            c_name = generateName();
        }
        return c_name;
    }

    public String generateCurrency(Player cb, ArrayList<CurrencyStatus> cslist){
        int ccount = cslist.size();

        //GENERATE CURRENCY NAME
        String c_name = generateUniqueName();
        System.out.println("Generating new currency = " + c_name);


        String cbname = cb.getUser_name();
        //GENERATE CURRENCY BALANCE
        Wallet cwallet = transactionService.createCurrency(cbname, c_name);
        double newcbalance = cwallet.getBalance();

        int usercount = transactionService.userCount();
        double newcamount = transactionService.validateBalance((2*newcbalance)/(ccount+usercount)); //*2 is because math random with have an average value of 1/2
        for(CurrencyStatus cs : cslist){
            System.out.println("creating transactions with currency: " + cs);
            String c = cs.getC_name();
            double as = cs.getBalancesupply();
            double os = cs.getGivesupply();
            double everycamount = transactionService.validateBalance((2*(os+as))/(ccount+usercount));
            //CREATE A BUY ORDER FOR EVERY CURRENCY USING NEW CURRENCY
            Order neworder = new Order(cbname, c, everycamount*Math.random(),
                    c_name, newcamount*Math.random());
            insertProcessing(neworder, true);
            //CREATE BUY ORDERS FOR NEW CURRENCY USING EVERY CURRENCY
            Order buynew = new Order(cbname, c_name, newcamount*Math.random(),
                    c, everycamount*Math.random());
            insertProcessing(buynew, true);
        }

        return c_name;

    }

    public static ArrayList<CurrencyStatus> getRandomElements(ArrayList<CurrencyStatus> cslist, int n){
        ArrayList<CurrencyStatus> rlist = new ArrayList<CurrencyStatus>();
        int objsize = cslist.size();
        for(int i = 0; i < n; i++){
            rlist.add(cslist.get((int)(Math.random()*objsize)));
        }
        return rlist;
    }

    public void getRandomElementsTest() {
        ArrayList<CurrencyStatus> cslist = generateCSList();

        ArrayList<CurrencyStatus> rlist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rlist = getRandomElements(cslist, 10);
        }
        for(Object cs : rlist){
            System.out.println("cs = " + cs);
        }
        System.out.println("---End Test---");

    }
    //(INCOMPLETE) FIX THE NEWS TO STRING
    public void executeRandomOrdersDB(String user_name, LinkedList<Order> olist){
        //Date startero = new Date();
        int olistsize = olist.size();
        int rnum = (int)(Math.random()*5);
        for(int i = 0; i <rnum && i<olistsize; i++) {
            //execute a random order
            int ordernum = (int)(olist.size()*Math.random());
            Order o = olist.get(ordernum);
            olist.remove(ordernum);
            if(!(o.getOwner_name().equals(user_name))){
                PendingOrder p = OrderExecution(o, user_name);
                if(p.getStatus().equals("order executed")){
                    News n = new News(user_name + " executes order: " + o);
                    transactionService.addNews(n);
                } else {
                    System.out.println("[Execution failed] status:" + p.getStatus());
                }
            }
        }
        System.out.println("Execute Random Orders DB: " + rnum /*+ " Time Elapsed-" + (startero.getTime()-(new Date()).getTime())+"ms"*/);

    }

    public void createRandomOrders(String pname, String cname, double supply, ArrayList<CurrencyStatus> cslist, boolean isbuy){
        Date startro = new Date();
        //init
        Wallet cbwallet = transactionService.getBigWallet(pname, cname);
        if (cbwallet.getBalance() == 0){
            return;
        }

        //n orders
        int rand = (int)(Math.random()*5)+1;//1-5
        //init
        double[] flist1 = factorList(rand);
        double[] flist2 = factorList(rand);


        double cbbalance = cbwallet.getBalance();
        double cbprop = (cbbalance/(supply)); //proportion of csupply held by player
        ArrayList<CurrencyStatus> rlist = new ArrayList<>(getRandomElements(cslist, rand));
        String buyname;
        String sellname;
        double buybalance;
        double sellbalance;
        for(int i = 0; i < rand; i++){
            if(isbuy){
                buyname = cname;
                buybalance = cbbalance * flist1[i];
                sellname = rlist.get(i).getC_name();
                sellbalance = transactionService.sumSupply(sellname)*cbprop*flist2[i];
            } else {
                buyname = rlist.get(i).getC_name();
                buybalance = transactionService.sumSupply(buyname)*cbprop*flist2[i];
                sellname = cname;
                sellbalance = cbbalance * flist1[i];
            }
            Order o = new Order(pname, buyname, buybalance, sellname, sellbalance);
            insertProcessing(o, true);
        }
        System.out.println("Create Random Orders: " + rand /*+ " Time Elapsed: " + (startro.getTime()-(new Date()).getTime())+"ms"*/);
    }

    public ArrayList<CurrencyStatus> generateCSList(){
        ArrayList<String> clist = new ArrayList<>(transactionService.getCurrencies());
        ArrayList<CurrencyStatus> cslist = new ArrayList<>();
        for(String c : clist) {
            double ssum = transactionService.sumSellOrders(c);
            double bsum = transactionService.sumBuyOrders(c);
            double asum = transactionService.sumAccountBalances(c);
            CurrencyStatus cs = new CurrencyStatus(c, ssum, bsum, asum);
            cslist.add(cs);
        }
        return cslist;
    }
    public ArrayList<AdvancedCurrencyStatus> generateAdvancedCSList(String user_name){

        ArrayList<String> clist = new ArrayList<>(transactionService.getCurrencies());
        int len = clist.size();
        ArrayList<AdvancedCurrencyStatus> advcslist = new ArrayList<AdvancedCurrencyStatus>(len);
        for(int i = 0; i < len; i++){
            String cname = clist.get(i);
            double ssum = transactionService.sumSellOrders(cname);
            double bsum = transactionService.sumBuyOrders(cname);
            double asum = transactionService.sumAccountBalances(cname);
            List<Double> blist =  transactionService.getBalances(user_name, cname);
            Double[] userbal = new Double[blist.size()];
            userbal = blist.toArray(userbal);
            AdvancedCurrencyStatus acs = new AdvancedCurrencyStatus(user_name, ssum, bsum, asum, userbal);
            advcslist.set(i, acs);
        }
        return advcslist;
    }

    //every operation creates a new news item
    //mode should be 1-100 (add constraint later)
    public void operate(Player p){
        //init
        String user_name = p.getUser_name();
        int mode = p.getMode();
        double upperlimit = (100.0-mode)/(100);
        double lowerlimit = (mode)/100.0;

        //time tracking
        Date start = new Date();
        long opsum = 0;

        System.out.println("-----START SESSION-----" + start);
        ArrayList<CurrencyStatus> cslist = generateCSList();
        for(CurrencyStatus cs : cslist){
            Date startop = new Date();
            System.out.println("--conducting operations for: " + cs + "--");
            String cname = cs.getC_name();
            double ssum = cs.getGivesupply();
            double bsum = cs.getWantsupply();
            double asum = cs.getBalancesupply();
            double supply = ssum+asum;

            //If demand is too high -- attempt to execute some of the orders
            if(bsum > (supply*upperlimit)){
                System.out.println("Demand for " + cname + " is too high!");
                System.out.println("[bsum = " + bsum + "> (ssum+asum)*0.8 = " + (ssum+asum)*0.8 + "]");
                LinkedList<Order> olist= new LinkedList<Order>(transactionService.listBuyOrders(cname));
                executeRandomOrdersDB(user_name, olist);
            }
            //if demand is too low -- create some buy orders
            if (bsum < (supply*lowerlimit)){
                System.out.println("Demand for " + cname + " is too low!");
                System.out.println("[bsum = " + bsum + "< (ssum+asum)*0.2 = " + (ssum+asum)*0.2 + "]");
                createRandomOrders(user_name, cname, supply,cslist, true);

            }
            //if supply is too high -- execute some of the orders
            if(ssum > (supply*upperlimit)){
                System.out.println("Supply for " + cname + " is too high!");
                System.out.println("[ssum = " + ssum + "> (ssum+asum)*0.8 = " + (ssum+asum)*0.8 + "]");
                LinkedList<Order> olist= new LinkedList<Order>(transactionService.listUniqueSellOrders(cname, user_name));
                executeRandomOrdersDB(user_name, olist);
            }
            //if supply is too low -- create some sell orders
            if(ssum < (supply*lowerlimit)){
                System.out.println("Supply for " + cname + " is too low!");
                System.out.println("[ssum = " + ssum + "< (ssum+asum)*0.2 = " + (ssum+asum)*0.2 + "]");
                createRandomOrders(user_name, cname, supply,cslist, false);
            }
            //randomly execute order (1 in 10 chance)
            if((int)(Math.random()*10)%10==0){
                System.out.println("Executing/Creating additional random orders");
                int rand = (int)(Math.random()*4+1);
                for (int j = 0; j < rand; j++) {
                    if (rand % 4 == 0) {
                        LinkedList<Order> olist = new LinkedList<>(transactionService.listBuyOrders(cname));
                        executeRandomOrdersDB(user_name, olist);

                    } else if (rand % 4 == 1){
                        createRandomOrders(user_name, cname, supply,cslist, true);
                    } else if (rand % 4 == 2){
                        LinkedList<Order> olist = new LinkedList<>(transactionService.listUniqueSellOrders(cname, user_name));
                        executeRandomOrdersDB(user_name, olist);
                    } else {
                        createRandomOrders(user_name, cname, supply,cslist, false);
                    }
                    rand = (int)(Math.random()*4);
                }
            }
            //activates bond trading (1 in 50 chance)
            if((int)(Math.random()*50)%50==0){
                Currency c = transactionService.getCurrency(cname);
                if(!(c.getC_b_trading() == 1)){
                    News n = new News(user_name + " launches bond trading for " + cname + "!");
                    transactionService.addNews(n);
                    transactionService.addBondTrading(cname);
                    //INCOMPLETE (add create bonds)
                }

            }
            //activates futures trading (1 in 50 chance)
            if((int)(Math.random()*50)%50==0){
                Currency c = transactionService.getCurrency(cname);
                if(!(c.getC_f_trading() == 1)){
                    News n = new News(user_name + " launches futures trading for " + cname + "!");
                    transactionService.addNews(n);
                    transactionService.addFuturesTrading(cname);
                    //INCOMPLETE (add create futures)
                }
            }

            //time tracking
            long optime = (startop.getTime()-new Date().getTime());
            System.out.println("--end operations-- Time elapsed: " + optime +"ms");
            opsum+=optime;
        }
        //randomly create new currency (1 in 5 chance)
        if((int)(Math.random()*5)%5==0){
            News n = new News(user_name + " launches new currency: " +
                    generateCurrency(p, cslist));
            transactionService.addNews(n);
        }


        System.out.println("-----END SESSION----- Operations Total: " + opsum + "Time elapsed:" + (start.getTime()-new Date().getTime()) + "ms");



    }
}
