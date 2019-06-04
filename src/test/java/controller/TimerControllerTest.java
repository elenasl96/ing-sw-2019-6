package controller;

import network.Manager;
import org.junit.jupiter.api.Test;

public class TimerControllerTest {

    @Test
    void test(){
        TimerController.get().addGroup(Manager.get().createGroup(5,1));
        TimerController.get().startTimer(1);
    }
}
