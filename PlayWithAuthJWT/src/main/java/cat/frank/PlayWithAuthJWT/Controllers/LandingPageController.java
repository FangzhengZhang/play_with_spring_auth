package cat.frank.PlayWithAuthJWT.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingPageController {

    @GetMapping("/")
    public String testRequest(){
        return "Hello Page";
    }

}
