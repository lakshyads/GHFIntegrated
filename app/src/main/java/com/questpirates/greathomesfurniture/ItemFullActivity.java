package com.questpirates.greathomesfurniture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.nkzawa.socketio.client.Socket;
import com.questpirates.greathomesfurniture.pojo.SocketPojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemFullActivity extends AppCompatActivity {

    TextView tvProdName, tvProdPrice, tvMRP, proddetails, warrentydetails;
    ImageView imgProd, imgAR;
    String prodData, warData, prodDataBackend, warDatabackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_full_new);
        ContextPojo.setContext(getApplicationContext());



        tvProdName = findViewById(R.id.prodname);
        tvProdPrice = findViewById(R.id.ourprodprice);
        tvMRP = findViewById(R.id.mrpprodprice);
        imgProd = findViewById(R.id.prodimg);
        imgAR = findViewById(R.id.showinar);
        proddetails = findViewById(R.id.proddetails);
        warrentydetails = findViewById(R.id.warrentyDetails);

        Intent intent = getIntent();
        HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("map");

        String prodName = (String) hashMap.get("prodName");
        String prodPrice = (String) hashMap.get("prodPrice");
        int prodImg = (int) hashMap.get("prodImg");
        String prodSFB = (String) hashMap.get("prodSFB");

        int indexRand = new Random().nextInt(7 - 0) + 0;
        ;

        prodData = getRandomProductInfo(indexRand).replaceAll("@","");
        warData = getRandomWarrentyInfo(indexRand);
        prodDataBackend = getRandomProductInfo(indexRand).split("@")[0] + "You can ask me to show this in ar to visualize products in your home.";

        Log.d("INFO", "prodData: " + prodData);
        Log.d("INFO", "prodDataBackend: " + prodDataBackend);

        JSONObject res = ItemFullActivity.getCommonJSON(prodDataBackend, warData, prodPrice);
        Socket socket = SocketPojo.getSocket();
        socket.emit("Product Info", res);
        Log.d("JSON", res.toString());


        // SocketQuery.setQUERY("WARRENTY DATA");

        int price = Integer.parseInt(prodPrice);
        double mrp = Integer.parseInt(prodPrice) + 20;
        if(price<100){
            mrp = 99.99;
        }else if (price >=100 && price <200){
            mrp = 199.99;
        }else if (price >=200 && price <300){
            mrp = 299.99;
        }else if (price >=300 && price <400){
            mrp = 399.99;
        }else if (price >=400 && price <500){
            mrp = 499.99;
        }else if (price >=500 && price <700){
            mrp = 699.99;
        }else if (price >=700 && price <800){
            mrp = 799.99;
        }else if (price >=800 && price <1000){
            mrp = 1999.99;
        }

        tvProdName.setText(prodName);
        tvMRP.setText((Html.fromHtml("<strike> $ " + mrp + "</strike>")));

        tvProdPrice.setText("$ " + prodPrice);
        imgProd.setImageResource(prodImg);
        proddetails.setText(prodData);
        warrentydetails.setText(warData);

        findViewById(R.id.showinartext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemFullActivity.this, ARMainActivity.class);
                //i.putExtra("image", valSFB);
                i.setData(Uri.parse(prodSFB));
                startActivity(i);
            }
        });
        findViewById(R.id.showinarlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemFullActivity.this, ARMainActivity.class);
                //i.putExtra("image", valSFB);
                i.setData(Uri.parse(prodSFB));
                startActivity(i);
            }
        });

        imgAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemFullActivity.this, ARMainActivity.class);
                //i.putExtra("image", valSFB);
                i.setData(Uri.parse(prodSFB));
                startActivity(i);
            }
        });


    }

    public static JSONObject getCommonJSON(String sprodData, String swarData, String sprice) {
        JSONObject res = null;
        String op = "{" +
                "\"commondata\":" +
                "{" +
                "\"prodDesc\":\"" + sprodData + "\"," +
                "\"warData\":\"" + swarData + "\"," +
                "\"price\":\"" + sprice + "\"" +
                "}" +
                "}";
        Log.d("JSONSTRING", "getCommonJSON: " + op);
        try {
            res = new JSONObject(op);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getRandomProductInfo(int index) {
        List<String> prodInfo = new ArrayList<>();
        prodInfo.add("This Product is made of premium quality materials and comes in multiple color options too. @ You can ask take me to ar to visualize products in your home.");
        prodInfo.add("This Product is a classic amalgamation of form and function. Crafted for compact homes, @ the range is clean and convenient and has an understated design aesthetic that adapts to any space.");
        prodInfo.add("It offers the best in comfort with elan. The collections are a series of modern trendy designs, simple yet striking and represent the ideals of minimalism. @The designs are a perfect blend of functionality and exceptional aesthetics. Each piece is crafted with passion and follows international standards on quality and style.");
        prodInfo.add("This is by one of Indias leading manufacturers of furniture with a global presence. It stands for its innovative design, quality products and affordable prices, @ a combination that is almost impossible to find.");
        prodInfo.add("This is Ideal for anchoring your modern space, this product is crafted with lush and glam look.The channel details on the arms and accent lend classic sophistication to your space, @ while the tonal piping on the edges of the product give it a tailored touch.");
        prodInfo.add("Love It? The designs are a perfect blend of functionality and exceptional aesthetics. @ Each piece is crafted with passion and follows international standards on quality and style.This product has a birch wood frame that offers sturdy support and durability.");
        prodInfo.add("A gorgeous premium finish, a sleek design, and easy assembly; what more could you want? @The Platform product Frame is the perfect statement piece to finish any place.Crafted to create an elegant look that matches perfectly with a classic tufted look.");
        prodInfo.add("Included slats easily  fit into space to create even, lasting dashing look that eliminates the need for a box spring. @ Durable and classy finish the design for a versatile look that will go well with most styles and preferences.Everything you need, except a screwdriver, to quickly and easily assemble this and included in a compartment on the back . Ships quickly and easily in one box, right to your front door. Choose one of our four neutral colors and finish your house in style.");





        return prodInfo.get(index);
    }

    public String getRandomWarrentyInfo(int index) {
        List<String> warInfo = new ArrayList<>();
        warInfo.add("The product comes with a 12 Months warranty against any manufacturing defect under normal household conditions.");
        warInfo.add("The product comes with a 6 Months warranty.");
        warInfo.add("There is no warranty covered for this product");
        warInfo.add("The warranty, as mentioned, on the product is provided by the Merchant for 18 month period");
        warInfo.add("The product comes with a minimum of 3 Months warranty under normal household conditions.");
        warInfo.add("The product comes with a 18 Months warranty.");
        warInfo.add("There is no warranty covered for this product");
        warInfo.add("The warranty, as mentioned, on the product is provided by the Merchant for 12 month period");

        return warInfo.get(index);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishActivity(0);
    }
}
