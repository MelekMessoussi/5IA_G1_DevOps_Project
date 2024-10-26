package tn.esprit.devops_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IOperatorService operatorService;

    @InjectMocks
    private OperatorController operatorController;

    private Operator operator1;
    private Operator operator2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(operatorController).build();

        // Sample operators for testing
        operator1 = new Operator();
        operator1.setId(1L);
        operator1.setName("Operator 1");

        operator2 = new Operator();
        operator2.setId(2L);
        operator2.setName("Operator 2");
    }

    @Test
    public void testGetOperators() throws Exception {
        List<Operator> operators = Arrays.asList(operator1, operator2);
        when(operatorService.retrieveAllOperators()).thenReturn(operators);

        mockMvc.perform(get("/operator"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Operator 1"))
                .andExpect(jsonPath("$[1].name").value("Operator 2"));

        verify(operatorService, times(1)).retrieveAllOperators();
    }

    @Test
    public void testRetrieveOperator() throws Exception {
        when(operatorService.retrieveOperator(1L)).thenReturn(operator1);

        mockMvc.perform(get("/operator/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Operator 1"));

        verify(operatorService, times(1)).retrieveOperator(1L);
    }

    @Test
    public void testAddOperator() throws Exception {
        when(operatorService.addOperator(any(Operator.class))).thenReturn(operator1);

        mockMvc.perform(post("/operator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(operator1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Operator 1"));

        verify(operatorService, times(1)).addOperator(any(Operator.class));
    }

    @Test
    public void testRemoveOperator() throws Exception {
        mockMvc.perform(delete("/operator/1"))
                .andExpect(status().isOk());

        verify(operatorService, times(1)).deleteOperator(1L);
    }

    @Test
    public void testModifyOperator() throws Exception {
        when(operatorService.updateOperator(any(Operator.class))).thenReturn(operator1);

        mockMvc.perform(put("/operator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(operator1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Operator 1"));

        verify(operatorService, times(1)).updateOperator(any(Operator.class));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
