package com.adsdemoby_ahsan.demo_admob_ads.nativetemplates;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.adsdemoby_ahsan.demo_admob_ads.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.bottomsheet.BottomSheetDialog;


abstract  public class BaseActivity extends AppCompatActivity {
    RelativeLayout layout_footer;
    public static String downloadLink = "https://drive.google.com/file/d/1xXk6hTqAgALm3m9wPqeNaMIyzfdyV_T8/view?usp=sharing";
    private Context context;
    TemplateView Nativetemplate;
    BottomSheetDialog bottomSheetDialog;
    CardView exitCard;
    RelativeLayout Laynative;

    ShimmerFrameLayout nativeTwo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void rateUS(final Activity act) {
        Uri uri = Uri.parse("market://details?id=" + act.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            act.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + act.getPackageName())));
        }
    }

    public  boolean isNetworkConnected(Context context) {
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

    public static void showAlert(final Activity act) {

        if (act.isFinishing()) {
            return;
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(act);
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                act.finish();
                System.exit(1);

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                rateUS(act);
            }
        });

        alertDialog.show();
    }

    public void load_NativeAD(String admob_native_splash, final RelativeLayout Laynative, final ShimmerFrameLayout NativeShimmer) {
        AdLoader adLoader = new AdLoader.Builder(this, admob_native_splash).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                Nativetemplate = findViewById(R.id.my_template);
                Nativetemplate.setStyles(styles);
                Nativetemplate.setNativeAd(nativeAd);
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Nativetemplate = findViewById(R.id.my_template);
                Laynative.setVisibility(View.GONE);
                NativeShimmer.setVisibility(View.GONE);
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded() {
                Laynative.setVisibility(View.VISIBLE);
                NativeShimmer.setVisibility(View.GONE);
                Nativetemplate.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        }).build();


        adLoader.loadAd(new AdRequest.Builder().build());
    }


    ///for list view
    public void expandListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        listView.measure(0, 0);
        params.height = listView.getMeasuredHeight() * listAdapter.getCount() + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

//    public void loadSimpleBanner(Activity activity, ShimmerFrameLayout loadBanner) {
//        final AdView mAdView = activity.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                mAdView.setVisibility(View.VISIBLE);
//                loadBanner.setVisibility(View.GONE);
//            }
//        });
//    }


//
////
//        public void loadNativMedium(final  Activity activity) {
//            AdLoader adLoader = new AdLoader.Builder(this, activity)
//                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
//                        @Override
//                        public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
//                            NativeTemplateStyle styles = new
//                                    NativeTemplateStyle.Builder().build();
//                            Nativetemplate = findViewById(R.id.my_template);
//                            Nativetemplate.setStyles(styles);
//                            Nativetemplate.setNativeAd(nativeAd);
//                        }
//                    }).withAdListener(new AdListener() {
//                        @Override
//                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
////                            Laynative.setVisibility(View.GONE);
////                            NativeShimmer.setVisibility(View.GONE);
//                            super.onAdFailedToLoad(loadAdError);
//                        }
//
//                        @Override
//                        public void onAdLoaded() {
////                            Laynative.setVisibility(View.VISIBLE);
////                            NativeShimmer.setVisibility(View.GONE);
//                            super.onAdLoaded();
//                        }
//                    }).build();
//
//
//            adLoader.loadAd(new AdRequest.Builder().build());
//        }


//    public void loadAdaptiveBanner(final Activity activity) {
////        MobileAds.initialize(activity.getApplicationContext(), activity.getResources().getString(R.string.app_id));
//        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//
//        MobileAds.setRequestConfiguration(
//                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("BEA8910591C3A00C7584C6F01CA490E1")).build());
//
//        final FrameLayout adContainerView = activity.findViewById(R.id.ad_view_container);
//
//        // Since we're loading the banner based on the adContainerView size, we need to wait until this
////        // view is laid out before we can get the width.
////        adContainerView.post(new Runnable() {
////            @Override
////            public void run() {
////                load_Adaptiv_Banner(activity, adContainerView);
////            }
////        });
//
//    }

    public AdView adView;

    public void load_Adaptiv_Banner(Activity activity, final FrameLayout adContainerView) {
        // Create an ad request.
//        final RelativeLayout footer = activity.findViewById(R.id.footer);
        String AD_UNIT_ID = activity.getResources().getString(R.string.BannerIdAdmob);

        adView = new AdView(activity);
        adView.setAdUnitId(AD_UNIT_ID);
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize(activity, adContainerView);
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
//                footer.setVisibility(View.GONE);
                adContainerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
//                footer.setVisibility(View.VISIBLE);
                adContainerView.setVisibility(View.GONE);
            }
        });
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


}
