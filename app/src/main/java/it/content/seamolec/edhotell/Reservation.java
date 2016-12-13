package it.content.seamolec.edhotell;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import it.content.seamolec.edhotell.config.ConfigUmum;

public class Reservation extends AppCompatActivity {
    ProgressDialog progressDialog;
    String id_kamar;
    TextView judul, alamat,phone,room,detil_room,harga;
    ImageView poster,imgRoom;
    EditText checkin,checkout;

    private SimpleDateFormat dateFormatter;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent i = getIntent();
        id_kamar = i.getStringExtra ("id");

        poster = (ImageView)findViewById(R.id.imageView);
        imgRoom = (ImageView)findViewById(R.id.imgRoom);
        judul = (TextView)findViewById(R.id.txtTitle);
        alamat = (TextView)findViewById(R.id.txtAlamat);
        harga = (TextView)findViewById(R.id.txtHarga);
        phone = (TextView)findViewById(R.id.txtPhone);
        room = (TextView)findViewById(R.id.txtRoom);
        detil_room = (TextView)findViewById(R.id.txtDeskripsi);

        checkin = (EditText)findViewById(R.id.txtCheckin);
        checkout = (EditText)findViewById(R.id.txtCheckout);


        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear  = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Reservation.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                checkin.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear  = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Reservation.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                checkout.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        GetData();
        System.out.println(ConfigUmum.URL_GET_DETIL_ORDER+id_kamar);
    }

    private void GetData(){
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final JsonObjectRequest request = new JsonObjectRequest( ConfigUmum.URL_GET_DETIL_ORDER+id_kamar,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response"+response);
                try {
                    JSONObject result = response.getJSONObject("result");
                    System.out.println(response);
                    judul.setText(""+result.get("nama_hotel"));
                    alamat.setText(""+result.get("alamat"));
                    phone.setText(""+result.get("phone"));
                    room.setText(""+result.get("nama_kamar"));
                    detil_room.setText(""+result.get("detail_kamar"));
                    harga.setText(""+result.get("harga"));
                    Picasso.with(getApplicationContext()).load("http://edotel.projectaplikasi.web.id/images/hotel/"+""+result.get("foto_hotel")).into(poster);
                    Picasso.with(getApplicationContext()).load("http://edotel.projectaplikasi.web.id/images/hotel/"+""+result.get("foto_kamar")).into(imgRoom);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), "Masalah pada koneksi", Toast.LENGTH_LONG).show();

            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }
}
