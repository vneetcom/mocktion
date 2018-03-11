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

public class BoardAActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_a);

        findViewById(R.id.new_button).setOnClickListener(this);
        findViewById(R.id.view_button).setOnClickListener(this);
        findViewById(R.id.cancel_button).setOnClickListener(this);
        findViewById(R.id.back_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.new_button:
                startActivity(new Intent(this, NewAActivity.class));
                break;
            case R.id.view_button:
                startActivity(new Intent(this, DetailsAActivity.class));
                break;
            case R.id.cancel_button:
                startActivity(new Intent(this, CancelAActivity.class));
                break;
            case R.id.back_button:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}
