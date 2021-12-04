package bg.softuni.mobilelele.web;

import java.security.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

  @GetMapping("/test")
  public String test(Principal p,
      @AuthenticationPrincipal UserDetails userDetails) {

    if (p != null) {
      System.out.println(p.getClass());
      System.out.println(p);
    }

    System.out.println("Principal: " + SecurityContextHolder.getContext().getAuthentication());

    return "index";
  }

}
