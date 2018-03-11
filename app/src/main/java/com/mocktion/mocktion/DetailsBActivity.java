package com.mocktion.mocktion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mocktion.mocktion.dao.AddressDao;
import com.mocktion.mocktion.dto.ArticleDto;
import com.mocktion.mocktion.task.AgreeTask;
import com.mocktion.mocktion.task.BidPriceTask;
import com.mocktion.mocktion.task.CancelTask;
import com.mocktion.mocktion.task.GetArticleTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DetailsBActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_b);

        String myAdress = AddressDao.getInstance().getAddress();

        Intent intent = getIntent();
        String strTargetAddress = intent.getStringExtra("targetAddress");

        TextView targetAddress = findViewById(R.id.text_target_address);
        targetAddress.setText((CharSequence) strTargetAddress);

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<ArticleDto> future = service.submit(new GetArticleTask(myAdress,strTargetAddress));
        ArticleDto result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(result != null){
            TextView articleName = findViewById(R.id.text_article_name);
            articleName.setText((CharSequence) result.name);

            TextView basePrice = findViewById(R.id.text_base_price);
            basePrice.setText((CharSequence) String.valueOf(result.basePrice));

            EditText lastPrice = (EditText) findViewById(R.id.edit_last_price);
            lastPrice.setText((CharSequence) String.valueOf(result.lastPrice));

            TextView isReverse = findViewById(R.id.text_is_reverse);
            isReverse.setText((CharSequence) (result.isReverse ? "Reverse" : "Normal"));
        }

        service.shutdown();

        findViewById(R.id.bid).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        TextView targetAddress = findViewById(R.id.text_target_address);
        EditText lastPrice = (EditText) findViewById(R.id.edit_last_price);

        ExecutorService service = Executors.newFixedThreadPool(1);

        switch(view.getId()){
            case R.id.bid:
                service.submit(
                        new BidPriceTask(
                                AddressDao.getInstance().getAddress(),
                                targetAddress.getText().toString(),
                                Long.valueOf(lastPrice.getText().toString())
                        )
                );
                break;
        }

        service.shutdown();

        startActivity(new Intent(this, ListBActivity.class));

    }
}
