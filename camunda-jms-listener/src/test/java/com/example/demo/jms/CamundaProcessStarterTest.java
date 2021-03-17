package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CamundaProcessStarterTest {

    @Mock
    private RuntimeService runtimeService;

    @Test
    void it_start_a_process() {
        CamundaProcessStarter starter = new CamundaProcessStarter(runtimeService);
        doReturn(runtimeService).when(runtimeService).setVariable(anyString(), any());

        DeserializedMessage msg = new DeserializedMessage("1234", "dupa");
        starter.startProcessByMessage(msg);

        verify(runtimeService).startProcessInstanceByKey("1234");
    }
}