package kr.co.rap.agent.process;

import java.util.Map;

public interface ProcessMapper {
    public void sendProductInfo(Map<String, Integer> productInfo);
    public String getIp();
    public long measure() throws Exception;
    public long readValue () throws Exception;
    public long measureAndSetTare() throws Exception;
    public boolean isReadyForMeasurement();
}
