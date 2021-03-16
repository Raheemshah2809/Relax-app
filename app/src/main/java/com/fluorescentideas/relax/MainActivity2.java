package com.fluorescentideas.relax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity2 extends AppCompatActivity {

    Button button;
    Button btn_getCityID, btn_getWeatherByCityID, btn_getWeatherByCityName;
    EditText et_dataInput;
    ListView lv_weatherReports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Button button = (Button) findViewById(R.id.homepage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();

            }

            public void openMainActivity() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity2.this);
        et_dataInput = findViewById(R.id.et_dataInput);
        btn_getCityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByCityID = findViewById(R.id.btn_getWeatherByCityID);
        btn_getWeatherByCityName = findViewById(R.id.btn_getWeatherByCityName);
        lv_weatherReports = findViewById(R.id.lv_weatherReports);



        btn_getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toasty.error(MainActivity2.this, "Please Enter City",Toasty.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity2.this, "No Cities Found, Please Enter City", Toast.LENGTH_SHORT).show(); Original Toast
                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toasty.success(MainActivity2.this,"Returned ID" +(et_dataInput.getText().toString()),Toasty.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity2.this, "Returned ID " + cityID, Toast.LENGTH_SHORT).show(); Original Toast
                    }
                });


            }

        });

        btn_getWeatherByCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataService.getCityForecastByID(et_dataInput.getText().toString(), new WeatherDataService.ForeCastByIDResponse() {
                    @Override
                    public void onError(String message) {

                        Toasty.error(MainActivity2.this, "Wrong City Id , Please Try Again"+ et_dataInput.getText().toString()
                                ,Toasty.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity2.this, "No Cities Found Error, Please try again", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {

                        Toasty.success(MainActivity2.this,"Returned Weather" + et_dataInput.getText().toString(),Toasty.LENGTH_LONG).show();


                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity2.this, android.R.layout.simple_list_item_1,weatherReportModels);
                        lv_weatherReports.setAdapter(arrayAdapter);


                    }
                });





            }

        });

        btn_getWeatherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityForecastByName(et_dataInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toasty.error(MainActivity2.this, "No Cities Found, Please Enter City" + et_dataInput.getText().toString(),
                                Toasty.LENGTH_LONG).show();
                        //Toast.makeText(MainActivity2.this, "No Cities Found Error, Please try again", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {

                        Toasty.success(MainActivity2.this,"Returned Weather" + et_dataInput.getText().toString(),Toasty.LENGTH_LONG).show();


                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity2.this, android.R.layout.simple_list_item_1,weatherReportModels);
                        lv_weatherReports.setAdapter(arrayAdapter);


                    }
                });
            }

        });


    }
}









                    // below here is the old code i was testing with, and above is the code i refactoerd and cleaned up, this is for demostartion purposes only.


                        //Instantiate the RequestQueue.
                       // RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);





//                Instantiate the RequestQueue.
//               RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
//                String url ="https://www.metaweather.com/api/location/search/?query=london";
//
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(MainActivity2.this,response, Toast.LENGTH_SHORT).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity2.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                // Add the request to the RequestQueue.



                //Toast.makeText(MainActivity2.this, "you pressed me 1", Toast.LENGTH_SHORT).show();
