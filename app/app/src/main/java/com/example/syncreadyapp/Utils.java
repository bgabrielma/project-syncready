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
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Oops");
        builder.setMessage("No momento não é possivel estabelecer conexão com os servidores da SyncReady.");
        builder.setIcon(R.drawable.ic_error);
        builder.setPositiveButton("Ok", null);
        builder.setCancelable(false);
        builder.show();
    }

    public static void showPermissionDeniedAlert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Oops");
        builder.setMessage("Deverá aceitar estas permissões para utilizar a aplicação.");
        builder.setIcon(R.drawable.ic_block);
        builder.setPositiveButton("Ok", null);
        builder.setCancelable(false);
        builder.show();
    }
}
