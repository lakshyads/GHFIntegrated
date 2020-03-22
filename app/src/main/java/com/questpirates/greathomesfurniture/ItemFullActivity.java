package com.questpirates.greathomesfurniture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ItemFullActivity extends AppCompatActivity {

    TextView tvProdName, tvProdPrice;
    ImageView imgProd, imgAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_full);

        tvProdName = findViewById(R.id.prodname);
        tvProdPrice = findViewById(R.id.prodprice);
        imgProd = findViewById(R.id.prodimg);
        imgAR = findViewById(R.id.showinar);

        Intent intent = getIntent();
        HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("map");

        String prodName = (String) hashMap.get("prodName");
        String prodPrice = (String) hashMap.get("prodPrice");
        int prodImg = (int) hashMap.get("prodImg");
        String prodSFB = (String) hashMap.get("prodSFB");

        tvProdName.setText(prodName);
        tvProdPrice.setText("Rs." + prodPrice);
        imgProd.setImageResource(prodImg);

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
}
