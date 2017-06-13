package kr.pe.ryudaewan.stiletto.autocomplete.dao.impl;

import kr.pe.ryudaewan.stiletto.autocomplete.dao.CustomerDAO;
import kr.pe.ryudaewan.stiletto.autocomplete.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ryudaewan on 2017-06-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/mvc-dispatcher-servlet-test.xml"})
public class CustomerDAOImplMyBatisTest {
    @Autowired
    private CustomerDAO customerDAO;

    @Test
    public void testSearchCustomers() {
        List<Customer> result = customerDAO.searchCustomers("삼성전자");
        assertNotNull(result);
        assertEquals(result.size() > 0, true);

        for (Customer customer : result) {
            assertEquals(customer.getCustNm().startsWith("삼성"), true);
        }
    }
}
