package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText et_movieName;
    Button search;
    TextView tvYear, tvPlot, tvDirector, tvActors, tvCountry, tvLanguage;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_movieName = (EditText) findViewById(R.id.et_movieName);
        search = (Button) findViewById(R.id.searchButton);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvActors = (TextView) findViewById(R.id.tvActors);
        tvPlot = (TextView) findViewById(R.id.tvPlot);
        tvDirector = (TextView) findViewById(R.id.tvDirector);
        tvCountry = (TextView) findViewById(R.id.tvCountry);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);

        ivPoster = (ImageView) findViewById(R.id.ivPoster);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = et_movieName.getText().toString();

                if(mName.isEmpty()){
                    et_movieName.setError("Please provide a Movie name");
                    return;
                }

                String url = "https://www.omdbapi.com/?t=" + mName + "&plot=full&apikey=79b40b5e";

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject movie = new JSONObject(response);

                                    String result = movie.getString("Response");

                                    if(result.equals("True")){
                                        String year = movie.getString("Year");
                                        tvYear.setText("Year : " + year);

                                        String director = movie.getString("Director");
                                        tvDirector.setText("Director : " + director);

                                        String language = movie.getString("Language");
                                        tvLanguage.setText("Language : " + language);

                                        String actors = movie.getString("Actors");
                                        tvActors.setText("Actors : " + actors);

                                        String country = movie.getString("Country");
                                        tvCountry.setText("Country : " + country);

                                        String plot = movie.getString("Plot");
                                        tvPlot.setText("Plot : " + plot);

                                        String posterUrl = movie.getString("Poster");
                                        if(posterUrl.equals("N/A")){

                                        }
                                        else{
                                            Picasso.get().load(posterUrl).into(ivPoster);
                                        }

                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Movie Not Found.", Toast.LENGTH_SHORT).show();
                                    }


                                }catch (JSONException e){
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Movie Not Found", Toast.LENGTH_SHORT).show();
                            }
                        }


                );
                queue.add(request);
            }
        });


    }
}