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
        Chairproducts.put("Premium Office Chair@9999@office_chair@office_chair.sfb", R.drawable.brown_chair_1);
        Chairproducts.put("Seesham Wood Chair@4999@wooden_chair@wooden_chair.sfb", R.drawable.seesham_wood_chair);
        Chairproducts.put("Luxury Recliner Chair with Support@4999@recliner_chair@recliner_chair.sfb", R.drawable.seesham_wood_chair);
        Chairproducts.put("Royal Stool@4999@stool@stool.sfb", R.drawable.seesham_wood_chair);

        //Desks
        Deskproducts.put("Computer Desk@12000@computer_desk@computer_desk.sfb", R.drawable.comp_desk);
        Deskproducts.put("Wooden Desk@20000@wooden_desk@wooden_desk.sfb", R.drawable.wooden_desk);

        //Couch
        Couchproducts.put("Royal Padded Couch@18000@padded_couch@padded_couch.sfb", R.drawable.padded_couch);
        Couchproducts.put("Home Style Sofa@9999@padded_sofa@padded_sofa.sfb", R.drawable.padded_sofa);
        Couchproducts.put("EveryHomes Wide Sofa@9999@wide_sofa@wide_sofa.sfb", R.drawable.wide_sofa);

        //Tables
        Tableproducts.put("Clarie Wooden Table@28000@wooden_table@wooden_table.sfb", R.drawable.wooden_table);
        Tableproducts.put("Royal Pearl White Table@3500@white_table@white_table.sfb", R.drawable.white_table);
        Tableproducts.put("Plain Office Table@3500@plain_table@plain_table.sfb", R.drawable.plain_table);

        //Beds
        Bedproducts.put("Zorin Double Drawer Bed@15000@double_drawer_bed@double_drawer_bed.sfb", R.drawable.double_drawer_bed);
        Bedproducts.put("Columba King Bed@15000@plane_bed@plane_bed.sfb", R.drawable.queen_size_bed);
        Bedproducts.put("Columba Single Bed@15000@single_bed@single_bed.sfb", R.drawable.single_bed);

        //Others
        Othproducts.put("Hygenic Door Mat@1999@doormat@doormat.sfb", R.drawable.doormat);
        Othproducts.put("Picture Perfect Frame@1999@frame@frame.sfb", R.drawable.frame);
        Othproducts.put("Tall Bed Lamp@1999@standing_lamp@standing_lamp.sfb", R.drawable.standinglamp);
        Othproducts.put("Premium Table Lamp@1999@table_lamp@table_lamp.sfb", R.drawable.tablelamp);
        Othproducts.put("Vintage Wooden Table@1999@vintage_wooden_table@vintage_wooden_table.sfb", R.drawable.vintagewoodtable);

        Allproducts.putAll(Chairproducts);
        Allproducts.putAll(Deskproducts);
        Allproducts.putAll(Couchproducts);
        Allproducts.putAll(Tableproducts);
        Allproducts.putAll(Bedproducts);
        Allproducts.putAll(Othproducts);

    }
}
