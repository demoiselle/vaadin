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
package br.gov.frameworkdemoiselle.vaadin.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Stack;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.vaadin.dialogs.ConfirmDialog;

import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.frameworkdemoiselle.vaadin.annotation.Navigable;
import br.gov.frameworkdemoiselle.vaadin.event.BeforeNavigateToView;
import br.gov.frameworkdemoiselle.vaadin.template.AbstractPresenter;
import br.gov.frameworkdemoiselle.vaadin.template.BaseVaadinView;
import br.gov.frameworkdemoiselle.vaadin.template.Presentable;
import br.gov.frameworkdemoiselle.vaadin.template.VaadinApplication;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;

/**
 * Utility class responsible to manage screen navigation.
 * 
 * @author CETEC
 */
@SessionScoped
public class ViewNavigator implements Serializable {

	private static final long serialVersionUID = 1L;

	private Stack<BaseVaadinView> stack = new Stack<BaseVaadinView>();

	@Inject
	private ResourceBundle bundle;

	/**
	 * Screen component which will be used to show ${link View}.
	 */
	private ComponentContainer navigable;

	@Inject
	private BeanManager beanManager;

	/**
	 * Navigate from current view to a new ${link View}.
	 * 
	 * @param component
	 *            ${link View} to be shown into Navigable.
	 */
	public void navigate(BaseVaadinView component) {
		if (navigable == null) {
			processNavigables();
		}
		navigate(getNavigable(), component);
	}

	/**
	 * Navigate from current view to a new ${link View}.
	 * 
	 * @param presenter
	 *            Presenter which have the view.
	 */
	public void navigate(Presentable<?> presenter) {
		navigate((BaseVaadinView) presenter.getView());
	}

	@SuppressWarnings("serial")
	private void navigate(final String caption, final TabSheet tabSheet, final BaseVaadinView view) {
		final Tab saved = tabSheet.addTab(view, caption, null);
		saved.setClosable(true);
		saved.setIcon(view.getIcon());
		tabSheet.setSelectedTab(view);
		beanManager.fireEvent(view, new AnnotationLiteral<BeforeNavigateToView>() {
		});
	}

	@SuppressWarnings("rawtypes")
	private void processNavigables() {
		for (Bean<?> bean : beanManager.getBeans(AbstractPresenter.class)) {
			AbstractPresenter<?> presenter = (AbstractPresenter) beanManager.getReference(bean, bean.getBeanClass(),
					beanManager.createCreationalContext(bean));
			BaseVaadinView view = presenter.getView();
			Field[] fields = Reflections.getNonStaticDeclaredFields(view.getClass());
			for (Field field : fields) {
				if (field.isAnnotationPresent(Navigable.class)) {
					try {
						boolean accessible = field.isAccessible();
						field.setAccessible(true);
						navigable = (ComponentContainer) field.get(view);
						field.setAccessible(accessible);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		if (navigable == null) {
			for (Bean<?> bean : beanManager.getBeans(VaadinApplication.class)) {
				VaadinApplication app = (VaadinApplication) beanManager.getReference(bean, bean.getBeanClass(),
						beanManager.createCreationalContext(bean));
				navigable = app.getMainWindow();
			}
		}

	}

	private void navigate(final ComponentContainer layout, final BaseVaadinView view) {
		if (layout instanceof TabSheet) {
			navigate(view.getCaption(), (TabSheet) layout, view);
			return;
		}
		BaseVaadinView actual = stack.isEmpty() ? null : stack.pop();
		if (actual != null) {
			stack.push(actual);
			if (actual.getWarningMessage() != null) {

				ConfirmDialog.show(actual.getWindow(), bundle.getString("navigator.title"), actual.getWarningMessage(),
						bundle.getString("navigator.confirm.yes"), bundle.getString("navigator.confirm.no"),
						new ConfirmDialog.Listener() {

							public void onClose(ConfirmDialog dialog) {
								if (dialog.isConfirmed()) {
									navigateTo(layout, view);
								}

							}

						});
			} else {
				navigateTo(layout, view);
			}
		} else {
			navigateTo(layout, view);
		}

	}

	@SuppressWarnings("serial")
	private void navigateTo(final ComponentContainer layout, BaseVaadinView component) {
		if (layout == null) {
			throw new RuntimeException("No 'Navigable' was defined. Use @Navigable or navigator.setNavigable() first.");
		}
		layout.removeAllComponents();
		layout.addComponent(component);
		beanManager.fireEvent(component, new AnnotationLiteral<BeforeNavigateToView>() {
		});
		stack.push(component);
	}

	public ComponentContainer getNavigable() {
		return navigable;
	}

	public void setNavigable(ComponentContainer navigable) {
		stack.clear();
		this.navigable = navigable;
	}

	/**
	 * Navigate to previous view.
	 */
	public void pop() {
		BaseVaadinView view = stack.isEmpty() ? null : stack.pop();
		if (view != null) {
			navigate(view);
		}
	}

}
