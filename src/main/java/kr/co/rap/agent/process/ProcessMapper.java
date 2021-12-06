package kr.co.rap.agent.process;

import java.util.Map;

public interface ProcessMapper {
    public void sendProductInfo(Map<String, Integer> productInfo);
    public String getIp();
}
