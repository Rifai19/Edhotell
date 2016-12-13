package it.content.seamolec.edhotell.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.content.seamolec.edhotell.R;

public class MainHolder extends RecyclerView.ViewHolder {

    public TextView txtHotel, txtAlamat, txtPhone,txtHarga;
    public CardView cardview_item;
    public ImageView imgFlag;

    public MainHolder(View itemView) {
        super(itemView);
        txtHotel = (TextView) itemView.findViewById(R.id.txtNamaHotel);
        txtAlamat = (TextView) itemView.findViewById(R.id.txtAlamat);
        imgFlag = (ImageView)itemView.findViewById(R.id.imgHotel);
        txtPhone = (TextView)itemView.findViewById(R.id.txtPhone);
        txtHarga = (TextView)itemView.findViewById(R.id.txtHarga);

        cardview_item = (CardView) itemView.findViewById(R.id.cv_item);


    }
}
