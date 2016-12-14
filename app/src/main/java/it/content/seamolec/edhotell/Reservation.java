package it.content.seamolec.edhotell;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import it.content.seamolec.edhotell.config.ConfigUmum;

public class Reservation extends AppCompatActivity {

    public static final String KEY_ROOM = "txtIdRoom";
    public static final String KEY_EMAIL= "txtEmail";
    public static final String KEY_CHECKIN = "txtCheckin";
    public static final String KEY_CHECKOUT = "txtCheckout";
    public static final String KEY_JML_ROOM = "txtJmlRoom";
    public static final String KEY_HARGA = "txtHarga";

    ProgressDialog progressDialog;
    String id_kamar;
    TextView judul, alamat,phone,room,detil_room,harga,total;
    ImageView poster,imgRoom;
    EditText checkin,checkout;
    Spinner optRoom,optDay;
    SharedPreferences sharedPreferences;
    String email;

    Button btnOrder;

    Integer hargaKamar, hargaTotal, totalKamar, totalHari;

    private SimpleDateFormat dateFormatter;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        sharedPreferences = getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(ConfigUmum.NIS_SHARED_PREF, "tidak tersedia");

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
        total = (TextView)findViewById(R.id.txtTotal);

        btnOrder = (Button)findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });

        checkin = (EditText)findViewById(R.id.txtCheckin);
//        checkout = (EditText)findViewById(R.id.txtCheckout);

        optRoom = (Spinner)findViewById(R.id.optGuest);
        optRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hargaKamar = Integer.parseInt(harga.getText().toString());
                totalKamar = Integer.parseInt(optRoom.getSelectedItem().toString());
                totalHari = Integer.parseInt(optDay.getSelectedItem().toString());
                hargaTotal = hargaKamar*totalKamar*totalHari;
                total.setText(""+hargaTotal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hargaKamar = Integer.parseInt(harga.getText().toString());
                totalKamar = Integer.parseInt(optRoom.getSelectedItem().toString());
                totalHari = Integer.parseInt(optDay.getSelectedItem().toString());
                hargaTotal = hargaKamar*totalKamar*totalHari;
                total.setText(""+hargaTotal);
            }
        });

        optDay = (Spinner)findViewById(R.id.optDay);
        optDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hargaKamar = Integer.parseInt(harga.getText().toString());
                totalKamar = Integer.parseInt(optRoom.getSelectedItem().toString());
                totalHari = Integer.parseInt(optDay.getSelectedItem().toString());
                hargaTotal = hargaKamar*totalKamar*totalHari;
                total.setText(""+hargaTotal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hargaKamar = Integer.parseInt(harga.getText().toString());
                totalKamar = Integer.parseInt(optRoom.getSelectedItem().toString());
                totalHari = Integer.parseInt(optDay.getSelectedItem().toString());
                hargaTotal = hargaKamar*totalKamar*totalHari;
                total.setText(""+hargaTotal);
            }
        });


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
//        checkout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final Calendar c = Calendar.getInstance();
//                mYear  = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(Reservation.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                checkout.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });

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
                    total.setText(""+result.get("harga"));
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


    private void Save() {
        progressDialog.show();
        final String txtEmail = email.toString().trim();
        final String txtIdRoom = id_kamar.toString().trim();
        final String txtCheckin = checkin.getText().toString().trim();
        final String txtCheckout = optDay.getSelectedItem().toString().trim();
        final String txtJmlRoom = optRoom.getSelectedItem().toString().trim();
        final String txtHarga = total.getText().toString().trim();



        //parsing id kelas
//            final String sIdKelas = getIdKelas(ambilIDKelas);
        //final String sIdKelas = "100000";
        //final int saveIdKelas = Integer.parseInt(sIdKelas);

        StringRequest sR = new StringRequest(Request.Method.POST, ConfigUmum.RESERVASI_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();


                        progressDialog.dismiss();
                        if (response.equalsIgnoreCase("Gagal") || response.equalsIgnoreCase("Not Found User, Please Login First !")) {
//                        signinhere.setText(response);
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), InvoiceActivity.class);
                            i.putExtra("id", response);
                            startActivity(i);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_EMAIL, txtEmail);
                params.put(KEY_ROOM, txtIdRoom);
                params.put(KEY_CHECKIN, txtCheckin);
                params.put(KEY_CHECKOUT, txtCheckout);
                params.put(KEY_JML_ROOM, txtJmlRoom);
                params.put(KEY_HARGA, txtHarga);

                return params;
            }

        };
//        Toast.makeText(getApplicationContext(), txt_email + " makanan = " + makanan, Toast.LENGTH_LONG).show();
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        sR.setRetryPolicy(policy);
        requestQueue.add(sR);
    }

}
