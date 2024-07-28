package de.rastef.edok.admin.transport;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TransportBeanDez4 extends TransportBean {

    private static final Logger logger = Logger.getLogger(TransportBeanDez4.class.getCanonicalName());

    public TransportBeanDez4() { super(); }

    public void assign() {
        doTransport("dez_4");
    }

}
