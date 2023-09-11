package com.kaluzny.demo.listener;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.dto.AutoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Consumer {

    @JmsListener(destination = "AutoTopic", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener1(Automobile automobile) {
        log.info("\u001B[32m" + "Automobile Consumer 1: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopic", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener2(Automobile automobile) {
        log.info("\u001B[33m" + "Automobile Consumer 2: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopic", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener3(Automobile automobile) {
        log.info("\u001B[34m" + "Automobile Consumer 4: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopicList", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener4(List<AutoResponseDto> automobile) {
        log.info("\u001B[34m" + "Automobile Consumer 5: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopicList", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener5(List<AutoResponseDto> automobile) {
        log.info("\u001B[34m" + "Automobile Consumer 6: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopicList", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener6(List<AutoResponseDto> automobile) {
        log.info("\u001B[34m" + "Automobile Consumer 7: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopicList", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener7(List<AutoResponseDto> automobile) {
        log.info("\u001B[34m" + "Automobile Consumer 8: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopicList", containerFactory = "automobileJmsContFactory")
    public void getAutomobileListener8(List<AutoResponseDto> automobile) {
        log.info("\u001B[34m" + "Automobile Consumer 9: " + automobile + "\u001B[0m");
    }

    @JmsListener(destination = "AutoTopic", containerFactory = "automobileJmsContFactory")
    public void createNewAutomobile(Automobile automobile) {
        log.info("\u001B[33m" + "Automobile Consumer 10: " + automobile + "\u001B[0m");
    }
}
