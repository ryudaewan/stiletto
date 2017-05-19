package kr.pe.ryudaewan.stiletto.autocomplete.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.ryudaewan.stiletto.autocomplete.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerDAO;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerService;

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
