package kr.pe.ryudaewan.stiletto.autocomplete.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.pe.ryudaewan.stiletto.autocomplete.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerDAO;

/**
 * Created by ryudaewan on 2017-05-17.
 */
@Repository
public class CustomerDAOImplRDB implements CustomerDAO {
    private static final Log logger = LogFactory.getLog(CustomerDAOImplRDB.class);
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
        String sql = "SELECT CT.ID\n" +
                "  , CT.NAME\n" +
                "  , CT.NATIONALITY\n" +
                "  , UTL_MATCH.JARO_WINKLER(?, ct.name) jw\n" +
//                ",  UTL_MATCH.JARO_WINKLER_SIMILARITY(?, ct.name) jws\n" +
//                ",  UTL_MATCH.EDIT_DISTANCE(?, ct.name) ed\n" +
//                ",  UTL_MATCH.EDIT_DISTANCE_SIMILARITY(?, ct.name) eds\n" +
                "FROM DEVUSER.COMPANY_NAME ct\n" +
                "WHERE 1 = 1 and\n" +
                "  CT.NAME LIKE ?\n" +
                "ORDER BY JW DESC";

        Object[] params = {keyword, keyword + "%"};

        if (2 < keyword.length()) params[1] = keyword.substring(0, 2) + "%";

        if (logger.isDebugEnabled()) {
            logger.debug(sql);

            for (int inx = 0; inx < params.length; inx++)
                logger.debug(params[inx]);
        }

        //////////////////////////////////
        System.out.println("\n" + logger);
        System.out.println(sql);
        System.out.println(this.dataSource);
        System.out.println(jdbcTemplate);
        System.out.println(keyword.length());
        for (int inx = 0; inx < params.length; inx++)
            System.out.println(params[inx]);
        //////////////////////////////////

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        //////////////////////////////////
        System.out.println(rows);
        /////////////////////////////////

        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setId(Integer.parseInt(((BigDecimal) row.get("ID")).toString()));
            customer.setCustNm((String) row.get("NAME"));
            customer.setNationality((String) row.get("NATIONALITY"));
            customer.setJwVal((Double) row.get("JW"));
            customer.setKeyword(keyword);
            searchResult.add(customer);
        }

        return searchResult;
    }
}
