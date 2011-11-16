package br.gov.frameworkdemoiselle.vaadin.configuration;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.configuration.Configuration;


@Configuration(prefix = "frameworkdemoiselle.vaadin")
public class VaadinConfig {

	@Name("upload.path")
	private String uploadPath = "/tmp/";

	
	public String getUploadPath() {
		return uploadPath;
	}

	
}
