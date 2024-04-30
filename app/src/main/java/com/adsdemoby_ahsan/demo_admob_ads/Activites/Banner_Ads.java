package com.adsdemoby_ahsan.demo_admob_ads.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.adsdemoby_ahsan.demo_admob_ads.R;
import com.adsdemoby_ahsan.demo_admob_ads.Utilz.AdsUtilize;
import com.facebook.shimmer.ShimmerFrameLayout;

public class Banner_Ads extends AppCompatActivity {

    Button smallnativebtn,largenativebtn,interstatiladsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
///for banner ads
        String bannerAdsId = getResources().getString(R.string.BannerIdAdmob);
        FrameLayout ad_view_container = findViewById(R.id.ad_view_container);
        ShimmerFrameLayout footer = findViewById(R.id.load_banner);
        RelativeLayout layBanner = findViewById(R.id.layoutone);
        if (TextUtils.isEmpty(bannerAdsId)) {
            layBanner.setVisibility(View.GONE);
            AdsUtilize.getInstance().showNavigationBar(Banner_Ads.this);
        } else {
            layBanner.setVisibility(View.VISIBLE);
            AdsUtilize.getInstance().hideNavigationBar(Banner_Ads.this);
            AdsUtilize.getInstance().loadBannerCollapsible(this, bannerAdsId, ad_view_container, footer, layBanner);
        }

        smallnativebtn=findViewById(R.id.nativesmall);
        largenativebtn=findViewById(R.id.nativelarge);
        interstatiladsbtn=findViewById(R.id.interstatialads);
        smallnativebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Banner_Ads.this, Small_Native.class));
            }
        });

        largenativebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Banner_Ads.this,Large_Native_Activity.class));
            }
        });

        interstatiladsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsUtilize.getInstance().startLoadAdActivity(Banner_Ads.this, Small_Native.class.getCanonicalName(), getResources().getString(R.string.intersitialAdUnit), null, 0);



            }
        });


    }
}