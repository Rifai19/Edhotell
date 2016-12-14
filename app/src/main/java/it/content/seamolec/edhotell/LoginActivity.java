package it.content.seamolec.edhotell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import it.content.seamolec.edhotell.config.ConfigUmum;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button btnLogin,btnRegister;
    ProgressDialog progressDialog;
    private boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText)findViewById(R.id.txtEmail);
        password = (EditText)findViewById(R.id.txtPassword);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    // cek dari db
                    login();

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(ConfigUmum.LOGGEDIN_SHARED_PREF, false);

        if (loggedIn) {
            Intent intent = new Intent(LoginActivity.this, ListHotelActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void login() {
        progressDialog.show();
        final String txtEmail = email.getText().toString().trim();
        final String txtPassword = password.getText().toString().trim();


//        Toast.makeText(Login.this, "hai: "+nisA +" "+passwordA,Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,ConfigUmum.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if (response.equalsIgnoreCase(ConfigUmum.LOGIN_SUCCESS)) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(ConfigUmum.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(ConfigUmum.NIS_SHARED_PREF, txtEmail);

                            editor.commit();

                            Intent i = new Intent(LoginActivity.this, SearchActivity.class);
                            startActivity(i);
                            progressDialog.dismiss();
                        } else {

                            Toast.makeText(LoginActivity.this, "username/password salah /masalah koneksi ke server", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("aaa", error.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(ConfigUmum.KEY_EMAIL, txtEmail);
                params.put(ConfigUmum.KEY_PASSWORD, txtPassword);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
