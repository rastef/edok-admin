package de.rastef.edok.admin.i18n;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Logger;

@Named
@SessionScoped
public class LocaleController implements Serializable {

	private static final Logger log = Logger.getLogger(LocaleController.class.getCanonicalName());
	private static final long serialVersionUID = 1L;

    private Locale locale;
    
    @Inject
    FacesContext facesContext;
    
    @Inject
    ExternalContext externalContext; 

    public LocaleController() {
	}

    public void changeLocaleTo(Locale locale) {
    	this.locale = locale;
    	facesContext.getViewRoot().setLocale(locale);
    	log.info("locale in changeLocaleTo() gesetzt auf: " + locale);
    }

	@PostConstruct
    public void init() {
        locale = externalContext.getRequestLocale();
        //log.info("locale in init() gesetzt auf: " + locale);
    }


	// Getter und Setter
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
