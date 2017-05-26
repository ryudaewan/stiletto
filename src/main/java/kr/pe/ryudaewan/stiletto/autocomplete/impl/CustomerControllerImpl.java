package kr.pe.ryudaewan.stiletto.autocomplete.impl;

import kr.pe.ryudaewan.stiletto.autocomplete.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerController;
import kr.pe.ryudaewan.stiletto.autocomplete.CustomerService;
import kr.pe.ryudaewan.stiletto.autocomplete.exception.EmptyInputException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryudaewan on 2017-05-17.
 */
@Controller
public class CustomerControllerImpl implements CustomerController {
    private static final Log logger = LogFactory.getLog(CustomerControllerImpl.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/ac/searchCustomers", method = RequestMethod.GET)
    @Override
    public @ResponseBody
    List<Customer> searchCustomers(@RequestParam String keyword) {
        List<Customer> searchResult = new ArrayList<>();
        keyword = keyword.trim();

        if (logger.isDebugEnabled()) logger.debug(keyword);

        if (null != keyword && !"".equals(keyword)) {
            searchResult = customerService.searchCustomers(keyword);
        } else {
            throw new EmptyInputException("AC-00000001", "검색할 고객명을 입력하지 않았습니다.");
        }

        return searchResult;
    }
}
