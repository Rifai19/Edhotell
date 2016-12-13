package it.content.seamolec.edhotell.holder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhamad Muslim Rifai on 12/12/2016.
 */

public class ItemRoom{
    public class ObjectRoom {
        @SerializedName("result")
        public List<ItemRoom.ObjectRoom.Results> result;

        public class Results {
            @SerializedName("id_room")
            public String id_room;

            @SerializedName("nama_room")
            public String nama_room;

            @SerializedName("detail")
            public String detail;

            @SerializedName("foto")
            public String foto;

            @SerializedName("harga")
            public String harga;
        }
    }
}