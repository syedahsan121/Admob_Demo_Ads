package com.adsdemoby_ahsan.demo_admob_ads.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adsdemoby_ahsan.demo_admob_ads.R;
import com.adsdemoby_ahsan.demo_admob_ads.Utilz.AdsUtilize;
import com.adsdemoby_ahsan.demo_admob_ads.Utilz.SpUtils;
import com.adsdemoby_ahsan.demo_admob_ads.nativetemplates.TemplateView;
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

public class Splash_Activity extends AppCompatActivity {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


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


    @Override
    protected void onResume() {
        super.onResume();
        getRealAdsFromFirebase();
        Boolean nativesplashid = SpUtils.getInstance().getBooleanValue("Nativesplashcontrol");
        Log.d("Nativesplashcontrolstatus", "onCreate: "+nativesplashid);
        if (nativesplashid==true){
            LoadNative();
        }else{
            TemplateView nativeTemplate = findViewById(R.id.my_template);
            ShimmerFrameLayout nativeShimmer = findViewById(R.id.load_native);
            RelativeLayout layNative = findViewById(R.id.Laynative);
            if (TextUtils.isEmpty("")) {
                layNative.setVisibility(View.GONE);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(Splash_Activity.this,  Banner_Ads.class));
                finish();

            }
        }, 9000);
    }
    private void getRealAdsFromFirebase() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600).build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.addOnConfigUpdateListener(new ConfigUpdateListener() {
            @Override
            public void onUpdate(@NonNull ConfigUpdate configUpdate) {
//                Log.d(TAG, "Updated keys: " + configUpdate.getUpdatedKeys());
                mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(Splash_Activity.this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d("check", "Config params updated: " + updated);

                            try {
                                ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

                                Bundle bundle = applicationInfo.metaData;
                                bundle.putString("com.google.android.gms.ads.APPLICATION_ID", mFirebaseRemoteConfig.getString("AppId"));
                                String apiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");

                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            SpUtils.getInstance().setStringValue("AppId", mFirebaseRemoteConfig.getString("AppId"));
                            SpUtils.getInstance().setStringValue("Banner", mFirebaseRemoteConfig.getString("Banner"));
                            SpUtils.getInstance().setStringValue("Interstatial", mFirebaseRemoteConfig.getString("Interstatial"));
                            SpUtils.getInstance().setStringValue("Nativeweb", mFirebaseRemoteConfig.getString("Nativeweb"));
                            SpUtils.getInstance().setStringValue("Nativesplash", mFirebaseRemoteConfig.getString("Nativesplash"));
                            SpUtils.getInstance().setStringValue("appopen", mFirebaseRemoteConfig.getString("appopen"));
                            ///for control
                            SpUtils.getInstance().setBooleanValue("Bannercontrol",mFirebaseRemoteConfig.getBoolean("Bannercontrol"));
                            SpUtils.getInstance().setBooleanValue("Nativesplashcontrol",mFirebaseRemoteConfig.getBoolean("Nativesplashcontrol"));
                            SpUtils.getInstance().setBooleanValue("Interstatialcontrol",mFirebaseRemoteConfig.getBoolean("Interstatialcontrol"));



                        } else {
                            Toast.makeText(Splash_Activity.this, "Fetch failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onError(FirebaseRemoteConfigException error) {

            }

        });
    }

    public void LoadNative(){
        String nativeAdsIdsnew = SpUtils.getInstance().getStringValue("Nativesplash");
        Log.d("adschekup", "LoadNative: "+nativeAdsIdsnew);
        TemplateView nativeTemplate = findViewById(R.id.my_template);
        ShimmerFrameLayout nativeShimmer = findViewById(R.id.load_native);
        RelativeLayout layNative = findViewById(R.id.Laynative);
        if (TextUtils.isEmpty(nativeAdsIdsnew)) {
            layNative.setVisibility(View.GONE);
        } else {
            layNative.setVisibility(View.VISIBLE);
            AdsUtilize.getInstance().load_NativeAD(this, nativeAdsIdsnew, nativeTemplate, nativeShimmer, layNative);
        }
    }



}