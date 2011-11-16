package br.gov.frameworkdemoiselle.vaadin.internal.implementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.internal.implementation.CoreBundle;
import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.vaadin.util.ViewNavigator;

import com.vaadin.ui.Window.Notification;

@SessionScoped
@Alternative
public class MessageContextVaadinImpl implements Serializable, MessageContext {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	private final List<Message> messages = new ArrayList<Message>();

	public MessageContextVaadinImpl() {
	}

	@Override
	public void add(Message message, Object... params) {
		Message aux;

		if (params != null) {
			aux = new DefaultMessage(message.getText(), message.getSeverity(), params);
		} else {
			aux = message;
		}

		logger.debug(CoreBundle.get().getString("adding-message-to-context", message.toString()));

		ViewNavigator navigator = Beans.getReference(ViewNavigator.class);
		if (navigator != null) {
			if (navigator.getNavigable() != null) {
				if (aux.getSeverity() == SeverityType.WARN) {
					navigator.getNavigable().getApplication().getMainWindow()
							.showNotification("", aux.getText(), Notification.TYPE_WARNING_MESSAGE);
				} else if (aux.getSeverity() == SeverityType.ERROR) {
					navigator.getNavigable().getApplication().getMainWindow()
							.showNotification("", aux.getText(), Notification.TYPE_ERROR_MESSAGE);
				} else if (aux.getSeverity() == SeverityType.INFO) {
					navigator.getNavigable().getApplication().getMainWindow()
							.showNotification("", aux.getText(), Notification.TYPE_HUMANIZED_MESSAGE);
				}
			}
		}

		messages.add(aux);
	}

	@Override
	public void add(String text, Object... params) {
		add(text, null, params);
	}

	@Override
	public void add(String text, SeverityType severity, Object... params) {
		add(new DefaultMessage(text, severity, params));
	}

	@Override
	public List<Message> getMessages() {
		return messages;
	}

	@Override
	public void clear() {
		messages.clear();
	}

}
