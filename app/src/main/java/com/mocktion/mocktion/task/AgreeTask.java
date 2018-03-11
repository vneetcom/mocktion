package com.mocktion.mocktion.task;

import com.mocktion.mocktion.util.ContractFacade;

import java.util.concurrent.Callable;

/**
 * Created by user on 2018/03/11.
 */

public class AgreeTask implements Callable<String> {

    String senderAddress;

    public AgreeTask(
            String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Override
    public String call() throws Exception {
        ContractFacade.agree(senderAddress);
        return "success";
    }
}
