package it.content.seamolec.edhotell.adapter;

import android.app.Application;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import it.content.seamolec.edhotell.DetailHotelActivity;
import it.content.seamolec.edhotell.R;

import it.content.seamolec.edhotell.Reservation;
import it.content.seamolec.edhotell.holder.ItemRoom;
import it.content.seamolec.edhotell.holder.DetailHotelHolder;

/**
 * Created by Muhamad Muslim Rifai on 12/12/2016.
 */

public class AdapterDetilHotel extends RecyclerView.Adapter<DetailHotelHolder> {

    ProgressDialog progressDialog;



    public List<ItemRoom.ObjectRoom.Results> resultsList;
    public Context context;

    public AdapterDetilHotel(Context context, List<ItemRoom.ObjectRoom.Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

//    public AdapterDetilHotel(Context context, List<ItemRoom.ObjectRoom.Results> resultsList) {
//        this.context = context;
//        this.resultsList = resultsList;
//    }



//    public AdapterDetilHotel(Context context, List<ItemRoom.ObjectRoom.Results> resultsList) {
//        this.context = context;
//        this.resultsList = resultsList;
//    }



    @Override
    public DetailHotelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, null);
        DetailHotelHolder mainHolder = new DetailHotelHolder(view);
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
    public void onBindViewHolder(DetailHotelHolder holder, final int position) {
        holder.txtRoom.setText(resultsList.get(position).nama_room);
        holder.txtDetail.setText(resultsList.get(position).detail);

        holder.txtHarga.setText(resultsList.get(position).harga);
        Picasso.with(context).load("http://edotel.projectaplikasi.web.id/images/hotel/"+resultsList.get(position).foto).into(holder.imgRoom);
        //System.out.println("http://edotel.projectaplikasi.web.id/images/hotel/"+resultsList.get(position).foto);

        System.out.println(resultsList.get(position).foto);


        holder.cardview_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent i =  new Intent(context, Reservation.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", resultsList.get(position).id_room);
                context.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.resultsList.size();
    }
}
