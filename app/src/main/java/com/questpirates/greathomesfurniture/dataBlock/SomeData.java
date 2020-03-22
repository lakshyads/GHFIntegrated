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
        Chairproducts.put("Premium Brown Chair@9999@chair@chair.sfb", R.drawable.brown_chair_1);
        Chairproducts.put("Seesham Wood Chair@4999@chair@chair.sfb", R.drawable.seesham_wood_chair);

        //Desks
        Deskproducts.put("Computer Desk@12000@desk@Desk.sfb", R.drawable.comp_desk);
        Deskproducts.put("Corner Desk@20000@desk@Desk.sfb", R.drawable.corner_desk);

        //Couch
        Couchproducts.put("Royal Gold Couch@18000@couch@couch.sfb", R.drawable.soft_couch);
        Couchproducts.put("Home Style Couch@9999@couch@couch.sfb", R.drawable.home_style_couch);

        //Tables
        Tableproducts.put("Clarie Coffee Table@28000@table@table.sfb", R.drawable.clarie_coffee_table);
        Tableproducts.put("Office Table@3500@table@table.sfb", R.drawable.office_table);

        //Beds
        Bedproducts.put("Zorin Home Bed@15000@bed@bed.sfb", R.drawable.zorin_bed);
        Bedproducts.put("Columba King Bed@15000@bed@bed.sfb", R.drawable.columba_king);

        //Others
        Othproducts.put("Book Shelf@OtherPrice@1999@other.sfb", R.drawable.book_shelf);
        Othproducts.put("Book Case Almirah@1999@other@other.sfb", R.drawable.book_case_almirah);

        Allproducts.putAll(Chairproducts);
        Allproducts.putAll(Deskproducts);
        Allproducts.putAll(Couchproducts);
        Allproducts.putAll(Tableproducts);
        Allproducts.putAll(Bedproducts);
        Allproducts.putAll(Othproducts);

    }
}
