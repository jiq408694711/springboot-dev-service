package com.chengdu.jiq.springtest.junit;

import com.chengdu.jiq.SpringbootDevServiceApplication;
import com.chengdu.jiq.common.utils.JsonConvertor;
import com.chengdu.jiq.model.vo.CityResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jiyiqin on 2017/9/19.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringbootDevServiceApplication.class})
@TestPropertySource("/application.properties")
@WebAppConfiguration
@AutoConfigureMockMvc
public class ControllerLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonConvertor jsonConvertor;

    @Test
    public void testCityController() throws Exception {
        MvcResult result = mockMvc.perform(get("/city/listCities")).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        List<CityResponse> response = jsonConvertor.fromJSON(responseBody, List.class, CityResponse.class);
        Assert.assertNotNull(response);
    }
}
