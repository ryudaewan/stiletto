package kr.pe.ryudaewan.stiletto.autocomplete.control.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import kr.pe.ryudaewan.stiletto.autocomplete.control.CustomerController;
import kr.pe.ryudaewan.stiletto.autocomplete.entity.Customer;
import kr.pe.ryudaewan.stiletto.autocomplete.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryudaewan on 2017-06-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/mvc-dispatcher-servlet-test.xml"})
public class CustomerControllerImplTest {
    @InjectMocks
    @Autowired
    CustomerController customerController;
    private MockMvc mockMvc;
    @Mock
    private CustomerService customerService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchCustomers() throws Exception {
        String keyword = "삼성전자";
        String jsonString = "[" +
                "{\"custNm\":\"삼성전자\",\"id\":\"10084667\",\"langCd\":\"ko\",\"jwVal\":1.0,\"keyword\":\"삼성전자\"}," +
                "{\"custNm\":\"삼성전자(주)\",\"id\":\"30071525\",\"langCd\":\"ko\",\"jwVal\":0.9411764705882353,\"keyword\":\"삼성전자\"}," +
                "{\"custNm\":\"삼성전자(주)\",\"id\":\"30076024\",\"langCd\":\"ko\",\"jwVal\":0.9411764705882353,\"keyword\":\"삼성전자\"}," +
                "{\"custNm\":\"삼성전자(주)\",\"id\":\"30076026\",\"langCd\":\"ko\",\"jwVal\":0.9411764705882353,\"keyword\":\"삼성전자\"}," +
                "{\"custNm\":\"삼성전자(주)\",\"id\":\"30075692\",\"langCd\":\"ko\",\"jwVal\":0.9411764705882353,\"keyword\":\"삼성전자\"}" +
                "]";
        when(customerService.searchCustomers(isA(String.class))).thenReturn(this.customerList(jsonString));

        mockMvc.perform(get("/ac/searchCustomers").param("keyword", keyword))
                .andExpect(status().isOk())
                .andDo(print());

        verify(customerService).searchCustomers(isA(String.class));
        verifyNoMoreInteractions(customerService);
    }

    private List<Customer> customerList(String jsonString) throws IOException {
        return new ObjectMapper().readValue(jsonString,
                TypeFactory.defaultInstance().constructCollectionType(List.class, Customer.class));
    }

    @Test
    public void testNoKeyword() throws Exception {
        mockMvc.perform(get("/ac/searchCustomers").param("keyword", ""))
                .andExpect(status().isOk())
                .andDo(print());
    }
}