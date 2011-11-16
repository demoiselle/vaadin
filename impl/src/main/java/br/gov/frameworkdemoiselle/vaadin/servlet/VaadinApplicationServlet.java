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
package br.gov.frameworkdemoiselle.vaadin.servlet;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import br.gov.frameworkdemoiselle.vaadin.internal.producer.VaadinLocaleProducer;
import br.gov.frameworkdemoiselle.vaadin.template.VaadinApplication;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.ApplicationServlet;

/**
 * Servlet that must be declared in web.xml <br>
 * Responsible to start Weld and make injection possible to be used in Vaadin applications.
 * 
 * @author CETEC
 */
public class VaadinApplicationServlet extends ApplicationServlet {

	private static final long serialVersionUID = -2634983216198803174L;

	@Inject
	private BeanManager beanManager;

	private InjectionTarget<VaadinApplication> injectionTarget;

	@Override
	@SuppressWarnings({ "unchecked" })
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		if (beanManager == null) {
			beanManager = (BeanManager) getServletContext().getAttribute(BeanManager.class.getName());
		}
		try {
			injectionTarget = (InjectionTarget<VaadinApplication>) beanManager.createInjectionTarget(beanManager
					.createAnnotatedType(getApplicationClass()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Application getNewApplication(HttpServletRequest request) throws ServletException {
		VaadinLocaleProducer.locale = request.getLocale();
		CreationalContext<VaadinApplication> context = beanManager.createCreationalContext(null);
		VaadinApplication application = injectionTarget.produce(context);

		injectionTarget.inject(application, context);
		injectionTarget.postConstruct(application);
		application.setInjectionTarget(injectionTarget);
		application.setCreationalContext(context);
		return application;
	}

}
