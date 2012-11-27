/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação
 * do Software Livre (FSF); na versão 2 da Licença.
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/GPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse o Portal do Software Público
 * Brasileiro no endereço www.softwarepublico.gov.br ou escreva para a Fundação do Software
 * Livre (FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package ${package}.persistence;

import static br.gov.frameworkdemoiselle.annotation.Priority.MAX_PRIORITY;
import static br.gov.frameworkdemoiselle.annotation.Priority.MIN_PRIORITY;

import javax.enterprise.context.ApplicationScoped;

import org.hsqldb.Server;

import br.gov.frameworkdemoiselle.annotation.Priority;
import br.gov.frameworkdemoiselle.lifecycle.Shutdown;
import br.gov.frameworkdemoiselle.lifecycle.Startup;

@ApplicationScoped
public class DatabaseServer {

	private final Server server;

	public DatabaseServer() {
		this.server = new Server();
		server.setDatabaseName(0, "bookmark");
		server.setDatabasePath(0, "database/bookmark");
		server.setPort(9001);
		server.setSilent(true);
	}

	@Startup
	@Priority(MAX_PRIORITY)
	public void start() {
		this.server.start();
	}

	@Shutdown
	@Priority(MIN_PRIORITY)
	public void stop() {
		this.server.shutdown();
	}
}
