package com.mocktion.mocktion.task;

import com.mocktion.mocktion.dto.ArticleDto;
import com.mocktion.mocktion.util.ContractFacade;

import java.util.concurrent.Callable;

/**
 * Created by user on 2018/03/11.
 */

public class GetArticleTask implements Callable<ArticleDto> {

    String senderAddress;
    String toAddress;

    public GetArticleTask(
            String senderAddress,
            String toAddress) {
        this.senderAddress = senderAddress;
        this.toAddress = toAddress;
    }

    @Override
    public ArticleDto call() throws Exception {
        ArticleDto result = ContractFacade.getArticle(senderAddress,toAddress);
        return result;
    }
}
