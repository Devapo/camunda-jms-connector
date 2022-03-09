package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.MessageCorrelationBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CamundaProcessStarterTest {

    @Mock(answer = Answers.RETURNS_SELF)
    MessageCorrelationBuilderImpl messageCorrelationBuilder;

    @Mock
    private RuntimeService runtimeService;

    @InjectMocks
    private CamundaProcessStarter camundaProcessStarter;

    @Test
    public void it_start_a_process() {
        when(runtimeService.createMessageCorrelation(anyString()))
                .thenReturn(messageCorrelationBuilder);
        DeserializedMessage msg = new DeserializedMessage("1234", "test");
        camundaProcessStarter.startProcessByMessage(msg);

        verify(runtimeService).createMessageCorrelation("test");
        InOrder inOrder = inOrder(messageCorrelationBuilder);
        inOrder.verify(messageCorrelationBuilder).setVariable("ID", "1234");
        inOrder.verify(messageCorrelationBuilder).setVariable("PAYLOAD", "test");
        inOrder.verify(messageCorrelationBuilder).processInstanceBusinessKey("1234");
        inOrder.verify(messageCorrelationBuilder).correlate();
    }
}
