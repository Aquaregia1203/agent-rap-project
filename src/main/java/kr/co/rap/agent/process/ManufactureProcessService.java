package kr.co.rap.agent.process;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.GpioUtil;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;

public class ManufactureProcessService{
    private GpioController controller;
    private Pin OUTPUT_GPIO_01;
    private GpioPinDigitalOutput OUT_PIN_01;

    public ManufactureProcessService() {
        GpioUtil.isPrivilegedAccessRequired();
        this.OUT_PIN_01 = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED", PinState.LOW);
        this.OUTPUT_GPIO_01 = RaspiPin.GPIO_01;
    }

    /*
    @Override
    public void afterPropertiesSet() throws Exception {
        Gpio.wiringPiSetup();       //TODO 여기야 여기
        controller = GpioFactory.getInstance();
    }
*/
//    public void run() {
//        System.out.println("여기도 확인 되나요");
//        executeManufacture(ProcessController.inputInfo);
//    }


    public void executeManufacture(InputInfo inputInfo) {
        try {
 //           while (!viewContactSwitch()) {  // 접촉 스위치 상태가 false인 경우는 0.5초 간격으로 LED 제어
//                onLED();
//                Thread.sleep(500);
//                offLED();
//                Thread.sleep(500);
//            }
//
//            controlPump(inputInfo);  // 접촉 스위치 상태가 true인 경우 원재료 통 별 펌프를 제어함.
//
//            Thread.sleep(10000);  //10초 대기.
//
            int productWeight = measureProductWeight();  //10초가 흐른 후 무게를 측정한 값을 저장한다.

            onLED();   //  무게 측정 후 LED를 점등.
//
//            while (viewContactSwitch()) {
//                continue;
//            }

            Map<String, Integer> product = new HashMap<String, Integer>();
            product.put("productWeight", productWeight);

            sendProductInfo(product);
            System.out.println("알고싶어요");
            flashLED();

            inputInfo = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> receiveManufacture(InputInfo inputInfo) {
        return null;
    }


    public boolean viewContactSwitch() {
        return false;
    }

    public void controlPump(InputInfo inputInfo) {

    }

    public void flashLED() {
       /* try {
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
        }*/
    }

    public void onLED() {

        try {
            OUT_PIN_01.high();
            Thread.sleep(500);
            OUT_PIN_01.low();
            Thread.sleep(500);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offLED() {

    }

    public int measureProductWeight() {
        return 0;
    }

    public void sendProductInfo(Map<String, Integer> productInfo) {
        //172.16.30.115 상대 서버 측 IP
        String url = "http://192.168.0.152/product";
        StringBuffer body = new StringBuffer();
        body.append("{")
               .append("  \"productWeight\":1000")
               .append("}");

        OkHttpClient okHttpClient = new OkHttpClient();
        try {
        //post 요청을 위한 RequestBody 생성
            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("application/json; " +
                            "charset=UTF-8"), body.toString());

            Request.Builder builder = new Request.Builder()
                                                 .url(url)
                                                 .post(requestBody);

            Request request = builder.build();

            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();

                if (responseBody != null) {
                    System.out.println(responseBody.string());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
