package com.tak.mapper;

import com.tak.pojo.Trade;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradeMapper {
    int addTrade(Trade trade);
    Trade getTrade(int order_id);
    List<Trade> listTrades();
    int deleteTrade(int order_id);
    Trade getLatestRate(@Param("wantname") String wantname, @Param("givename") String givename);
}
