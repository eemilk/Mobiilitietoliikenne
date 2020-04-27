package com.example.stock_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    ArrayList<String> listItem = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ProgressDialog pd;
    EditText editTextStockName,editTextstockID;
    String stockID = "";
    String stockName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextstockID = findViewById(R.id.textEditStockID);
        editTextStockName = findViewById(R.id.textEditStockName);
        findViewById(R.id.buttonAdd).setOnClickListener(this);

        listView = (ListView)findViewById(R.id.stockListView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItem);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        stockName = editTextStockName.getText().toString();
        stockID = editTextstockID.getText().toString();
        if(stockName != null && stockName.length()>0 && stockID != null && stockID.length()>0) {
            String Url = "https://financialmodelingprep.com/api/company/price/" + stockID + "?datatype=json";
            new JsonTask().execute(Url);
            adapter.notifyDataSetChanged();
            editTextstockID.setText("");
            editTextStockName.setText("");
        }
    }

    private  class JsonTask extends AsyncTask<String, String, String>{

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null) {
                    buffer.append(line );
                    publishProgress(line.toString());
                    Log.d("Response : ", "> " + line);
                }
                return buffer.toString();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            Log.d("Testi", result);
            try{
                JSONObject jObj = new JSONObject(result);
                String stockPrice = jObj.getString(stockID);
                Log.d("Testi2", stockPrice);
                JSONObject subObj = jObj.getJSONObject(stockID);
                String price = subObj.getString("price");
                Log.d("Testi3", price);
                listItem.add(stockName+ ": " +price);
                String koko = Integer.toString(listItem.size());

                Log.d("testi4",koko);

                adapter.notifyDataSetChanged();
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
    }
}