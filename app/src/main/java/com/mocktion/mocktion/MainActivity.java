package com.mocktion.mocktion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mocktion.mocktion.constant.LocalConstants;
import com.mocktion.mocktion.dao.AddressDao;
import com.mocktion.mocktion.task.GetBalanceTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                LocalConstants.addressArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                AddressDao.getInstance().setAddress(item);

                ExecutorService service = Executors.newFixedThreadPool(1);
                Future<String> future = service.submit(new GetBalanceTask(item));
                String result = null;
                try {
                    result = future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(result != null){
                    TextView balance = findViewById(R.id.text_balance);
                    balance.setText((CharSequence) result);
                }

                service.shutdown();

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        findViewById(R.id.a_button).setOnClickListener(this);
        findViewById(R.id.b_button).setOnClickListener(this);
     }

    public void onClick(View view) {

        switch(view.getId()){
            case R.id.a_button:
                startActivity(new Intent(this, BoardAActivity.class));
                break;
            case R.id.b_button:
                startActivity(new Intent(this, ListBActivity.class));
                break;
        }
    }

}
