package com.mocktion.mocktion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocktion.mocktion.dao.AddressDao;
import com.mocktion.mocktion.dto.ArticleDto;
import com.mocktion.mocktion.task.AgreeTask;
import com.mocktion.mocktion.task.CancelTask;
import com.mocktion.mocktion.task.GetArticleTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class CancelAActivity extends AppCompatActivity implements View.OnClickListener {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_a);

        String myAdress = AddressDao.getInstance().getAddress();
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<ArticleDto> future = service.submit(new GetArticleTask(myAdress,myAdress));
        ArticleDto result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(result != null){
            TextView articleId = findViewById(R.id.text_article_id);
            articleId.setText((CharSequence) String.valueOf(result.id));

            TextView articleName = findViewById(R.id.text_article_name);
            articleName.setText((CharSequence) result.name);

            TextView basePrice = findViewById(R.id.text_base_price);
            basePrice.setText((CharSequence) String.valueOf(result.basePrice));

            TextView lastPrice = findViewById(R.id.text_last_price);
            lastPrice.setText((CharSequence) String.valueOf(result.lastPrice));

            TextView isReverse = findViewById(R.id.text_is_reverse);
            isReverse.setText((CharSequence) (result.isReverse ? "Reverse" : "Normal"));
        }

        service.shutdown();

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        ExecutorService service = Executors.newFixedThreadPool(1);

        switch(view.getId()){
            case R.id.cancel:
                service.submit(
                        new CancelTask(
                                AddressDao.getInstance().getAddress()));
                break;
        }

        service.shutdown();

        startActivity(new Intent(this, BoardAActivity.class));

    }

}
