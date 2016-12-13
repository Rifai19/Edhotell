package it.content.seamolec.edhotell.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;


import com.squareup.picasso.Picasso;

import it.content.seamolec.edhotell.DetailHotelActivity;
import it.content.seamolec.edhotell.R;
import it.content.seamolec.edhotell.config.ConfigUmum;
import it.content.seamolec.edhotell.holder.ItemObject;
import it.content.seamolec.edhotell.holder.MainHolder;


public class MainAdapter extends RecyclerView.Adapter<MainHolder> {

    ProgressDialog progressDialog;



    public List<ItemObject.ObjectHotel.Results> resultsList;
    public Context context;

    public MainAdapter(Context context, List<ItemObject.ObjectHotel.Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null);
        MainHolder mainHolder = new MainHolder(view);
        return mainHolder;
    }




    public void DeleteData(String Url) {

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {;
                    @Override
                    public void onResponse(String response) {
                        Log.d("uye", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("uye", error.toString());

            }
        });
        queue.add(stringRequest);
    }


    @Override
    public void onBindViewHolder(MainHolder holder, final int position) {
        holder.txtHotel.setText(resultsList.get(position).nama_hotel);
        holder.txtAlamat.setText(resultsList.get(position).alamat);
        holder.txtPhone.setText(resultsList.get(position).phone);
        holder.txtHarga.setText(resultsList.get(position).harga);
        Picasso.with(context).load("http://edotel.projectaplikasi.web.id/images/hotel/"+resultsList.get(position).foto).into(holder.imgFlag);
        //System.out.println("http://edotel.projectaplikasi.web.id/images/hotel/"+resultsList.get(position).foto);

        System.out.println(resultsList.get(position).foto);


        holder.cardview_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent i =  new Intent(context, DetailHotelActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", resultsList.get(position).id_hotel);
                context.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.resultsList.size();
    }
}
