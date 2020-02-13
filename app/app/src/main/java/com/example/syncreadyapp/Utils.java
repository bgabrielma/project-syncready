package com.example.syncreadyapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.example.syncreadyapp.views.MainActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final int PICK_IMAGE = 1;
    public static final int CAMERA_REQUEST = 2;
    public static final int GROUP_IMAGE_DETAILS_VIEW = 3;
    public static final int ROOM_VIEW = 4;
    public static final int GROUP_IMAGE_UPDATED = 5;
    public static final int GROUP_LIST_NEED_TO_BE_RELOADED = 6;

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

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, PICK_IMAGE);
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

    public static void ApplicationLogout(Activity activity) {
        Intent mainActivity = new Intent(activity, MainActivity.class);
        activity.startActivity(mainActivity);
        activity.finish();
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            String path = cursor.getString(column_index);

            return path;
        } catch (Exception e) {
            Log.e("TAG", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static AlertDialog showImageUploadProcessing(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Aguarde");
        builder.setMessage("A enviar imagem...");
        builder.setIcon(R.drawable.ic_action_upload);
        builder.setCancelable(false);

        return builder.create();
    }

    public static Date getDateFromString(String inputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date date = null;
        try {
            date = simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static AlertDialog.Builder showUserUpdated(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Utilizador atualizado");
        builder.setMessage("O utilizador foi atualizado com sucesso.");
        builder.setIcon(R.drawable.ic_update);
        builder.setCancelable(false);

        return builder;
    }

    public static AlertDialog.Builder showGroupUpdated(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Grupo atualizado");
        builder.setMessage("A imagem do grupo foi atualizada com sucesso.");
        builder.setIcon(R.drawable.ic_update);
        builder.setCancelable(false);

        return builder;
    }

    public static AlertDialog.Builder showGroupExpired(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Não é possível entrar no grupo");
        builder.setMessage("O tempo de validade do grupo expirou, contacte a loja responsável pela criação da sala.");
        builder.setIcon(R.drawable.ic_sync_disabled);
        builder.setCancelable(false);

        return builder;
    }

    public static Bitmap fixOrientation(Bitmap mBitmap) {
        if (mBitmap.getWidth() > mBitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        }

        return mBitmap;
    }
}
