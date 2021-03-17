package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CamundaProcessStarterTest {

    @MockBean
    private RuntimeService runtimeService;

    @InjectMocks
    @Resource
    private CamundaProcessStarter camundaProcessStarter;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        when(runtimeService).thenReturn(runtimeService);
    }

    @Test
    public void it_start_a_process() {
        final RuntimeService runtimeService = mock(RuntimeService.class);

        CamundaProcessStarter starter = new CamundaProcessStarter();
        //doReturn(runtimeService).when(runtimeService).setVariable(anyString(), any(), 1);
        DeserializedMessage msg = new DeserializedMessage("1234", "dupa");
        //Mockito.when(starter.startProcessByMessage(msg), Mockito.any(String.class), Mockito.any(String.class));

        starter.startProcessByMessage(msg);

        verify(runtimeService).startProcessInstanceByKey("1234");
    }
}