package com.liuhao.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

	/**
	 * @return
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {

		return new ServerEndpointExporter();
	}
}
