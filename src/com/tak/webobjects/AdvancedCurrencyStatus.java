package com.tak.webobjects;

public class AdvancedCurrencyStatus extends CurrencyStatus {
    public AdvancedCurrencyStatus(String c_name, double givesupply, double wantsupply,
                                  double balancesupply, Double[] user_balances) {
        super(c_name, givesupply, wantsupply, balancesupply);
        this.user_balances = user_balances;
        this.blistlen = user_balances.length;
        updated = false;
    }


    public double getUser_balances(int index) {
        return user_balances[index];
    }

    public void setUser_balances(int index, double value) {
        this.user_balances[index] = value;
        this.updated = true;
    }
    public void setSmallWallet(double value){
        this.user_balances[this.blistlen-1] = value;
    }
    public void setBigWallet(double value){
        int len = this.blistlen;
        if(len > 1){
            for (int i = 1; i < len; i++) {
                double ival = this.user_balances[i];
                if((value < ival)){ //, 32, 11, 1
                    this.user_balances[i-1] = ival;
                    continue;
                } else {
                    this.user_balances[0] = ival;
                    this.user_balances[i] = value;
                }
            }
        } else {
            this.user_balances[0] = value;
        }
        this.user_balances[0] = value;
    }

    private Double[] user_balances;
    private int blistlen;
    private boolean updated;
}
