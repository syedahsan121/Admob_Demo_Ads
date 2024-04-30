package com.adsdemoby_ahsan.demo_admob_ads.nativetemplates;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.adsdemoby_ahsan.demo_admob_ads.R;


public class PrepareLoadingAdsDialog extends Dialog {


    public PrepareLoadingAdsDialog(Context context) {
        super(context, androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prepair_loading_ads);
    }

    public void hideLoadingAdsText() {
        findViewById(R.id.loading_dialog_tv).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }
}
