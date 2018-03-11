package com.mocktion.mocktion.task;

import com.mocktion.mocktion.util.ContractFacade;

import java.util.concurrent.Callable;

/**
 * Created by user on 2018/03/11.
 */

public class GetBalanceTask implements Callable<String> {

    String senderAddress;

    public GetBalanceTask(
            String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Override
    public String call() throws Exception {
        String result = ContractFacade.getBalance(senderAddress);
        return result;
    }
}
