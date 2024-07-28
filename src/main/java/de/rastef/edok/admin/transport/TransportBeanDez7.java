package de.rastef.edok.admin.transport;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TransportBeanDez7 extends TransportBean {

    private static final Logger logger = Logger.getLogger(TransportBeanDez7.class.getCanonicalName());

    public TransportBeanDez7() {
        super();
    }

    public void assign() {
        doTransport("dez_7");
    }

}
