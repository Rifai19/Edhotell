package it.content.seamolec.edhotell.config;


public class ConfigUmum {

    public static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";



    public static final String IP="http://edotel.projectaplikasi.web.id/api/";
    public static final String ROOT="http://edotel.projectaplikasi.web.id/";

    public static String URL_GET_LIST= IP+"get_negara.php";
    public static String URL_GET_DETIL= IP+"get_detail_hotel.php?id=";
    public static String URL_GET_DETIL_ORDER= IP+"get_detail_order.php?id=";
    public static String URL_GET_LIST_ROOM= IP+"get_list_room.php?id=";
    public static String URL_GET_HOTEL_BY_CITY= IP+"search_hotel.php?id=";


    public static String GAMBAR_HOTEL_URL= ROOT+"images/hotel/";
    public static String LOGIN_URL= IP+"login.php";
    public static String REGISTER_URL= IP+"register.php";
    public static String GET_CITY_URL= IP+"get_city.php";
    public static String GET_ALL_HOTEL_URL= IP+"get_all_hotel.php";


    public static final String KEY_EMAIL = "txtEmail";
    public static final String KEY_PASSWORD = "txtPassword";
    public static final String LOGIN_SUCCESS = "sukses";
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String AMBIL_NAMA = "nama";
    public static final String NIS_SHARED_PREF = "email";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String ID_KELAS = "id_kelas";
}
