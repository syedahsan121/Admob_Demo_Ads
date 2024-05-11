package com.adsdemoby_ahsan.demo_admob_ads.Activites;

import static com.adsdemoby_ahsan.demo_admob_ads.nativetemplates.ActionBottomDialogFragment.isNetworkConnected;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.adsdemoby_ahsan.demo_admob_ads.R;
import com.adsdemoby_ahsan.demo_admob_ads.Utilz.AdsUtilize;
import com.adsdemoby_ahsan.demo_admob_ads.Utilz.SpUtils;
import com.adsdemoby_ahsan.demo_admob_ads.nativetemplates.TemplateView;
import com.facebook.shimmer.ShimmerFrameLayout;

public class Small_Native extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_native);
////for small native
        String nativeAdsId = SpUtils.getInstance().getStringValue("Nativeweb");
        TemplateView nativeTemplate = findViewById(R.id.my_template);
        ShimmerFrameLayout nativeShimmer = findViewById(R.id.load_native);
        RelativeLayout layNative = findViewById(R.id.Laynative);

        if (isNetworkConnected(this) && !TextUtils.isEmpty(nativeAdsId)) {
            if (TextUtils.isEmpty(nativeAdsId)) {
                layNative.setVisibility(View.GONE);
            } else {
                layNative.setVisibility(View.VISIBLE);
                AdsUtilize.getInstance().load_NativeAD(
                        Small_Native.this,
                        nativeAdsId,
                        nativeTemplate,
                        nativeShimmer,
                        layNative
                );
            }
        } else {
            layNative.setVisibility(View.GONE);
        }

    }
}