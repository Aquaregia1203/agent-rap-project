package kr.co.rap.agent.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ManufactureInfo implements Serializable {
    private List<Map<String, String>> pumpInfo;

    public ManufactureInfo() {
    }

    public List<Map<String, String>> getPumpInfo() {
        return pumpInfo;
    }

    public void setPumpInfo(List<Map<String, String>> pumpInfo) {
        this.pumpInfo = pumpInfo;
    }
}
