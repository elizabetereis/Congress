package com.example.elizabete.congress;

/**
 * Created by elizabete on 24/09/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CongressActivity extends Activity  {

    protected ListView listaCongressos;
    protected CongressValue congressValue;
    protected ArrayAdapter<CongressValue> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_congress);

        CongressDAO dao = new CongressDAO(this);

        listaCongressos = (ListView) findViewById(R.id.list_congresso);
        int layout = android.R.layout.simple_list_item_1;
        adapter = new ArrayAdapter<CongressValue>(this,layout, dao.getLista());
        listaCongressos.setAdapter(adapter);

    }

}
