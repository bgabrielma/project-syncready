package com.example.syncreadyapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class Utils {
    public static void showInternalUnavailableConnectionToServerAlert(Activity activity) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activity.getApplicationContext(), R.drawable.ic_check);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLightSaturate));

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Oops");
        builder.setMessage("No momento não é possivel estabelecer conexão com os servidores da SyncReady.");
        builder.setIcon(R.drawable.ic_error);
        builder.setPositiveButton("Entendido", null);
        builder.setPositiveButtonIcon(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.ic_check));
        builder.setCancelable(false);
        builder.show();
    }
}
