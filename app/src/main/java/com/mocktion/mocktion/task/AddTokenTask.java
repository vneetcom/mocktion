package com.mocktion.mocktion.task;

import com.mocktion.mocktion.util.ContractFacade;

import java.util.concurrent.Callable;

/**
 * Created by user on 2018/03/11.
 */

public class AddTokenTask implements Callable<String> {

    String senderAddress;
    long value;
    public AddTokenTask(String senderAddress, long value) {
        this.senderAddress = senderAddress;
        this.value = value;
    }

    @Override
    public String call() throws Exception {
        ContractFacade.addToken(senderAddress,value);
        return "success";
    }
}
