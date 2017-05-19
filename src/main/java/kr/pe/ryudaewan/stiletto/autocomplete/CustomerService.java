package kr.pe.ryudaewan.stiletto.autocomplete;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Created by ryudaewan on 2017-05-17.
 */
@Service
public interface CustomerService {
    public List<Customer> searchCustomers(String keyword);
}
