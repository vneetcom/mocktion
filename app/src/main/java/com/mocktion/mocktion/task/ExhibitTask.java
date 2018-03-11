package com.mocktion.mocktion.task;

import com.mocktion.mocktion.util.ContractFacade;

import java.util.concurrent.Callable;

/**
 * Created by user on 2018/03/11.
 */

public class ExhibitTask implements Callable<String> {

    String senderAddress;
    String name;
    long basePrice;
    boolean isReverse;

    public ExhibitTask(
            String senderAddress,
            String name,
            long basePrice,
            boolean isReverse) {
        this.senderAddress = senderAddress;
        this.name = name;
        this.basePrice = basePrice;
        this.isReverse = isReverse;
    }

    @Override
    public String call() throws Exception {
        ContractFacade.exhibit(senderAddress,name,basePrice,isReverse);
        return "success";
    }
}
