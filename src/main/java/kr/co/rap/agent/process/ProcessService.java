package kr.co.rap.agent.process;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ProcessService {
    public Map<String, String> receiveManufacture(InputInfo inputInfo);

    public void executeManufacture(InputInfo inputInfo);

    public boolean viewContactSwitch();

    public void controlPump(InputInfo inputInfo);

    public void controlLED(boolean LEDStatus);

    public int measureProductWeight();

    public void sendProductInfo(Map<String, Integer> productInfo);
}
