package de.rastef.edok.admin.transport;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TransportBeanDez5 extends TransportBean {

    private static final Logger logger = Logger.getLogger(TransportBeanDez5.class.getCanonicalName());

    public TransportBeanDez5() {
        super();
    }

    public void assign() {
        doTransport("dez_5");
    }

}
