package banquemisr.challenge05.controller;

import banquemisr.challenge05.config.ApplicationConfiguration;
import banquemisr.challenge05.config.JwtAuthenticationFilter;
import banquemisr.challenge05.config.SecurityConfiguration;
import banquemisr.challenge05.model.TaskDto;
import banquemisr.challenge05.model.User;
import banquemisr.challenge05.model.UserRepository;
import banquemisr.challenge05.service.JwtService;
import banquemisr.challenge05.service.TaskService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SecurityConfig.class, ApplicationConfiguration.class,
//        SecurityConfiguration.class, JwtAuthenticationFilter.class})
@WebMvcTest(value = TaskController.class)
@WebAppConfiguration
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetails userDetails;
    @MockBean
    User user;

    String mockTask = "{\"title\":\"Task1\",\"durationInHours\":1}";
    TaskDto taskDto = new TaskDto();

    @BeforeEach
    public void setup() {
        taskDto.setTitle("Task1");
        taskDto.setDurationInHours(1);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(jwtService.extractUsername(anyString())).thenReturn("Joe.Doe@email.com");
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(true);
    }

    @Test
    @WithUserDetails
    public void getAllTasks() throws Exception {

        when(taskService.getAllTasks()).thenReturn(List.of(taskDto));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/tasks").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UuRG9lQGVtYWlsLmNvbSIsImlhdCI6MTcyMTI4OTM0MSwiZXhwIjoxNzIxMjkyOTQxfQ.NtrGT5tbup_zSFW6U_2DR0sQzP_lO_oPefykjurRrbc").accept(
                MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        JSONAssert.assertEquals("[" + mockTask + "]", result.getResponse()
                .getContentAsString(), false);
    }


    @Test
    @WithUserDetails
    public void searchTaskByTitle() throws Exception {

        when(taskService.searchTaskByTitle(anyString())).thenReturn(List.of(taskDto));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/task/search/Task").header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UuRG9lQGVtYWlsLmNvbSIsImlhdCI6MTcyMTI4OTM0MSwiZXhwIjoxNzIxMjkyOTQxfQ.NtrGT5tbup_zSFW6U_2DR0sQzP_lO_oPefykjurRrbc").accept(
                MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        JSONAssert.assertEquals("[" + mockTask + "]", result.getResponse()
                .getContentAsString(), false);
    }
}
