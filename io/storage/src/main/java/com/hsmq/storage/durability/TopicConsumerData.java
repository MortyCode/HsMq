package com.hsmq.storage.durability;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ：河神
 * @date ：Created in 2021/10/8 11:37 上午
 */
public class TopicConsumerData  {

    private Map<String,Map<String, Long>> offSetMap;

    public Map<String, Map<String, Long>> getOffSetMap() {
        return offSetMap;
    }

    public void setOffSetMap(Map<String, Map<String, Long>> offSetMap) {
        this.offSetMap = offSetMap;
    }


}
