package kr.pe.ryudaewan.stiletto.autocomplete.control;

import kr.pe.ryudaewan.stiletto.autocomplete.entity.Customer;

import java.util.List;

/**
 * Created by ryudaewan on 2017-05-17.
 */
public interface CustomerController {
    public List<Customer> searchCustomers(String keyword);
}
