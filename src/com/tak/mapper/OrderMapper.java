package com.tak.mapper;
import com.tak.pojo.Order;
import com.tak.pojo.Wallet;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int addOrder(Order order);
    Order getOrder(int order_id);
    List<Order> listOrders();
    List<Order> listOrdersByUsername(String owner_name);
    List<Order> listBuyOrders(String c_name);
    List<Order> listUniqueBuyOrders(@Param("c_name") String c_name, @Param("user_name") String user_name);
    List<Order> listSellOrders(String c_name);
    List<Order> listUniqueSellOrders(@Param("c_name") String c_name, @Param("user_name") String user_name);
    Double sumBuyOrders(String c_name);
    Double sumSellOrders(String c_name);
    Double sumOrdersByUsername(String owner_name);
    int deleteOrder(int order_id);
    int deleteAll();
}
