package optimodlyon.agile.endpoints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import optimodlyon.agile.controller.Controller;


	
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class EndPointsTest {
    
    @Test
    public void givenMapFile_whenGetMapFile_thenReturnJsonArray() {
    	
    }

}


