package kr.co.rap.agent.process;

import com.pi4j.io.gpio.*;
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
        final Pin OUTPUT_GPIO_01 = RaspiPin.GPIO_01;
        final GpioController controller = GpioFactory.getInstance();
            try {
                final GpioPinDigitalOutput pin =
                        controller.provisionDigitalOutputPin(OUTPUT_GPIO_01,
                                "LED", PinState.LOW);

                for (int i = 0; i < 10; i ++) {
                    pin.high();
                    Thread.sleep(500);

                    pin.low();
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                controller.shutdown();
            }
        }



    @Override
    public int measureProductWeight() {
        return 0;
    }

    @Override
    public void sendProductInfo(Map<String, Integer> productInfo) {

    }
}
