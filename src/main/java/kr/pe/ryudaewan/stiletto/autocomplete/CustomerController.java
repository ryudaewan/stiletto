package kr.pe.ryudaewan.stiletto.autocomplete;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ryudaewan on 2017-05-17.
 */
public interface CustomerController {
    public List<Customer> searchCustomers(String keyword);
}
