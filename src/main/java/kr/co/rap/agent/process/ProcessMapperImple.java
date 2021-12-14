package kr.co.rap.agent.process;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import okhttp3.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileReader;
import java.util.Map;
import java.util.Properties;

public class ProcessMapperImple implements ProcessMapper {

    private final GpioPinDigitalOutput pinSCK;
    private final GpioPinDigitalInput pinDT;
    private int gain;
    private final int loadCellMaxWeight;
    private double ratedOutput;
    private long tareOffset;

    public ProcessMapperImple(GpioPinDigitalInput pinDT, GpioPinDigitalOutput pinSCK, int loadCellMaxWeight,
                              double ratedOutput, int gain) {
        this.pinSCK = pinSCK;
        this.pinDT = pinDT;
        this.loadCellMaxWeight = loadCellMaxWeight;
        this.ratedOutput = ratedOutput;
        this.gain = gain;
        pinSCK.setState(PinState.LOW);
    }

    @Override
    public String getIp() {
        String ip = null;

        try {
            Properties properties = new Properties();
            properties.load(new FileReader("config.properties"));
            ip = properties.getProperty("ip");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip;
    }

    @Override
    public void sendProductInfo(Map<String, Integer> productInfo) {
        String url = "http://" + getIp() + "/product";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(productInfo);

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("application/json; " +
                            "charset=UTF-8"), body);

            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .post(requestBody);

            Request request = builder.build();

            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = null;
            int receiveCount = 0;

            if (response.isSuccessful()) {
                responseBody = response.body();

                if (responseBody != null) {
                    while (receiveCount == 3) {
                        if (!(responseBody.string().contains("200")) && receiveCount < 3) {
                            response = okHttpClient.newCall(request).execute();
                            receiveCount++;

                            Thread.sleep(10000);
                        }
                    }

                    responseBody.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long measure() throws Exception {
        double measuredKilogram = ((readValue() - tareOffset) * 0.5 * loadCellMaxWeight) / ((ratedOutput / 1000) * 128 * 8388608);
        double measuredGrams = measuredKilogram * 1.0644;
        long roundedGrams = Math.round(measuredGrams);

        return roundedGrams;
    }

    public long readValue() throws Exception {
        pinSCK.setState(PinState.LOW);
        while (!isReadyForMeasurement()) {
            Thread.sleep(1);
        }

        long count = 0;
        for (int i = 0; i < this.gain; i++) {
            pinSCK.setState(PinState.HIGH);
            count = count << 1;
            pinSCK.setState(PinState.LOW);
            if (pinDT.isHigh()) {
                count++;
            }
        }

        pinSCK.setState(PinState.HIGH);
        count = count ^ 0x800000;
        pinSCK.setState(PinState.LOW);

        return count;
    }

    public long measureAndSetTare() throws Exception {
        long result = 0;
        for (int i = 0; i < 10; i++) {
            result = result + readValue();
            try { Thread.sleep(100); } catch (Exception e) { }
        }

        this.tareOffset = result / 10;

        return result / 10;
    }

    public boolean isReadyForMeasurement() {
        return (pinDT.isLow());
    }
}
