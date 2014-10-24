package br.gov.frameworkdemoiselle.vaadin.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import br.gov.frameworkdemoiselle.message.Message;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.util.Beans;

import com.vaadin.ui.Window.Notification;

public class VaadinFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		chain.doFilter(request, response);

		MessageContext messageContext = Beans.getReference(MessageContext.class);

		// for (Message message : messageContext.getMessages()) {
		//
		// ViewNavigator navigator = Beans.getReference(ViewNavigator.class);
		// if (navigator != null) {
		// if (navigator.getNavigable() != null) {
		// if (message.getSeverity() == SeverityType.WARN) {
		// navigator.getNavigable().getApplication().getMainWindow()
		// .showNotification("", message.getText(), Notification.TYPE_WARNING_MESSAGE);
		// } else if (message.getSeverity() == SeverityType.ERROR) {
		// navigator.getNavigable().getApplication().getMainWindow()
		// .showNotification("", message.getText(), Notification.TYPE_ERROR_MESSAGE);
		// } else if (message.getSeverity() == SeverityType.INFO) {
		// navigator.getNavigable().getApplication().getMainWindow()
		// .showNotification("", message.getText(), Notification.TYPE_HUMANIZED_MESSAGE);
		// }
		// }
		// }
		// }

	}

	@Override
	public void destroy() {
	}
}
