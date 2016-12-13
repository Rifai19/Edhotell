package it.content.seamolec.edhotell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import it.content.seamolec.edhotell.config.ConfigUmum;

public class RegisterActivity extends AppCompatActivity {

    public static final String KEY_NAMA = "txtNama";
    public static final String KEY_PHONE = "txtPhone";
    public static final String KEY_EMAIL= "txtEmail";
    public static final String KEY_PASS = "txtPassword";
    public static final String KEY_CONF = "txtConfirm";

    ProgressDialog progressDialog;
    EditText nama,phone,email,password,confirm;
    Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");


        email = (EditText)findViewById(R.id.txtEmail);
        password = (EditText)findViewById(R.id.txtPassword);
        confirm = (EditText)findViewById(R.id.txtConfirm);
        nama = (EditText)findViewById(R.id.txtNama);
        phone = (EditText)findViewById(R.id.txtPhone);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });
    }



    private void Save() {
        progressDialog.show();
        final String txtNama = nama.getText().toString().trim();
        final String txtPhone = phone.getText().toString().trim();
        final String txtEmail = email.getText().toString().trim();
        final String txtPassword = password.getText().toString().trim();
        final String txtConfirm = confirm.getText().toString().trim();



        //parsing id kelas
//            final String sIdKelas = getIdKelas(ambilIDKelas);
        //final String sIdKelas = "100000";
        //final int saveIdKelas = Integer.parseInt(sIdKelas);

        StringRequest sR = new StringRequest(Request.Method.POST, ConfigUmum.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                          Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();


                        progressDialog.dismiss();
                        if (response.equalsIgnoreCase(ConfigUmum.LOGIN_SUCCESS)) {
//                        signinhere.setText(response);
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
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
                params.put(KEY_NAMA, txtNama);
                params.put(KEY_PHONE, txtPhone);
                params.put(KEY_EMAIL, txtEmail);
                params.put(KEY_PASS, txtPassword);
                params.put(KEY_CONF, txtConfirm);

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
