package com.questpirates.greathomesfurniture;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.questpirates.greathomesfurniture.bottomNavFrags.AssistantFragment;
import com.questpirates.greathomesfurniture.bottomNavFrags.CartFragment;
import com.questpirates.greathomesfurniture.bottomNavFrags.HomeFragment;
import com.questpirates.greathomesfurniture.bottomNavFrags.ProfileFragment;
import com.questpirates.greathomesfurniture.bottomNavFrags.ScanFragment;
import com.questpirates.greathomesfurniture.myServices.SocketService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView actionTV;
    ActionBar actionBar;
    private Toolbar mToolbar;
    Fragment f;
    public static String ITEM_NAME = "";
    private static boolean SERVICE_FLAG = true;

    public static FragmentManager fragmentManager;

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 100;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment f;
        switch (item.getItemId()) {
//            case R.id.profile:
//                // Single menu item is selected do something
//                // Ex: launching new activity/screen or show alert message
//                Toast.makeText(MainActivity.this, "Menu is Selected. Notice Action Bar Name has Changed", Toast.LENGTH_SHORT).show();
//                actionTV.setText("My Profile");
//                //loadFragment(new ProfileFragment());
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        }

        if (!(getIntent().getStringExtra("fragment") == null)) {
            String z = getIntent().getStringExtra("fragment");
            switch (z.toLowerCase()) {
                case "cart":
                    loadFragment(new CartFragment());
                    break;
                case "profile":
                    loadFragment(new ProfileFragment());
                    break;
                case "chat":
                    loadFragment(new AssistantFragment());
                    break;
                case "home":
                    loadFragment(new HomeFragment());
                    break;
                case "homechairs":
                    loadFragment(new HomeFragment());
                    setItemValue("chairs");
                    break;
                case "homedesks":
                    loadFragment(new HomeFragment());
                    setItemValue("desks");
                    break;
                case "hometables":
                    loadFragment(new HomeFragment());
                    setItemValue("tables");
                    break;
                case "chatscreen":
                    loadFragment(new AssistantFragment());
                    break;
            }
        } else {
            loadFragment(new HomeFragment());
        }
        // Inflate your custom layout
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_action_bar, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        actionTV = (TextView) actionBarLayout.findViewById(R.id.header);

        // Set up your ActionBar
        actionBar = getSupportActionBar();
        //actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(actionBarLayout, layoutParams);
        actionTV.setText("Home.");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

        if (SERVICE_FLAG) {
            startService(new Intent(MainActivity.this, SocketService.class));
            SERVICE_FLAG = false;
        }


    }

    //FOR BOTTOM NAVIGATION BAR
    private boolean loadFragment(Fragment fragment) {
        fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    // FOR BOTTON NAVIGATION
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_frag_home:
                            //actionBar.setTitle("Home");
                            loadFragment(new HomeFragment());
                            actionTV.setText("Home.");
                            return true;

                        case R.id.nav_frag_scan:
                            //actionBar.setTitle("Notifications");
                            loadFragment(new ScanFragment());
                            actionTV.setText("Scan.");
                            return true;

                        case R.id.nav_frag_assist:
                            //actionBar.setTitle("My Account");
                            loadFragment(new AssistantFragment());
                            actionTV.setText("Assistant.");
                            return true;

                        case R.id.nav_frag_cart:
                            //actionBar.setTitle("Dashboard");
                            loadFragment(new CartFragment());
                            actionTV.setText("Shopping Cart.");
                            return true;

                        case R.id.nav_frag_prof:
                            //actionBar.setTitle("My Account");
                            loadFragment(new ProfileFragment());
                            actionTV.setText("Profile.");
                            return true;
                    }

                    return false;
                }
            };

    public void showAlertScan() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Select any from below").setTitle("How may i help you!?");
        builder.setCancelable(false)
                .setPositiveButton("Intelligent Vision", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Intelligent Vision", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Agumented Vision", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Agumented Vision", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSuccess(String msg) {
        msg = "Hello";
//        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
//        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_dialog, null, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        builder.setCancelable(true);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = dialogView.findViewById(R.id.buttonOk);
        TextView t = dialogView.findViewById(R.id.msg);
        t.setText(msg);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }

    private boolean checkAndRequestPermissions() {
        int permissionCameraAccess = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCameraAccess != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0 || grantResults == null) {
            /*If result is null*/
        } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Camera permission granted", Toast.LENGTH_LONG).show();
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(getApplicationContext(), "Camera Permission needed for this to work. Please Grant", Toast.LENGTH_LONG).show();
        }

    }//end onRequestPermissionsResult


    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(MainActivity.this, SocketService.class));
    }

    private void dosomething(Intent i) {
        String val = i.getStringExtra("fragment");
        switch (val) {
            case "cart":
                loadFragment(new CartFragment());
                break;
            case "profile":
                loadFragment(new ProfileFragment());
                break;
            case "chat":
                loadFragment(new AssistantFragment());
                break;
            case "home":
                loadFragment(new HomeFragment());
                break;
            case "homechairs":
                loadFragment(new HomeFragment());
                setItemValue("chairs");
                break;
            case "homedesks":
                loadFragment(new HomeFragment());
                setItemValue("desks");
                break;
            case "hometables":
                loadFragment(new HomeFragment());
                setItemValue("tables");
                break;

            case "chatscreen":
                loadFragment(new AssistantFragment());
                break;
        }
    }

    public void setItemValue(String chairs) {
        ITEM_NAME = chairs;
    }

    public String getItemValue() {
        return ITEM_NAME;
    }

    private BroadcastReceiver broadcastReceiverM = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dosomething(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiverM, new IntentFilter(SocketService.BROADCAST_MAIN));
        registerReceiver(broadcastReceiverM, new IntentFilter(SocketService.BROADCAST_MAINCHAIRS));
        registerReceiver(broadcastReceiverM, new IntentFilter(SocketService.BROADCAST_MAINDESKS));
    }
}
