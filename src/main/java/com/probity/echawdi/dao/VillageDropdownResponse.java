
package com.probity.echawdi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.probity.echawdi.entity.MVillageMapper;

public class VillageDropdownResponse {

    private Map<String, String> encryHeaders = new HashMap<>();

    private List<MVillageMapper> villageList;

    public Map<String, String> getEncryHeaders() {
        return encryHeaders;
    }

    public void setEncryHeaders(Map<String, String> originalInfoMap) {

        if (originalInfoMap == null) {
            this.encryHeaders = new HashMap<>();
            return;
        }

        Map<String, String> encryptedInfoMap = new HashMap<>();

        encryptedInfoMap.put("echHost", originalInfoMap.get("echHost"));
        encryptedInfoMap.put("mhrHost", originalInfoMap.get("mhrHost"));
        encryptedInfoMap.put("mhrDbName", originalInfoMap.get("mhrDbName"));
        encryptedInfoMap.put("mhrSchemaName", originalInfoMap.get("mhrSchemaName"));
        encryptedInfoMap.put("echDbName", originalInfoMap.get("echDbName"));
        encryptedInfoMap.put("echSchemaName", originalInfoMap.get("echSchemaName"));

        this.encryHeaders = encryptedInfoMap;
    }

    public List<MVillageMapper> getVillageList() {
        return villageList;
    }

    public void setVillageList(List<MVillageMapper> villageList) {
        this.villageList = villageList;
    }
}
