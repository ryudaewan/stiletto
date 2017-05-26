package kr.pe.ryudaewan.stiletto.autocomplete.impl;

import kr.pe.ryudaewan.stiletto.autocomplete.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerDAO;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ryudaewan on 2017-05-17.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDAO customerDAO;

    @Override
    public List<Customer> searchCustomers(String keyword) {
        return customerDAO.searchCustomers(keyword);
    }
}
