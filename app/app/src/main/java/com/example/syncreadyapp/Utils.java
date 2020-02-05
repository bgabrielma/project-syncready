package com.example.syncreadyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.syncreadyapp.views.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final int PICK_IMAGE = 1;
    public static final int CAMERA_REQUEST = 2;

    public static AlertDialog.Builder showInternalUnavailableConnectionToServerAlert(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Oops");
        builder.setMessage("De momento não é possivel estabelecer comunicação com os servidores da SyncReady.");
        builder.setIcon(R.drawable.ic_error);
        builder.setCancelable(false);

        return builder;
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
        builder.setIcon(R.drawable.ic_clear);
        builder.setCancelable(false);

        return builder;
    }

    public static AlertDialog.Builder showAlreadyAdded(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Código QR válido, mas...");
        builder.setMessage("Já está inserido nesta sala :)");
        builder.setIcon(R.drawable.ic_info);
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

    public static void requestFile(Activity activity, boolean isImage, boolean cameraRequested) {

        if (cameraRequested) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
            return;
        }

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");


        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        List<String> mimeTypes = new ArrayList<>();
        if (isImage) {
            mimeTypes.add("image/gif");
            mimeTypes.add("image/png");
            mimeTypes.add("image/jpeg");
            mimeTypes.add("image/bmp");
            mimeTypes.add("image/webp");
        } else {
            mimeTypes.add("application/pdf");
            mimeTypes.add("application/docx");
            mimeTypes.add("application/dotx");
            mimeTypes.add("application/doc");
            mimeTypes.add("application/dot");
        }

        chooserIntent.putExtra(Intent.EXTRA_MIME_TYPES, Arrays.copyOf(mimeTypes.toArray(), mimeTypes.size(), String[].class));

        // Launching the Intent
        activity.startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    public static File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public static void ApplicationLogout(Activity activity) {
        Intent mainActivity = new Intent(activity, MainActivity.class);
        activity.startActivity(mainActivity);
        activity.finish();
    }
}
