
package com.mkalymlam.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/test")
    public String index() {
        return "index";
    }
}
