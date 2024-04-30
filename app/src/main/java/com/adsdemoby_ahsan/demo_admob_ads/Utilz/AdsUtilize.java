package com.adsdemoby_ahsan.demo_admob_ads.Utilz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.adsdemoby_ahsan.demo_admob_ads.nativetemplates.ConstantAds;
import com.adsdemoby_ahsan.demo_admob_ads.nativetemplates.LoadAdsActivity;
import com.adsdemoby_ahsan.demo_admob_ads.nativetemplates.NativeTemplateStyle;
import com.adsdemoby_ahsan.demo_admob_ads.nativetemplates.TemplateView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;

public class AdsUtilize {
    private static AdsUtilize staticInstance;
    private final Context context;
    private boolean isNavigationBarHidden = false;

    // Private constructor for non-static instance
    private AdsUtilize(Context context) {
        this.context = context;
    }

    // Static method to get the static instance
    public static AdsUtilize getInstance() {
        if (staticInstance == null) {
            staticInstance = new AdsUtilize(null); // Pass null or a default context here
        }
        return staticInstance;
    }

    private static AdSize getAdSize(Activity activity, FrameLayout adContainerView) {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = outMetrics.density;
        float adWidthPixels = adContainerView.getWidth();
        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }
        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public static boolean isNetworkConnected(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = cm.getActiveNetwork();

                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);
                    if (nc != null)
                        return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
                }
            }
        }

        return false;
    }

    // Method that uses the non-static instance
    public void doSomethingWithContext() {
        // Use the context here, for example:
        if (context != null) {
            Toast.makeText(context, "Hello from AdsUtilize (non-static)!", Toast.LENGTH_SHORT).show();
        } else {
            // Handle the case where context is null
        }
    }

    public void makeFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void load_Adaptiv_Banner(Activity activity, String bannerId, FrameLayout adContainerView, ShimmerFrameLayout footer, RelativeLayout laybanner) {


        if (isNetworkConnected(activity)) {


            AdView adView = new AdView(activity);
            adView.setAdUnitId(bannerId);
            adContainerView.removeAllViews();
            adContainerView.addView(adView);

            AdSize adSize = getAdSize(activity, adContainerView);
            adView.setAdSize(adSize);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    footer.setVisibility(View.GONE);
                    footer.stopShimmer();
                    adContainerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    footer.setVisibility(View.GONE);
                    adContainerView.setVisibility(View.GONE);
                }
            });
        } else {
            laybanner.setVisibility(View.GONE);
        }
    }

    public void load_NativeAD(Context activity, String admobNativeIds, TemplateView nativetemplate, ShimmerFrameLayout NativeShimmer, RelativeLayout Laynative) {

        if (isNetworkConnected(activity)) {
            AdLoader adLoader = new AdLoader.Builder(activity, admobNativeIds).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();

                    nativetemplate.setStyles(styles);
                    nativetemplate.setNativeAd(nativeAd);
                    NativeShimmer.setVisibility(View.GONE);
                    NativeShimmer.stopShimmer();
                }
            }).withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Laynative.setVisibility(View.GONE);
                    NativeShimmer.setVisibility(View.GONE);
                    super.onAdFailedToLoad(loadAdError);
                }

                @Override
                public void onAdLoaded() {
                    Laynative.setVisibility(View.VISIBLE);
                    NativeShimmer.setVisibility(View.GONE);
                    nativetemplate.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());


        } else {
            Laynative.setVisibility(View.GONE);

        }
    }
    public void hideOrShowNavigation(Activity activity) {
        if (isNetworkConnected(activity)) {
            hideNavigationBar(activity);
        } else {
            showNavigationBar(activity);
        }
    }

    public void hideNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        isNavigationBarHidden = true;
    }

    public void showNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        isNavigationBarHidden = false;
    }

    public void loadBannerCollapsible(Activity context, String bannerId, FrameLayout adContainerView,
                                      ShimmerFrameLayout footer, RelativeLayout laybanner) {
        if (isNetworkConnected(context)) {
            AdView adView = new AdView(context);
            adContainerView.setVisibility(View.VISIBLE);
            adContainerView.removeAllViews();
            Bundle extras = new Bundle();
            extras.putString("collapsible", "bottom");
            AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
            adView.setAdUnitId(bannerId);
            // adView.loadAd(adRequest);

            AdSize adSize = getAdSize(context, adContainerView);
            adView.setAdSize(adSize);
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    // findViewById(R.id.include).setVisibility(View.INVISIBLE);
                    footer.setVisibility(View.GONE);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    footer.setVisibility(View.GONE);
                }
            });

            // adContainerView.addView(adView);
            RelativeLayout.LayoutParams adContainerParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
            );
            adContainerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            adContainerView.addView(adView, adContainerParams);
        } else {
            laybanner.setVisibility(View.GONE);
        }
    }


    public void startLoadAdActivity( Context context,String nextClassName,String intertialAdsIds,String value, int KeyTwo) {
        if (isNetworkConnected(context)) {
            if(TextUtils.isEmpty(intertialAdsIds)){
                try {
                    Intent mIntent = new Intent(context, Class.forName(nextClassName));
                    mIntent.putExtra(ConstantAds.KeyTwo, KeyTwo);
                    mIntent.putExtra(ConstantAds.KeyOne, value);
                    context.startActivity(mIntent);
                    Log.d("checkSide", "startLoadAdActivity: emty ");
                } catch (Exception e) {


                }
            }else {
                ConstantAds.currentTime = System.currentTimeMillis();
                if ((ConstantAds.currentTime - ConstantAds.lastClickTime > 8000)) {
                    ConstantAds.lastClickTime = ConstantAds.currentTime;

                    Intent mIntent = new Intent(context, LoadAdsActivity.class);
                    mIntent.putExtra(ConstantAds.nextClassName, nextClassName);
                    mIntent.putExtra(ConstantAds.interstitialid,intertialAdsIds );
                    mIntent.putExtra(ConstantAds.KeyTwo, KeyTwo);
                    mIntent.putExtra(ConstantAds.KeyOne, value);
                    context.startActivity(mIntent);

                    Log.d("checkSide", "startLoadAdActivity: ads phase ");
                } else {
                    try {
                        Intent mIntent = new Intent(context, Class.forName(nextClassName));
                        mIntent.putExtra(ConstantAds.KeyTwo, KeyTwo);
                        mIntent.putExtra(ConstantAds.KeyOne, value);
                        context.startActivity(mIntent);

                        Log.d("checkSide", "startLoadAdActivity: adsphasin ");
                    } catch (Exception e) {

                    }
                }
            }

        } else {

            try {
                Intent mIntent = new Intent(context, Class.forName(nextClassName));
                mIntent.putExtra(ConstantAds.KeyTwo, KeyTwo);
                mIntent.putExtra(ConstantAds.KeyOne, value);
                context.startActivity(mIntent);

                Log.d("checkSide", "startLoadAdActivity: outsied ");
            } catch (Exception e) {

            }
        }
    }
}
