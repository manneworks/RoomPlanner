package roomplanner.utils

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class CustomLoggingOutInterceptor extends AbstractPhaseInterceptor<Message> {

    public CustomLoggingOutInterceptor() {
        super(Phase.POST_INVOKE);
    }

    @SuppressWarnings("unchecked")
    public void handleMessage(Message message) throws Fault {
        System.out.println "OutInterceptor"
    }
}