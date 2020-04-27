package com.example.football_comp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class CountryActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> listItem = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String competitionName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        listView = (ListView) findViewById(R.id.listVIew);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
        listView.setAdapter(adapter);
        competitionName = getIntent().getStringExtra("id");
        Log.d("LABRA3","ID: "+ competitionName);




        JSONObject jsonbody = new JSONObject();
        String dataUrl = "https://api.football-data.org/v2/competitions?areas="+competitionName;
        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonObjectRequest footballRequest = new JsonObjectRequest(Request.Method.GET, dataUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("LABRA3",response.toString());
                JSONArray array = new JSONArray();

                try {
                    array = response.getJSONArray("competitions");
                    for(int i =0; i < array.length(); i++){
                        JSONObject comp = array.getJSONObject(i);
                        listItem.add(comp.getString("name"));
                        adapter.notifyDataSetChanged();

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




}