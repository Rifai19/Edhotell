package it.content.seamolec.edhotell.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.content.seamolec.edhotell.R;

/**
 * Created by Muhamad Muslim Rifai on 12/12/2016.
 */

public class DetailHotelHolder extends RecyclerView.ViewHolder {

    public TextView txtRoom, txtDetail,txtHarga;
    public CardView cardview_item;
    public ImageView imgRoom;

    public DetailHotelHolder(View itemView) {
        super(itemView);
        txtRoom = (TextView) itemView.findViewById(R.id.txtRoom);
        txtDetail = (TextView) itemView.findViewById(R.id.txtDeskripsi);
        imgRoom = (ImageView)itemView.findViewById(R.id.imgRoom);
        txtHarga = (TextView)itemView.findViewById(R.id.txtHarga);

        cardview_item = (CardView) itemView.findViewById(R.id.cv_item);


    }
}