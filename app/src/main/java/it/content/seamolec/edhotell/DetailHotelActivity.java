package it.content.seamolec.edhotell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import it.content.seamolec.edhotell.adapter.AdapterDetilHotel;
import it.content.seamolec.edhotell.adapter.MainAdapter;
import it.content.seamolec.edhotell.config.ConfigUmum;
import it.content.seamolec.edhotell.holder.ItemObject;
import it.content.seamolec.edhotell.holder.ItemRoom;

public class DetailHotelActivity extends AppCompatActivity {
    TextView judul, alamat,phone;
    String id_hotel;
    ImageView poster;
    ProgressDialog progressDialog;
    private ItemRoom.ObjectRoom objectR;
    private AdapterDetilHotel adapter;
    RecyclerView rv_room;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotel);
        Intent i = getIntent();
        id_hotel = i.getStringExtra ("id");

        poster = (ImageView)findViewById(R.id.imageView);
        judul = (TextView)findViewById(R.id.txtTitle);
        alamat = (TextView)findViewById(R.id.txtAlamat);
        phone = (TextView)findViewById(R.id.txtPhone);
        rv_room = (RecyclerView)findViewById(R.id.rv_room);
//        harga = (TextView)findViewById(R.id.txtPhone);

        layoutManager = new LinearLayoutManager(getApplication());
        rv_room.setHasFixedSize(true);
        rv_room.setLayoutManager(layoutManager);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        GetData();
        GetListData(ConfigUmum.URL_GET_LIST_ROOM+id_hotel);
        System.out.println(ConfigUmum.URL_GET_DETIL+id_hotel);
    }

    private void GetData(){
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final JsonObjectRequest request = new JsonObjectRequest( ConfigUmum.URL_GET_DETIL+id_hotel,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response"+response);
                try {
                    JSONObject result = response.getJSONObject("result");

                    judul.setText(""+result.get("nama_hotel"));
                    alamat.setText(""+result.get("alamat"));
                    phone.setText(""+result.get("phone"));
                    Picasso.with(getApplicationContext()).load("http://edotel.projectaplikasi.web.id/images/hotel/"+""+result.get("foto")).into(poster);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), "Masalah pada koneksi, atau data makan kurang lengkap", Toast.LENGTH_LONG).show();

            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    public void GetListData(String URL) {

        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            ;

            @Override
            public void onResponse(String response) {

                System.out.println(response);

                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                objectR = mGson.fromJson(response, ItemRoom.ObjectRoom.class);
                adapter = new AdapterDetilHotel(getApplication(), objectR.result);
                rv_room.setAdapter(adapter);

                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Konek ke server, periksa jaringan anda :(", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
        int socketTimeout = 5000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }
}