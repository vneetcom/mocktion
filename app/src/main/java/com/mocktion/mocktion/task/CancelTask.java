package com.mocktion.mocktion.task;

import com.mocktion.mocktion.util.ContractFacade;

import java.util.concurrent.Callable;

/**
 * Created by user on 2018/03/11.
 */

public class CancelTask implements Callable<String> {

    String senderAddress;

    public CancelTask(
            String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Override
    public String call() throws Exception {
        ContractFacade.cancel(senderAddress);
        return "success";
    }
}
