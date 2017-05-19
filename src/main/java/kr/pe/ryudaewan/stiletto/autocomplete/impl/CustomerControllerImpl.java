package kr.pe.ryudaewan.stiletto.autocomplete.impl;

import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pe.ryudaewan.stiletto.autocomplete.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerController;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerService;

/**
 * Created by ryudaewan on 2017-05-17.
 */
@Controller
public class CustomerControllerImpl implements CustomerController {
    private static final Log logger = LogFactory.getLog(CustomerControllerImpl.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/searchCustomers", method = RequestMethod.GET)
    @Override
    public @ResponseBody
    List<Customer> searchCustomers(@RequestParam String keyword) {
        if (logger.isDebugEnabled()) logger.debug(keyword);

        List<Customer> searchResult = customerService.searchCustomers(keyword);

        return searchResult;
    }
}
