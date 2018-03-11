package com.mocktion.mocktion.task;

import com.mocktion.mocktion.util.ContractFacade;

import java.util.concurrent.Callable;

/**
 * Created by user on 2018/03/11.
 */

public class BidPriceTask implements Callable<String> {

    String senderAddress;
    String toAddress;
    long bidPrice;

    public BidPriceTask(
            String senderAddress,
            String toAddress,
            long bidPrice) {
        this.senderAddress = senderAddress;
        this.toAddress = toAddress;
        this.bidPrice = bidPrice;
    }

    @Override
    public String call() throws Exception {
        boolean result = ContractFacade.bidPrice(senderAddress, toAddress, bidPrice);
        return result ? "success" : "error";
    }
}
