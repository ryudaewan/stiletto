package kr.pe.ryudaewan.stiletto.autocomplete.dao.impl;

import kr.pe.ryudaewan.stiletto.autocomplete.entity.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.dao.CustomerDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ryudaewan on 2017-06-01.
 */
@Repository
public class CustomerDAOImplMyBatis implements CustomerDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override @SuppressWarnings("unchecked")
    public List<Customer> searchCustomers(String keyword) {
        HashMap<String, String> params = new HashMap<>();
        String temp = keyword.toUpperCase();
        params.put("keyword", temp);

        if (2 < temp.length()) {
            params.put("keyword.substring", temp.substring(0, 2) + "%");
        } else {
            params.put("keyword.substring", temp + "%");
        }

        return this.sqlSessionTemplate.selectList("selectCustNms", params);
    }
}
