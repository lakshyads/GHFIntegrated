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
        Chairproducts.put("Premium Office Chair@200@office_chair@office_chair.sfb", R.drawable.brown_chair_1);
        Chairproducts.put("Seesham Wood Chair@180@wooden_chair@wooden_chair.sfb", R.drawable.wooden_chair);
        Chairproducts.put("Luxury Recliner Chair with Support@500@recliner_chair@recliner_chair.sfb", R.drawable.recliner_chair);
        Chairproducts.put("Royal Stool@120@stool@stool.sfb", R.drawable.stool);

        //Desks
        Deskproducts.put("Computer Desk@103@computer_desk@computer_desk.sfb", R.drawable.comp_desk);
        Deskproducts.put("Wooden Desk@130@wooden_desk@wooden_desk.sfb", R.drawable.wooden_desk);

        //Couch
        Couchproducts.put("Royal Padded Couch@499@padded_couch@padded_couch.sfb", R.drawable.padded_couch);
        Couchproducts.put("Home Style Sofa@800@padded_sofa@padded_sofa.sfb", R.drawable.padded_sofa);
        Couchproducts.put("EveryHomes Wide Sofa@999@wide_sofa@wide_sofa.sfb", R.drawable.wide_sofa);

        //Tables
        Tableproducts.put("Clarie Wooden Table@130@wooden_table@wooden_table.sfb", R.drawable.wooden_table);
        Tableproducts.put("Royal Pearl White Table@150@white_table@white_table.sfb", R.drawable.white_table);
        Tableproducts.put("Plain Office Table@120@plain_table@plain_table.sfb", R.drawable.plain_table);

        //Beds
        Bedproducts.put("Zorin Double Drawer Bed@1200@double_drawer_bed@double_drawer_bed.sfb", R.drawable.double_drawer_bed);
        Bedproducts.put("Columba Queen Bed@899@queenBed@queenBed.sfb", R.drawable.queen_size_bed);
        Bedproducts.put("Columba Single Bed@700@single_bed@single_bed.sfb", R.drawable.single_bed);

        //Others
        Othproducts.put("Hygenic Door Mat@20@doormat@doormat.sfb", R.drawable.doormat);
        Othproducts.put("Picture Perfect Frame@35@frame@frame.sfb", R.drawable.frame);
        Othproducts.put("Tall Bed Lamp@100@standing_lamp@standing_lamp.sfb", R.drawable.standinglamp);
        Othproducts.put("Premium Table Lamp@65@table_lamp@tablelamp.sfb", R.drawable.tablelamp);
        Othproducts.put("Vintage Wooden Table@80@vintagewoodtable@vintagewoodtable.sfb", R.drawable.vintagewoodtable);

        Allproducts.putAll(Chairproducts);
        Allproducts.putAll(Deskproducts);
        Allproducts.putAll(Couchproducts);
        Allproducts.putAll(Tableproducts);
        Allproducts.putAll(Bedproducts);
        Allproducts.putAll(Othproducts);

    }
}
