package com.training.mjunction.account.query.ui;

import javax.servlet.ServletException;

import org.springframework.stereotype.Component;

import com.vaadin.spring.server.SpringVaadinServlet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("vaadinServlet")
public class AccountsVaadinUIServlet extends SpringVaadinServlet {

	private static final long serialVersionUID = 3407235182832472556L;

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		getService().addSessionInitListener(new AccountsSessionInitListener());
		log.debug("AccountsVaadinUIServlet initialized");
	}

}
