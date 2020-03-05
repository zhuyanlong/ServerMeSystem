package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorRegisterActivity extends AppCompatActivity {

    private Button buttonSubmit;
    private CheckBox checkBoxAgreement;

    private List<LinearLayout> linearLayout_ArrayList = new ArrayList<>(); //store all added service view
    private HashMap<String, String> serviceRegistered = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_register);

        this.buttonSubmit = findViewById(R.id.button_vendorRegister_submit);
        this.checkBoxAgreement = findViewById(R.id.checkBox_agreement);

        String[] checkboxText = new String[] {"CLeaning", "Dog Walking", "Tutoring", "Electronics"};
        String[] checkboxTextId = new String[] {"1","2","3","4"};
        String[] checkboxType = new String[] {"1", "2", "2", "1"};


        LinearLayout linearLayout_ServiceList = this.findViewById(R.id.linearLayout_vendor_register_serviceList);

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

                    //print
                    for(Map.Entry<String, String> a:serviceRegistered.entrySet()){
                        System.out.println(a.getKey()+" : " + a.getValue());
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
                checkedTag = ((CheckBox)ll.findViewById(R.id.checkBox_vendor_register_service)).getText().toString();
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
                VendorRegisterActivity.this,
                msg,
                Toast.LENGTH_LONG).show();
    }

}
