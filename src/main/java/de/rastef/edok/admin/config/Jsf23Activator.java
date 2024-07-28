package de.rastef.edok.admin.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
/**
 * Diese Klasse wird benötigt, um JSF 2.3 zu aktivieren. Ohne diese
 * Aktivierung läuft JSF2.3 im JSF2.2 Mode. Dann funktionieren z.B.
 * Injection und EL resolving nicht, selbst wenn eine 2.3 kompatible
 * faces-config.xml vorhanden ist.
 *
 */
public class Jsf23Activator {
}
