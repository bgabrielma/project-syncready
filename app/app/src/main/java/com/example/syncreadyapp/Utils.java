package com.example.syncreadyapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static void showInternalUnavailableConnectionToServerAlert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Oops");
        builder.setMessage("De momento não é possivel estabelecer comunicação com os servidores da SyncReady.");
        builder.setIcon(R.drawable.ic_error);
        builder.setPositiveButton("Ok", null);
        builder.setCancelable(false);
        builder.show();
    }

    public static AlertDialog.Builder showPermissionDeniedAlert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Oops");
        builder.setMessage("Deverá aceitar estas permissões para utilizar a aplicação. Se não conseguir aceitar permissões, limpe os dados da aplicação e em seguida tente novamente.");
        builder.setIcon(R.drawable.ic_block);
        builder.setCancelable(false);

        return builder;
    }

    public static AlertDialog.Builder showInvalidRoomCode(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Código QR inválido");
        builder.setMessage("Oops, não foi encontrado nenhuma sala a partir do código lido :(");
        builder.setIcon(R.drawable.ic_block);
        builder.setCancelable(false);

        return builder;
    }
    public static boolean isEmailPreValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
