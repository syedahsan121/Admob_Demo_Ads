package com.adsdemoby_ahsan.demo_admob_ads.nativetemplates;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.adsdemoby_ahsan.demo_admob_ads.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment  implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    private NativeAd nativeAds;
    TemplateView Nativetemplate;
    BottomSheetDialog bottomSheetDialog;
    CardView exitCard;
    RelativeLayout Laynative;
    ShimmerFrameLayout NativeShimmer;
    View view;

    public static ActionBottomDialogFragment newInstance() {
        return new ActionBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);
        exitCard = view.findViewById(R.id.exitCard);
        NativeShimmer = view.findViewById(R.id.load_native);
        Laynative = view.findViewById(R.id.Laynative);
        if (isNetworkConnected(getActivity())) {
            load_NativeAD(getString(R.string.NativeExit), Laynative, NativeShimmer);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Laynative.setVisibility(View.GONE);
                }
            }, 1000);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.exitCard).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onClick(View view) {
        mListener.onItemClick(getActivity());

    }

    public interface ItemClickListener {
        void onItemClick(Context context);
    }

    public void load_NativeAD(String admob_native_splash, final RelativeLayout Laynative, final ShimmerFrameLayout NativeShimmer) {
        AdLoader adLoader = new AdLoader.Builder(getActivity(), admob_native_splash).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                Nativetemplate = view.findViewById(R.id.my_template);
                Nativetemplate.setStyles(styles);
                Nativetemplate.setNativeAd(nativeAd);
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Nativetemplate = view.findViewById(R.id.my_template);
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
}