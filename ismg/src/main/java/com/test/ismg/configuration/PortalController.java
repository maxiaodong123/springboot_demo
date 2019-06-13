package com.test.ismg.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {

	@RequestMapping("/api")
	public String index() {
		return "redirect:swagger-ui.html";
	}

}
