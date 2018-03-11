package com.mocktion.mocktion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocktion.mocktion.dao.AddressDao;
import com.mocktion.mocktion.task.ExhibitTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NewAActivity extends AppCompatActivity implements View.OnClickListener {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_a);

        CheckBox checkBox = (CheckBox) findViewById(R.id.is_reverse);
        checkBox.setChecked(false);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                Toast.makeText(NewAActivity.this,
                        "onClick():" + String.valueOf(checked),
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.exhibit).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.exhibit:
                EditText articleName = (EditText) findViewById(R.id.article_name);
                EditText basePrice = (EditText) findViewById(R.id.base_price);
                CheckBox checkBox = (CheckBox) findViewById(R.id.is_reverse);

                ExecutorService service = Executors.newFixedThreadPool(1);
                Future<String> future = service.submit(
                        new ExhibitTask(
                                AddressDao.getInstance().getAddress(),
                                articleName.getText().toString(),
                                Long.valueOf(basePrice.getText().toString()),
                                checkBox.isChecked()));
                try {
                    String result = future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                service.shutdown();

                break;
        }

        Intent intent = new Intent(this.getApplicationContext(), BoardAActivity.class);
            startActivity(intent);
    }

}
