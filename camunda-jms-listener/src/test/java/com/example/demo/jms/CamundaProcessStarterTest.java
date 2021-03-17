package com.example.demo.jms;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;

@ExtendWith(MockitoExtension.class)
class CamundaProcessStarterTest {

    @InjectMocks
    @Resource
    private CamundaProcessStarter camundaProcessStarter;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    /* EXAMPLE MOCKS:
        CamundaProcessStarter starter = new CamundaProcessStarter();
        doReturn(runtimeService).when(runtimeService).setVariable(anyString(), any(), 1);
        Mockito.when(starter.startProcessByMessage(msg), Mockito.any(String.class), Mockito.any(String.class));*/

    @Test
    public void it_start_a_process() {
        /*final RuntimeService runtimeService = mock(RuntimeService.class);
        when(runtimeService.createMessageCorrelation(anyString()).setVariable(anyString(), anyString()).setVariable(anyString(), anyString()).processInstanceBusinessKey(anyString())).
                thenReturn(new MessageCorrelationBuilderImpl(new CommandExecutorImpl(),"test"));
        DeserializedMessage msg = new DeserializedMessage("1234", "test");
        camundaProcessStarter.startProcessByMessage(msg);
        verify(runtimeService).startProcessInstanceByKey("1234");*/
    }
}