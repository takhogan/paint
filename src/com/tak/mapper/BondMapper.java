package com.tak.mapper;
import com.tak.pojo.Bond;
import org.apache.ibatis.annotations.Param;
import java.time.ZonedDateTime;
import java.util.List;

public interface BondMapper {
    int addBond(Bond b);
    Bond getBond(int loan_id);
    List<Bond> listBonds();
    List<Bond> listActiveBonds();
    List<Bond> listInActiveBonds();
    List<Bond> listLendRequests(String c_name);
    List<Bond> listBorrowRequests(String c_name);
    int updateBond(Bond b);
    int makeActive(@Param("loan_id") int loan_id, @Param("last_payment") ZonedDateTime last_payment);
    int makePending(int loan_id);
    int addBorrower(@Param("loan_id") int loan_id, @Param("borrower_name") String borrower_name);
    int addLender(@Param("loan_id") int loan_id, @Param("owner_name") String owner_name);
    int deleteBond(int loan_id);
}
