package kr.pe.ryudaewan.stiletto.autocomplete;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ryudaewan on 2017-05-17.
 */
@Service
public interface CustomerService {
    public List<Customer> searchCustomers(String keyword);
}
