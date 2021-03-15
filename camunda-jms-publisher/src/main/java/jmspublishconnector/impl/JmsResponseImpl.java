package jmspublishconnector.impl;

import jmspublishconnector.JmsResponse;
import org.camunda.connect.impl.AbstractConnectorResponse;

import java.util.Map;

public class JmsResponseImpl extends AbstractConnectorResponse implements JmsResponse {

    public JmsResponseImpl(){}

    public void close(){}

    @Override
    protected void collectResponseParameters(Map<String, Object> map){}
}