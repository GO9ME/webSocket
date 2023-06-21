package com.multiwebsocketPro.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketTestController {
	@RequestMapping("/chat")
	public String showview() {
		return "thymeleaf/chatt";
	}
}
