package de.rastef.edok.admin.transport;

import java.util.Base64;
import java.util.logging.Logger;

/**
 * Mutterklasse für Transporte.
 * In dieser Klasse wird die ganze Arbeit erledigt. Ist nicht
 */
public class TransportBean {

    private static final String infServerTest = "https://lww-vt01.lvrintern.lvr.de";
    private static final String infServerQs   = "https://inf-vq01.lvrintern.lvr.de";
    private static final String infServerInt  = "https://inf-vi01.lvrintern.lvr.de";
    private static final String infServerProd = "https://inf-vp01.lvrintern.lvr.de";

    private static final String tokenServerQs  = "https://verbundsystem-q.lvrintern.lvr.de/security-token-service/tokens";
    private static final String tokenServerInt = "https://verbundsystem-i.lvrintern.lvr.de/security-token-service/tokens";

    private static final String infPE = "/infinica-process-engine/rest/run/?processUri=transport/extern_itxDeploymentProcess.ipd";

    private static final String schluessel = "WjEzQU5MMDM6TGFiZXJMYWJlcmJhbUJBTTQyOkphYmJhZGFiYWR1";

    private static final Logger logger = Logger.getLogger(TransportBean.class.getCanonicalName());

    private Integer transportVonUmgebung;
    private Integer referenzenEinbeziehen;
    private String vorlagenDateien;
    private String sourceServer;
    private String targetServer;
    private String tokenServer;
    private String projectBase;
    private String restApiUrl;
    private String itxUrls;
    private String requestBody;


    public TransportBean() {
        transportVonUmgebung = 1;
        referenzenEinbeziehen = 1;
        vorlagenDateien = "";
        sourceServer = "";
        targetServer = "";
        tokenServer = "";
        projectBase = "";
        restApiUrl = "";
        itxUrls = "";
        requestBody = "";
    }

    public boolean doTransport(String dezernat) {
        logger.info("Transport wird vorbereitet ...");
        setEnvUrls();
        convertVorlagenDateienToItxUrls(dezernat);
        setRequestBody();
        logger.info("Request.Body:\r\n" + getRequestBody());
        encode();
        return true;
    }

    /**
     * Setzt alle Umgebungsabhängigen URLs.
     * In der Test-Umgebung wird kein Token benötigt, daher wird auch die URL
     * nicht gesetzt.
     */
    private void setEnvUrls() {
        switch ( getTransportVonUmgebung() ) {
            case 1:
                setSourceServer(TransportBean.infServerTest);
                setTargetServer(TransportBean.infServerQs);
                setRestApiUrl(TransportBean.infServerTest + TransportBean.infPE);
                break;
            case 2:
                setSourceServer(TransportBean.infServerQs);
                setTargetServer(TransportBean.infServerInt);
                setRestApiUrl(TransportBean.infServerQs + TransportBean.infPE);
                setTokenServer(TransportBean.tokenServerQs);
                break;
            case 3:
                setSourceServer(TransportBean.infServerInt);
                setTargetServer(TransportBean.infServerProd);
                setRestApiUrl(TransportBean.infServerInt + TransportBean.infPE);
                setTokenServer(TransportBean.tokenServerInt);
                break;
        }
    }

    /**
     * Wandelt das Format der Vorlagendateiliste aus der Eingabe in das vom
     * REST-Service benötigte Format um. Dabei werden alle Zeilenvorschübe
     * (CRLF) entfernt. Zudem wird das fehlende ICR URL Prefix hinzugefügt und
     * die ganze URL in doppelten Anführungszeichen eingefasst. Ab dem zweiten
     * Eintrag wird ein Komma als Separator vor den nächsten URL-String gesetzt.
     *
     * @param dezernat Kürzel des Dezernats im ICR. Ist "dez_4", "dez_5" oder "dez_7".
     *
     */
    private void convertVorlagenDateienToItxUrls(String dezernat) {
        setProjectBase(getSourceServer() + "/infinica-content-repository/anlei/schreiben/" + dezernat + "/") ;
        int count = 0;
        for (String vorlage : getVorlagenDateien().split("\n")) {
            count++;
            vorlage = vorlage.trim().replace("\n", "").replace("\r", "");
            if (count == 1) {
                setItxUrls("\"" + getProjectBase() + vorlage + "\"");
            } else {
                setItxUrls(getItxUrls() + "," + "\"" + getProjectBase() + vorlage + "\"");
            }
        }
    }

    /**
     * Baut den JSON String zusammen, den der Infinica Transportprozess für
     * seine Arbeit benötigt.
     */
    private void setRequestBody() {
        String deepCopy = getReferenzenEinbeziehen() == 1 ? "true" : "false";
        requestBody =
                "{\"dataStore\": " +
                        "{\"entry\": [" +
                        "{\"name\": \"itxUrls\",\"type\": \"text/plain\",\"value\": [" + getItxUrls() + "]}," +
                        "{\"name\": \"targetServerUrl\",\"type\": \"text/plain\",\"value\": \"" + getTargetServer() + "\"}," +
                        "{\"name\": \"deepCopy\",\"type\": \"text/plain\",\"value\": \"" + deepCopy + "\"}," +
                        "{\"name\": \"projectBaseUrl\",\"type\": \"text/plain\",\"value\": \"" + getProjectBase() + "\"}]}}";
    }

    private void encode() {
        String urspwd = new String(Base64.getDecoder().decode(TransportBean.schluessel));
        logger.info("Schlüssel: " + urspwd);
    }

    public Integer getTransportVonUmgebung() {
        return transportVonUmgebung;
    }

    public void setTransportVonUmgebung(Integer transportVonUmgebung) {
        this.transportVonUmgebung = transportVonUmgebung;
    }

    public Integer getReferenzenEinbeziehen() {
        return referenzenEinbeziehen;
    }

    public void setReferenzenEinbeziehen(Integer referenzenEinbeziehen) {
        this.referenzenEinbeziehen = referenzenEinbeziehen;
    }

    public String getVorlagenDateien() {
        return vorlagenDateien;
    }

    public void setVorlagenDateien(String vorlagenDateien) {
        this.vorlagenDateien = vorlagenDateien;
    }
    public String getSourceServer() {
        return sourceServer;
    }

    public void setSourceServer(String sourceServer) {
        this.sourceServer = sourceServer;
    }

    public String getProjectBase() {
        return projectBase;
    }

    public void setProjectBase(String projectBase) {
        this.projectBase = projectBase;
    }

    public String getRestApiUrl() {
        return restApiUrl;
    }

    public void setRestApiUrl(String restApiUrl) {
        this.restApiUrl = restApiUrl;
    }

    public String getTargetServer() {
        return targetServer;
    }

    public void setTargetServer(String targetServer) {
        this.targetServer = targetServer;
    }

    public String getTokenServer() {
        return tokenServer;
    }

    public void setTokenServer(String tokenServer) {
        this.tokenServer = tokenServer;
    }

    public String getItxUrls() {
        return itxUrls;
    }

    public void setItxUrls(String itxUrls) {
        this.itxUrls = itxUrls;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
