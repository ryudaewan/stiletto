package kr.pe.ryudaewan.stiletto.autocomplete.dao.impl;

import kr.pe.ryudaewan.stiletto.autocomplete.dao.CustomerDAO;
import kr.pe.ryudaewan.stiletto.autocomplete.entity.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ryudaewan on 2017-05-17.
 */
// @Repository
public class CustomerDAOImplSpringJdbc implements CustomerDAO {
    private static final Log logger = LogFactory.getLog(CustomerDAOImplSpringJdbc.class);
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        List<Customer> searchResult = new ArrayList<>();
        String sql = "SELECT cn.CUST_ID, cn.CUST_NM, cn.LANG_CD, " +
                "UTL_MATCH.JARO_WINKLER(?, cn.CUST_NM) jw\n" +
                "from TB_CUST_NM cn\n" +
                "where 1=1 AND\n" +
                "cn.CUST_NM like ?\n" +
                "ORDER BY jw DESC";
        String temp = keyword.toUpperCase();

        Object[] params = {temp, temp + "%"};

        if (2 < temp.length()) params[1] = temp.substring(0, 2) + "%";

        if (logger.isDebugEnabled()) {
            logger.debug(sql);

            for (int inx = 0; inx < params.length; inx++)
                logger.debug(params[inx]);
        }

        //////////////////////////////////
        System.out.println("\n" + logger);
        //////////////////////////////////

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (null != rows && !rows.isEmpty() && 1.0 == (double) rows.get(0).get("JW")) {
            rows = rows.subList(0, 1);
        }

        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setId((String) row.get("CUST_ID"));
            customer.setCustNm((String) row.get("CUST_NM"));
            customer.setLangCd((String) row.get("LANG_CD"));
            customer.setJwVal((Double) row.get("JW"));
            customer.setKeyword(keyword);
            searchResult.add(customer);
        }

        //////////////////////////////////
        //System.out.println(searchResult);
        /////////////////////////////////

        return searchResult;
    }
}