import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class RetailerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RetailerService retailerService;

    @InjectMocks
    private RetailerController retailerController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(retailerController).build();
    }

    @Test
    void calculateRewards_ShouldReturn200_WhenValidTransactionsArePassed() throws Exception {
        
        List<Transaction> transactions = List.of(
            new Transaction("123", 100.0),
            new Transaction("124", 200.0)
        );
        
        CustomerPoints customerPoints = new CustomerPoints(300);  // Assuming a constructor with total points
        
        when(retailerService.calculatePoints(transactions)).thenReturn(customerPoints);
        
        // Perform POST request and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/rewards/calculate")
                .content(objectMapper.writeValueAsString(transactions))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customerPoints)));
        
        verify(retailerService, times(1)).calculatePoints(transactions);  // Ensure service method was called once
    }

   
    
}
