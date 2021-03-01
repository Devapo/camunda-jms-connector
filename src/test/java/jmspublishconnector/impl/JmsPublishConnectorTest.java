package jmspublishconnector.impl;
import static org.assertj.core.api.Assertions.assertThat;

import jmspublishconnector.JmsPublishConnector;
import org.camunda.connect.Connectors;
import org.camunda.connect.impl.DebugRequestInterceptor;
import org.camunda.connect.spi.Connector;
import org.junit.Before;
import org.junit.Test;

public class JmsPublishConnectorTest {
    protected JmsPublishConnector connector;
    protected DebugRequestInterceptor interceptor;

    @Before
    public void createConnector(){
        connector = new JmsPublishConnectorImpl();
        interceptor = new DebugRequestInterceptor(false);
        connector.addRequestInterceptor(interceptor);
    }

    @Test
    public void shouldDiscoverConnector(){
        Connector jmsPublisher = Connectors.getConnector(JmsPublishConnector.ID);
        assertThat(jmsPublisher).isNotNull();
    }

}