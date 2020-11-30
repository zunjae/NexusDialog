package com.github.dkharrat.nexusdialog.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.examples_list);

        String[] items = {"Complex Form", "Custom Form", "Fragment Form"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch(position) {
                    case 0: {
                        intent = new Intent(MainActivity.this, ComplexForm.class);
                        break;
                    }
                    case 1: {
                        intent = new Intent(MainActivity.this, CustomFormActivity.class);
                        break;
                    }
                    case 2: {
                        intent = new Intent(MainActivity.this, FragmentFormActivity.class);
                        break;
                    }
                    default: {
                        throw new Error("Unhandled list item " + position);
                    }
                }
                startActivity(intent);
            }
        });
    }
}

