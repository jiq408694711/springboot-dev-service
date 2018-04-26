package com.chengdu.jiq.springboottest;

import com.chengdu.jiq.common.utils.JsonConvertor;
import com.chengdu.jiq.model.vo.CityResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jiyiqin on 2017/9/18.
 * <p>
 * There are multiple ways to test this particular feature in our application.
 * Here’s one example that leverages MockMvc, so it does not require to start the Servlet container:
 * <p>
 * 关于SpringBootTest注解：
 * The @SpringBootTest annotation tells Spring Boot to go and look for a main
 * configuration class (one with @SpringBootApplication for instance), and use
 * that to start a Spring application context.
 *
 * 若出现多个@SpringBootApplication，则需要显示指定：
 * @ContextConfiguration(classes = {SpringbootDevServiceApplication.class})
 *
 * <p>
 * 关于AutoConfigureMockMvc注解：
 * here Spring Boot is only instantiating the web layer, not the whole context.
 * your code will be called exactly the same way as if it was processing a real
 * HTTP request, but without the cost of starting the server.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonConvertor jsonConvertor;

//    @MockBean
//    private StorageService storageService;

    @Test
    public void testCityController() throws Exception {
        MvcResult result = mockMvc.perform(get("/city/listCities")).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        List<CityResponse> response = jsonConvertor.fromJSON(responseBody, List.class, CityResponse.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void shouldListAllFiles() throws Exception {
//        given(this.storageService.loadAll())
//                .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));
//
//        this.mvc.perform(get("/")).andExpect(status().isOk())
//                .andExpect(model().attribute("files",
//                        Matchers.contains("http://localhost/files/first.txt",
//                                "http://localhost/files/second.txt")));
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
//                "text/plain", "Spring Framework".getBytes());
//        this.mvc.perform(fileUpload("/").file(multipartFile))
//                .andExpect(status().isFound())
//                .andExpect(header().string("Location", "/"));
//
//        then(this.storageService).should().store(multipartFile);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void should404WhenMissingFile() throws Exception {
//        given(this.storageService.loadAsResource("test.txt"))
//                .willThrow(StorageFileNotFoundException.class);
//
//        this.mvc.perform(get("/files/test.txt")).andExpect(status().isNotFound());
    }

}