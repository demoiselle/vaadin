/*
 * Demoiselle Framework Copyright (C) 2010 SERPRO
 * ---------------------------------------------------------------------------- This file is part of Demoiselle
 * Framework. Demoiselle Framework is free software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License version 3 as published by the Free Software Foundation. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU Lesser General Public License version 3 along with this program; if not, see
 * <http://www.gnu.org/licenses/> or write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA. ---------------------------------------------------------------------------- Este arquivo
 * é parte do Framework Demoiselle. O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação do Software Livre (FSF). Este
 * programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de
 * ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português para
 * maiores detalhes. Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título "LICENCA.txt", junto com esse
 * programa. Se não, acesse <http://www.gnu.org/licenses/> ou escreva para a Fundação do Software Livre (FSF) Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.vaadin.template;

import java.io.Serializable;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.terminal.Terminal;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;

/**
 * Must be used as super class for all vaadin application. Used this class instead com.vaadin.Application. Responsible
 * to "wake up" Weld.
 * 
 * @author CETEC
 */
@SuppressWarnings("serial")
public abstract class VaadinApplication extends Application implements Serializable, HttpServletRequestListener {

	private InjectionTarget<VaadinApplication> injectionTarget;

	private CreationalContext<VaadinApplication> creationalContext;

	private boolean callDefaultTerminalErrorImpl = true;

	private static ThreadLocal<VaadinApplication> currentApplication = new ThreadLocal<VaadinApplication>();

	public static VaadinApplication getInstance() {
		return currentApplication.get();
	}

	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		if (currentApplication.get() == null)
			currentApplication.set(this);
	}

	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		currentApplication.set(null);
		currentApplication.remove();
	}

	@Override
	public void close() {
		super.close();
		getInjectionTarget().preDestroy(this);
		getInjectionTarget().dispose(this);
		getCreationalContext().release();
	}

	public void setInjectionTarget(InjectionTarget<VaadinApplication> injectionTarget) {
		this.injectionTarget = injectionTarget;
	}

	public InjectionTarget<VaadinApplication> getInjectionTarget() {
		return injectionTarget;
	}

	public void setCreationalContext(CreationalContext<VaadinApplication> creationalContext) {
		this.creationalContext = creationalContext;
	}

	public CreationalContext<VaadinApplication> getCreationalContext() {
		return creationalContext;
	}

	public boolean getCallDefaultTerminalErrorImpl() {
		return callDefaultTerminalErrorImpl;
	}

	public void setCallDefaultTerminalErrorImpl(boolean flag) {
		this.callDefaultTerminalErrorImpl = flag;
	}

	@Override
	public void terminalError(Terminal.ErrorEvent event) {
		if (getCallDefaultTerminalErrorImpl())
			super.terminalError(event);
	}

	public void reloadApp() {
		getMainWindow().executeJavaScript("window.location.href=window.location.href;");
	}

}
