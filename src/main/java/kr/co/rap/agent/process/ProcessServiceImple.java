package kr.co.rap.agent.process;

import kr.co.rap.agent.util.InputInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessServiceImple implements ProcessService {

    @Override
    public Map<String, String> receiveManufacture(InputInfo inputInfo) {
        System.out.println("확인");
        System.out.println(inputInfo.getPumpInfo());
        return null;
    }

    @Override
    public void executeManufacture(InputInfo inputInfo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public boolean viewContactSwitch() {
        return false;
    }

    @Override
    public void controlPump(InputInfo inputInfo) {

    }

    @Override
    public void controlLED(boolean LEDStatus) {

    }

    @Override
    public int measureProductWeight() {
        return 0;
    }

    @Override
    public void sendProductInfo(Map<String, Integer> productInfo) {

    }
}
