package roomplanner.utils

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class CustomLoggingInInterceptor extends AbstractPhaseInterceptor<Message> {

    public CustomLoggingInInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @SuppressWarnings("unchecked")
    public void handleMessage(Message message) throws Fault {
        System.out.println "InInterceptor"
    }
}