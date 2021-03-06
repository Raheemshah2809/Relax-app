package com.fluorescentideas.relax;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    Context context;
    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }


    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {

        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String cityID = "";

                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toasty.success(context, "City ID Found" + cityID,
                        Toasty.LENGTH_LONG).show();





                //Toast.makeText(context, "city ID=" + cityID, Toast.LENGTH_SHORT).show();

                volleyResponseListener.onResponse(cityID);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toasty.error(context, "No Cities Found, Please Enter A CityID or a City Name" ,
                        Toasty.LENGTH_LONG).show();
                //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                //volleyResponseListener.onError("Error");
            }
        });


        MySingleton.getInstance(context).addToRequestQueue(request);
        //return cityID;

    }

    public interface ForeCastByIDResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel>weatherReportModels);
    }


    public void getCityForecastByID(String CityID, ForeCastByIDResponse foreCastByIDResponse) {


        List<WeatherReportModel> weatherReportModels = new ArrayList<>();



        String url = QUERY_FOR_CITY_WEATHER_BY_ID + CityID;
        //get the json object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray consolidated__weather_list = response.getJSONArray("consolidated_weather");




                    for(int i=0;i <consolidated__weather_list.length(); i++) {

                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject first_day_from_api = (JSONObject) consolidated__weather_list.get(i);

                        one_day_weather.setId(first_day_from_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                        one_day_weather.setCreated(first_day_from_api.getString("created"));
                        one_day_weather.setApplicable_date(first_day_from_api.getString("applicable_date"));
                        one_day_weather.setMin_temp((float) first_day_from_api.getLong("min_temp"));
                        one_day_weather.setMax_temp((float) first_day_from_api.getLong("max_temp"));
                        one_day_weather.setWind_speed((float) first_day_from_api.getLong("wind_speed"));
                        one_day_weather.setWind_direction((float) first_day_from_api.getLong("wind_direction"));
                        one_day_weather.setAir_pressure(first_day_from_api.getInt("air_pressure"));
                        one_day_weather.setHumidity(first_day_from_api.getInt("humidity"));
                        one_day_weather.setVisibility((float) first_day_from_api.getLong("visibility"));
                        one_day_weather.setPredictability(first_day_from_api.getInt("predictability"));
                        weatherReportModels.add(one_day_weather);

                    }
                    foreCastByIDResponse.onResponse(weatherReportModels);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

public interface GetCityForecastByNameCallback{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
}
    public void getCityForecastByName(String cityName, GetCityForecastByNameCallback getCityForecastByNameCallback){

        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {




            }

            @Override
            public void onResponse(String cityID) {
                getCityForecastByID(cityID, new ForeCastByIDResponse() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        getCityForecastByNameCallback.onResponse(weatherReportModels);

                    }
                });

            }
        });
    }



}
