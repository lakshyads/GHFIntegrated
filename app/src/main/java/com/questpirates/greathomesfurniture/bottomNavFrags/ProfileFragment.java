package com.questpirates.greathomesfurniture.bottomNavFrags;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.questpirates.greathomesfurniture.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileFragment extends Fragment {

    private ImageView profilepic;
    ListView mainListView;
    private ArrayAdapter<String> listAdapter ;
    private Button btn;
    TextView name,email;
    String[] planets;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View v = inflater.inflate(R.layout.fragment_profile, null);

        mainListView = v.findViewById( R.id.mainlistview );
        name = v.findViewById(R.id.pname);
        email = v.findViewById(R.id.psubtitle);
        btn = v.findViewById(R.id.signout);
        profilepic = v.findViewById(R.id.pp);

        // Create and populate a List of planet names.
        planets = new String[] { "About Application", "Payment Options","FAQ","Developer Info", "© Copyrights & Privacy Policy",
                "Contact Us", "Made with ❤ in India"};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(planets) );


        listAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,planetList);
        // Add more planets. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0 :
                        //About Application
                       //showDialog(R.layout.profile_about_application_dialog);
                        Toast.makeText(getContext(),"Coming Soon \nYou Clicked on " + planets[position], Toast.LENGTH_LONG).show();
                        break;
                    case 1 :
                        // Payment Options
                        //startActivity(new Intent(getContext(),PaymentListview.class));
                        break;
                    case 2 :
                        //FAQ
                        //showDialog(R.layout.profile_faq_dialog);
                        Toast.makeText(getContext(),"Coming Soon \nYou Clicked on " + planets[position], Toast.LENGTH_LONG).show();
                        break;
                    case 3 :
                        //DEVELOPER INFO
                        //showDialog(R.layout.profile_dev_info_dialog);
                        Toast.makeText(getContext(),"Coming Soon \nYou Clicked on " + planets[position], Toast.LENGTH_LONG).show();
                        break;
                    case 4 :
                        //Copyright & Privacy policy
                        //showDialog(R.layout.profile_copy_pp_dialog);
                        Toast.makeText(getContext(),"Coming Soon \nYou Clicked on " + planets[position], Toast.LENGTH_LONG).show();
                        break;
                    case 5 :
//                        Intent intent = new Intent(Intent.ACTION_SEND);
//                        intent.setType("text/html");
//                        intent.putExtra(Intent.EXTRA_EMAIL, "manjunath189@gmail.com");
//                        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello Developer");
//                        intent.putExtra(Intent.EXTRA_TEXT, "Hello from " + mAuth.getInstance().getCurrentUser().getDisplayName());
//
//                        startActivity(Intent.createChooser(intent, "Send Email"));
                        break;

                    case 6 :
                        //made in india
                        //showDialog(R.layout.profile_madeinindia_dialog);
                        Toast.makeText(getContext(),"Coming Soon \nYou Clicked on " + planets[position], Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(),"Invalid", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });



        return v;
    }


    public void showDialog(final int layout){
//        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
//        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(layout, null, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        builder.setCancelable(false);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button b = dialogView.findViewById(R.id.buttonOk);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
