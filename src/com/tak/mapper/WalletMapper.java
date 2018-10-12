package com.tak.mapper;
import com.tak.pojo.Wallet;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletMapper {
    int addWallet(Wallet w);
    List<Wallet> listWallets(String user_name);
    List<Double> getBalances(@Param("user_name") String user_name, @Param("c_name") String c_name);
    Double sumUserBalances(@Param("user_name") String user_name, @Param("c_name") String c_name);
    Wallet getBigWallet(@Param("user_name") String user_name, @Param("c_name") String c_name);
    Wallet getSmallWallet(@Param("user_name") String user_name, @Param("c_name") String c_name);
    List<String> getCurrencies();
    List<String> getNCurrencies(int n);
    Wallet getOneWallet(String c_name);
    Wallet getWalletByID(int wallet_id);
    int updateWallet(@Param("wallet_id") int wallet_id, @Param("balance") double balance);
    int swapOwnership(@Param("wallet_id") int wallet_id, @Param("user_name") String user_name);
    Double sumAccountBalances(String c_name);
    Integer userCount();
    int deleteWallet(int wallet_id);
}
