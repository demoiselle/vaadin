/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.vaadin.ui;

import java.util.ResourceBundle;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;

import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessClear;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessDelete;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessInvalidValue;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessSave;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window.Notification;

/**
 * Abstract Form that implements basic functionality to crud operations.
 * 
 * @author We all!
 */
@SuppressWarnings("all")
public class CrudForm<E> extends AutoForm<E> {

	private static final long serialVersionUID = 1246614603336033228L;

	private Button buttonSave;

	private Button buttonClear;

	private Button buttonDelete;

	private BeanItem<E> beanItem;

	protected ResourceBundle bundle;

	private BeanManager beanManager;

	public CrudForm(Class<E> cls) {
		super(cls);
		this.bundle = Beans.getReference(ResourceBundle.class);
		this.beanManager = Beans.getBeanManager();
		setInvalidCommitted(false);
	}

	public E getBean() {
		return beanItem.getBean();
	}

	public void setBean(E bean) {
		if (buttonSave == null) {
			createButtons();
		}
		beanItem = new BeanItem<E>(bean);
		setItemDataSource(beanItem);
	}

	protected void createButtons() {
		buttonSave = new Button(this.bundle.getString("button.save"));
		buttonClear = new Button(this.bundle.getString("button.clear"));
		buttonDelete = new Button(this.bundle.getString("button.delete"));

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		buttons.addComponent(getButtonSave());
		buttons.addComponent(getButtonDelete());
		buttons.addComponent(buttonClear);
		getFooter().addComponent(buttons);

		getButtonSave().addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					commit();
				} catch (InvalidValueException exception) {
					beanManager.fireEvent(getBean(), new AnnotationLiteral<ProcessInvalidValue>() {
					});
					getWindow().showNotification(exception.getMessage(), Notification.TYPE_WARNING_MESSAGE);
					return;
				}
				beanManager.fireEvent(getBean(), new AnnotationLiteral<ProcessSave>() {
				});
			}

		});

		buttonClear.addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				CrudForm.this.discard();
				beanManager.fireEvent(getBean(), new AnnotationLiteral<ProcessClear>() {
				});
			}

		});

		getButtonDelete().addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				beanManager.fireEvent(getBean(), new AnnotationLiteral<ProcessDelete>() {
				});
			}

		});
	}

	public void setButtonSave(Button buttonSave) {
		this.buttonSave = buttonSave;
	}

	public Button getButtonSave() {
		return buttonSave;
	}

	public Button getButtonClear() {
		return buttonClear;
	}

	public void setButtonClear(Button buttonClear) {
		this.buttonClear = buttonClear;
	}

	public void setButtonDelete(Button buttonDelete) {
		this.buttonDelete = buttonDelete;
	}

	public Button getButtonDelete() {
		return buttonDelete;
	}

	protected BeanManager getBeanManager() {
		return beanManager;
	}
}
