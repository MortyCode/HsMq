package com.hsms.mqserver.strategy;

import com.hsmq.data.Head;
import com.hsmq.data.HsError;
import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.Message;
import com.hsmq.data.Pull;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsmq.protocol.HsDecodeData;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqserver.strategy.executors.BaseExecutor;
import com.hsms.mqserver.strategy.executors.MessageExecutor;
import com.hsms.mqserver.strategy.executors.PullExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:41 下午
 */
public class MessageStrategy {


    private static Map<OperationEnum, BaseExecutor<?,?>> enumExecutorMap ;

    static {
        enumExecutorMap = new HashMap<>();
        enumExecutorMap.put(OperationEnum.Message,new MessageExecutor());
        enumExecutorMap.put(OperationEnum.Pull,new PullExecutor());
    }



    public static HsEecodeData executor(HsDecodeData hsDecodeData){
        HsReq<?> hsReq = (HsReq<?>) hsDecodeData.getData();

        OperationEnum operationEnum = hsReq.getOperationEnum();
        if (operationEnum==null){
            return HsEecodeData.resp(HsError.ParameterWrongType);
        }

        BaseExecutor<?,?> baseExecutor = enumExecutorMap.get(operationEnum);
        HsResp<?> hsResp = baseExecutor.executor0(hsReq);
        return HsEecodeData.resp(hsResp);
    }



}
