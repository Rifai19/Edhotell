package it.content.seamolec.edhotell.holder;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ItemObject {
    public class ObjectHotel {
        @SerializedName("result")
        public List<Results> result;

        public class Results {
            @SerializedName("id_hotel")
            public String id_hotel;

            @SerializedName("nama_hotel")
            public String nama_hotel;

            @SerializedName("alamat")
            public String alamat;

            @SerializedName("phone")
            public String phone;

            @SerializedName("foto")
            public String foto;

            @SerializedName("harga")
            public String harga;
        }
    }
}
