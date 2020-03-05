package com.example.servemesystem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorRegisterFragment extends Fragment {
    private Button buttonSubmit;
    private CheckBox checkBoxAgreement;

    private List<LinearLayout> linearLayout_ArrayList = new ArrayList<>(); //store all added service view
    private HashMap<String, String> serviceRegistered = new HashMap<>();

    public VendorRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_vendor_register, container, false);

        this.buttonSubmit = view.findViewById(R.id.button_vendorRegister_submit);
        this.checkBoxAgreement = view.findViewById(R.id.checkBox_agreement);

        String[] checkboxText = new String[] {"Appliances", "Electrical", "Plumbing", "Home Cleaning",
                "Tutoring", "Packaging and Moving", "Computer Repair", "Home Repair and Painting", "Pest Control"};
        String[] checkboxTextId = new String[] {"1","2","3","4", "5", "6", "7", "8", "9"};
        String[] checkboxType = new String[] {"1", "1", "1", "1", "1", "2", "2", "2", "2"};


        LinearLayout linearLayout_ServiceList = view.findViewById(R.id.linearLayout_vendor_register_serviceList);

        for(int i = 0; i < checkboxText.length; i++){

            LinearLayout linearLayout_newService = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_vendor_register_checkbox, null);

            //different type of service needs different view:
            //type1: $_/hour
            //type2: $_/time
            if(checkboxType[i].equals("1")){
                ((TextView)linearLayout_newService.findViewById(R.id.textView_type1)).setVisibility(View.VISIBLE);
                ((TextView)linearLayout_newService.findViewById(R.id.textView_type2)).setVisibility(View.GONE);
            } else {
                ((TextView)linearLayout_newService.findViewById(R.id.textView_type1)).setVisibility(View.GONE);
                ((TextView)linearLayout_newService.findViewById(R.id.textView_type2)).setVisibility(View.VISIBLE);
            }

            //set service info
            ((CheckBox)linearLayout_newService.findViewById(R.id.checkBox_vendor_register_service)).setText(checkboxText[i]);  //service name
            linearLayout_newService.setTag(checkboxTextId[i]);   //service id

            linearLayout_ArrayList.add(linearLayout_newService); //add service item to list for later use
            linearLayout_ServiceList.addView(linearLayout_newService); //add service item to linearLayout view one by one
        }

        setButtonSubmitRegisterListener();

        return view;
    }

    private void setButtonSubmitRegisterListener(){
        this.buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!checkBoxAgreement.isChecked()){
                    showMessage("You must agree to the agreement above.");
                } else{
                    int checkedNum = getVendorRegisterData();

                    if(checkedNum == 0){
                        showMessage("At least one service should be selected.");
                    }
                    else if(checkedNum == -1){
                        //no price
                    }
                    else {
                        //print
//                        for(Map.Entry<String, String> a:serviceRegistered.entrySet()){
//                            System.out.println(a.getKey()+" : " + a.getValue());
//                        }

                        //after submit, jump to home page
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction()
                                .replace(R.id.container_fragment, new HomeFragment())
                                .commit();

                        showMessage("Service Registration Successful.");
                    }
                }
            }
        });
    }

    private int getVendorRegisterData(){
        int checkedNum = 0;
        String checkedTag = "";
        String checkedPrice = "";
        for(LinearLayout ll: linearLayout_ArrayList){
            if(((CheckBox)ll.findViewById(R.id.checkBox_vendor_register_service)).isChecked()){
                checkedTag = ll.getTag().toString();
                checkedPrice = ((TextView)ll.findViewById(R.id.editText_vendor_register_price)).getText().toString();
                if(checkedPrice.isEmpty()){
                    ((TextView)ll.findViewById(R.id.textView_asterisks)).setVisibility(View.VISIBLE);
                    showMessage("Please fill in the price.");
                    checkedNum = -1;
                    break;
                } else{
                    ((TextView)ll.findViewById(R.id.textView_asterisks)).setVisibility(View.INVISIBLE);
                }
                serviceRegistered.put(checkedTag, checkedPrice);
                checkedNum++;
            }
        }
        return checkedNum;
    }

    private void showMessage(String msg){
        Toast.makeText(
                getActivity(),
                msg,
                Toast.LENGTH_LONG).show();
    }

}
