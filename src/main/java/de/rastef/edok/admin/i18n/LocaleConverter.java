package de.rastef.edok.admin.i18n;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Locale;

@FacesConverter(forClass = Locale.class)
public class LocaleConverter implements Converter<Locale>{

	@Override
	public Locale getAsObject(FacesContext context, UIComponent component, String value) {
		return new Locale(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Locale value) {
		return value.toString();
	}

}
