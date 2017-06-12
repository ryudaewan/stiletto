package kr.pe.ryudaewan.stiletto.autocomplete.control.impl;

import kr.pe.ryudaewan.stiletto.autocomplete.entity.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.control.CustomerController;
import kr.pe.ryudaewan.stiletto.autocomplete.service.CustomerService;
import kr.pe.ryudaewan.stiletto.autocomplete.EmptyInputException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ryudaewan on 2017-05-17.
 */
@RestController
public class CustomerControllerImpl implements CustomerController {
    private static final Log logger = LogFactory.getLog(CustomerControllerImpl.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/ac/searchCustomers", method = RequestMethod.GET)
    @Override
    public @ResponseBody
    List<Customer> searchCustomers(@RequestParam String keyword) {
        List<Customer> searchResult = null;

        if (logger.isDebugEnabled()) logger.debug(keyword);

        if (null != keyword && !"".equals(keyword)) {
            searchResult = customerService.searchCustomers(keyword.trim());
        } else {
            throw new EmptyInputException("AC-00000001", "검색할 고객명을 입력하지 않았습니다.");
        }

        return searchResult;
    }

    /**
     * @see <a href="http://springboot.tistory.com/33">스프링부트 : REST 어플리케이션에서 예외 처리하기</a>
     * */
    @ExceptionHandler(value = EmptyInputException.class)
    public String nfeHandler(EmptyInputException ex){
        return ex.toString();
    }
}
