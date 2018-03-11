package com.mocktion.mocktion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.mocktion.mocktion.constant.LocalConstants;
import com.mocktion.mocktion.dao.AddressDao;

import java.util.ArrayList;
import java.util.List;

public class ListBActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private String[] goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_b);

        String myAddress = AddressDao.getInstance().getAddress();
        List<String> addressList = new ArrayList<String>();
        for(String item: LocalConstants.addressList){
            if(myAddress.equals(item)) {
                continue;
            }
            addressList.add(item);
        }
        goods = addressList.toArray(new String[addressList.size()]);

        ListView listView = findViewById(R.id.list_view);

        BaseAdapter adapter = new ListViewAdapter(
                this.getApplicationContext(),
                R.layout.list,
                goods);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        listView.setItemsCanFocus(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        Intent intent = new Intent(
                this.getApplicationContext(), DetailsBActivity.class);

        String selectedText = goods[position];
        intent.putExtra("targetAddress", selectedText);

        startActivity(intent);
    }
}
