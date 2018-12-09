package optimodlyon.agile.endpoints;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import optimodlyon.agile.controller.Controller;


	
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class EndPointsTest {
 
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void givenMapFile_whenGetMapFile_thenReturnJsonArray()
      throws Exception {
         
        Employee alex = new Employee("alex");
     
        List<Employee> allEmployees = Arrays.asList(alex);
     
        given(service.getAllEmployees()).willReturn(allEmployees);
     
        mvc.perform(get("/map/petit")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].name", is(alex.getName())));
    }

}


