package com.tak.mapper;

import com.tak.pojo.FuturesContract;
import java.util.List;

public interface FuturesMapper {
    int addContract(FuturesContract fc);
    int getContract(int contract_id);
    List<FuturesContract> listContracts();
    List<FuturesContract> listActiveContracts();
    List<FuturesContract> listShortFutures(String c_name);
    List<FuturesContract> listLongFutures(String c_name);
    int deleteContract(int contract_id);
    int deleteAll();

}
