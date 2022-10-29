package com.odenktools.netzmeodenktools.util;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.odenktools.netzmeodenktools.BaseApp;
import com.odenktools.netzmeodenktools.R;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class AppUtils {

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            BaseApp.applicationHandler.post(runnable);
        } else {
            BaseApp.applicationHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        BaseApp.applicationHandler.removeCallbacks(runnable);
    }

    public static String signTopup(String data) {
        String s = new String(Hex.encodeHex(DigestUtils.md5(data)));
        byte[] stringToSign = DigestUtils.sha1(s);
        return new String(Hex.encodeHex(stringToSign));
    }

    private static void showSnackBar(View view, Context context, String text, boolean success) {
        Snackbar sb = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE);
        if (!success) {
            sb.getView().setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        } else {
            sb.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.green_notify));
        }
        sb.setAction("YES", act -> sb.dismiss());
        sb.setActionTextColor(ContextCompat.getColor(context, android.R.color.white));
        sb.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        sb.show();
    }

    public static void showSnackBar(View view, Context context, String text) {
        showSnackBar(view, context, text, true);
    }

    public static void showFailedSnackBar(View view, Context context, String text) {
        showSnackBar(view, context, text, false);
    }
}
