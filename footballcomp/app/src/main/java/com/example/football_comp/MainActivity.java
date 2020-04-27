package com.example.football_comp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    ArrayList<League> listItem = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayAdapter<League> adapter;
    ArrayAdapter<String> adapter1;
    String competitionName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.dataButton).setOnClickListener(this);
        listView = (ListView)findViewById(R.id.listVIew);
        adapter = new ArrayAdapter<League>(this, android.R.layout.simple_list_item_1,listItem);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nameList);
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String comptetion = listItem.get(position).getId();
                Intent intent = new Intent(MainActivity.this, CountryActivity.class);
                intent.putExtra("id",comptetion);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.dataButton){
            JSONObject jsonbody = new JSONObject();
            String dataUrl = "https://api.football-data.org/v2/areas";
            RequestQueue queue = Volley.newRequestQueue(this);
            final JsonObjectRequest footballRequest = new JsonObjectRequest(Request.Method.GET, dataUrl,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("LABRA3",response.toString());
                    JSONArray array = new JSONArray();

                    try {
                        array = response.getJSONArray("areas");
                        for(int i =0; i < array.length(); i++){
                            JSONObject comp = array.getJSONObject(i);
                            League compe = new League();
                            compe.setId(comp.getString("id"));
                            compe.setName(comp.getString("name"));

                            listItem.add(compe);
                            nameList.add(compe.getName());
                            adapter1.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //listItem.add(response);
                    //adapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.response", error.toString());
                }
            });
            queue.add(footballRequest);
        }
        //if(v.getId()==R.id.listVIew){

        // }
    }
}
