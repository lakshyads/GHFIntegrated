package com.questpirates.greathomesfurniture.dataBlock;

import com.questpirates.greathomesfurniture.R;

import java.util.HashMap;
import java.util.Map;

public class SomeData {

    public static final Map<String, Integer> EMPTY = new HashMap<>();
    public Map<String, Integer> Allproducts = new HashMap<>();
    public Map<String, Integer> Chairproducts = new HashMap<>();
    public Map<String, Integer> Deskproducts = new HashMap<>();
    public Map<String, Integer> Couchproducts = new HashMap<>();
    public Map<String, Integer> Tableproducts = new HashMap<>();
    public Map<String, Integer> Bedproducts = new HashMap<>();
    public Map<String, Integer> Othproducts = new HashMap<>();

    public SomeData() {
        //Chairs
        Chairproducts.put("ChairName@ChairPrice@chair@chair.sfb", R.drawable.furnitures);
        Chairproducts.put("ChairName1@ChairPrice@chair@chair.sfb", R.drawable.camera);

        //Desks
        Deskproducts.put("DeskName@DeskPrice@desk@Desk.sfb", R.drawable.sendchat);
        Deskproducts.put("DeskName1@DeskPrice@desk@Desk.sfb", R.drawable.chatbot);

        //Couch
        Couchproducts.put("CouchName@CouchPrice@couch@couch.sfb", R.drawable.emptycart);
        Couchproducts.put("CouchName1@CouchPrice@couch@couch.sfb", R.drawable.vision);

        //Tables
        Tableproducts.put("TableName@TablePrice@table@table.sfb", R.drawable.profile);
        Tableproducts.put("TableName1@TablePrice@table@table.sfb", R.drawable.qr);

        //Beds
        Bedproducts.put("BedName@BedPrice@bed@bed.sfb", R.drawable.shoppingcart);
        Bedproducts.put("BedName1@BedPrice@bed@bed.sfb", R.drawable.ghflogo);

        //Others
        Othproducts.put("OtherName@OtherPrice@other@other.sfb", R.drawable.voicebot);
        Othproducts.put("OtherName1@OtherPrice@other@other.sfb", R.drawable.images);

        Allproducts.putAll(Chairproducts);
        Allproducts.putAll(Deskproducts);
        Allproducts.putAll(Couchproducts);
        Allproducts.putAll(Tableproducts);
        Allproducts.putAll(Bedproducts);
        Allproducts.putAll(Othproducts);

    }
}
