package de.rastef.edok.admin.transport;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@FacesValidator("vorlagenValidator")
public class VorlagenValidator implements Validator<String> {

    private static final FacesMessage emptyException =
            new FacesMessage( FacesMessage.SEVERITY_ERROR,
                    "Mindestens ein ITX-Pfad muss angegeben werden!",
                    "Mindestens ein ITX-Pfad muss angegeben werden!" );

    private static final FacesMessage shortyException =
            new FacesMessage( FacesMessage.SEVERITY_ERROR,
                    "Ungültiger ITX-Pfad!",
                    "Ungültiger ITX-Pfad!" );

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        if (value == null || value.isEmpty()) {
            throw new ValidatorException(emptyException); // wird eigentlich durch Required erledigt
        }
        if (value.length() < 14) {
            throw new ValidatorException(shortyException);
        } else {
            int count = 0;
            for (String s : value.split("\n")) {
                count++;
                s = s.trim().replace("\n", "").replace("\r", "");
                if ( !s.startsWith("vorlagen/")) {
                    String es = "Fehler in Zeile " + count + ": Der Pfad beginnt nicht mit 'vorlagen/'!";
                    throw new ValidatorException(new FacesMessage( FacesMessage.SEVERITY_ERROR, es, es));
                }
                if ( !s.endsWith(".itx") ) {
                    String es = "Fehler in Zeile " + count + ": Der Pfad endet nicht mit '.itx'!";
                    throw new ValidatorException(new FacesMessage( FacesMessage.SEVERITY_ERROR, es, es));
                }
                if ( !isValidURL("https://server.lvrintern.lvr.de/" + s) ) {
                    String es = "Fehler in Zeile " + count + ": Der Pfad enthält ungültige Zeichen und ergibt keine gültige URL!";
                    throw new ValidatorException(new FacesMessage( FacesMessage.SEVERITY_ERROR, es, es));
                }
            }
        }
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}