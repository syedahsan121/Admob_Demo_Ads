package com.adsdemoby_ahsan.demo_admob_ads.Activites;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adsdemoby_ahsan.demo_admob_ads.R;
import com.adsdemoby_ahsan.demo_admob_ads.Utilz.AdsUtilize;
import com.adsdemoby_ahsan.demo_admob_ads.Utilz.SpUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Banner_Ads extends AppCompatActivity {

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private Button interstatilbtn;
    String interstatialad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        interstatilbtn=findViewById(R.id.interstatialads);
        interstatialad = SpUtils.getInstance().getStringValue("Interstatial");

        Boolean bannerAdscont = SpUtils.getInstance().getBooleanValue("Bannercontrol");
        Log.d("cheakadsstatys", "onCreate: "+bannerAdscont);
        if (bannerAdscont==true){
            loadBannerAds();
        }else{

        }
        interstatilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean interstatialcontrols = SpUtils.getInstance().getBooleanValue("Interstatialcontrol");
                Log.d("cheakcontrolinter", "onCreate: "+interstatialcontrols);
                if (interstatialcontrols == true) {
                    AdsUtilize.getInstance().startLoadAdActivity(Banner_Ads.this, Small_Native.class.getCanonicalName(), interstatialad, null, 0);

                } else {
                   startActivity(new Intent(Banner_Ads.this,Small_Native.class));

                }


            }
        });



        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("FCMToken", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                Log.d("FCMToken", "Get FCM    " + token);
            }
        });


    }





    private void loadBannerAds() {
        String bannerAdsId = SpUtils.getInstance().getStringValue("Banner");
        FrameLayout adViewContainer = findViewById(R.id.ad_view_container);
        ShimmerFrameLayout footer = findViewById(R.id.load_banner);
        RelativeLayout layBanner = findViewById(R.id.layoutone);
        if (TextUtils.isEmpty(bannerAdsId)) {
            layBanner.setVisibility(View.GONE);
            AdsUtilize.getInstance().showNavigationBar(this);
        } else {
            layBanner.setVisibility(View.VISIBLE);
            AdsUtilize.getInstance().hideNavigationBar(this);
            AdsUtilize.getInstance().loadBannerCollapsible(this, bannerAdsId, adViewContainer, footer, layBanner);
        }
    }


}
