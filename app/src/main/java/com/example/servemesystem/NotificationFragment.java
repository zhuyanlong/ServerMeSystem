package com.example.servemesystem;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static androidx.core.content.ContextCompat.getSystemService;

public class NotificationFragment extends Fragment {

    Switch sound_switch1;
    Switch communication_switch1;
    Switch rewards_switch1;

    Button buttonBackNotification;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_notification, container, false);

        sound_switch1 = view.findViewById(R.id.sound_switch);
        communication_switch1 = view.findViewById(R.id.communication_switch);
        rewards_switch1 = view.findViewById(R.id.rewards_switch);


        sound_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext(),"Sound On",Toast.LENGTH_SHORT).show();

                    try {
                        //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }else{
                    Toast.makeText(getContext(),"Sound Off",Toast.LENGTH_SHORT).show();
                }

            }
        });

        communication_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext(),"Communication On",Toast.LENGTH_SHORT).show();
                    //NotificationManager notificationManager = (NotificationManager) getSystemService(.NOTIFICATION_SERVICE);

                }else{
                    Toast.makeText(getContext(),"Communication Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        rewards_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext(),"Rewards Points will be applied",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Rewards Points will Not be applied",Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackNotification = view.findViewById(R.id.buttonNotificatonBack);
        buttonBackNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLogin);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                getActivity().finish();
            }
        });


        return view;
    }
}
