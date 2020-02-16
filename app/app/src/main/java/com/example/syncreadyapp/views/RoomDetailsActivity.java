package com.example.syncreadyapp.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.adapters.MessageListAdapter;
import com.example.syncreadyapp.adapters.RoomDetailsFilesAdapter;
import com.example.syncreadyapp.databinding.RoomDetailsBinding;
import com.example.syncreadyapp.interfaces.OnRoomDetailsFileClickListener;
import com.example.syncreadyapp.models.messagemodel.MessageModel;
import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.GroupActivityViewModel;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RoomDetailsActivity extends AppCompatActivity implements OnRoomDetailsFileClickListener {
    private RoomDetailsBinding detailsBinding;
    private Bundle bundle;
    private HomeActivityViewModel homeActivityViewModel;
    private GroupActivityViewModel groupActivityViewModel;
    private String groupUsersFormatted = null;
    private AlertDialog showImageUploadProcessing;
    private List<MessageModel> filesAsMessages = new ArrayList<>();
    private String newGroupImagePath = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showImageUploadProcessing = Utils.showImageUploadProcessing(RoomDetailsActivity.this);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.room_details);

        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        groupActivityViewModel = ViewModelProviders.of(this).get(GroupActivityViewModel.class);

        bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));
        groupActivityViewModel.roomUuid.setValue(bundle.getString("syncready_room_uuid", null));
        groupActivityViewModel.roomTitle.setValue(bundle.getString("syncready_room_title", null));
        groupActivityViewModel.roomImage.setValue(bundle.getString("syncready_room_image", null));
        groupActivityViewModel.roomDesignation.setValue(bundle.getString("syncready_room_designation", null));
        groupUsersFormatted = bundle.getString("groupUsersFormatted", null);

        // loading user data
        homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUserDataObserver);
    }

    private final Observer<ResponseUser> getUserDataObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {
            homeActivityViewModel.userMutableLiveData.setValue(responseUser.getData().get(0));
            groupActivityViewModel.getMessagesByRoom(groupActivityViewModel.roomUuid.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue(), false)
                    .observe(RoomDetailsActivity.this, getMessagesByRoomObserver);
        }
    };

    private final Observer<ResponseMessage> getMessagesByRoomObserver = new Observer<ResponseMessage>() {
        @Override
        public void onChanged(ResponseMessage responseMessage) {
            if (responseMessage != null) {
                configureToolbar();
                configurePermissions();

                filesAsMessages.clear();
                for (MessageModel messageModel : responseMessage.getData()) {
                    if (messageModel.getIsImage() == 1) filesAsMessages.add(messageModel);
                }
                configureRecyclerFilesView(filesAsMessages);

                detailsBinding.roomDetailsGroupUsersFormatted.setText(groupUsersFormatted);

                detailsBinding.btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RoomDetailsActivity.this, SyncReadyPDFViewerActivity.class);
                        intent.putExtra("syncready_room_designation", groupActivityViewModel.roomDesignation.getValue());
                        startActivity(intent);
                    }
                });

                detailsBinding.expandedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(RoomDetailsActivity.this, PictureActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("groupImage", groupActivityViewModel.roomImage.getValue());
                        bundle.putString("groupTitle", groupActivityViewModel.roomTitle.getValue());
                        bundle.putString("groupImageFile", groupActivityViewModel.roomImage.getValue());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                detailsBinding.buttonUploadImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.requestFile(RoomDetailsActivity.this, true, false);
                    }
                });
            }
        }
    };

    private final Observer<JsonObject> getUploadImage = new Observer<JsonObject>() {
        @Override
        public void onChanged(JsonObject responseBody) {

            if (responseBody != null ) {
                String filePath = newGroupImagePath = responseBody.get("filename").getAsString();
                homeActivityViewModel.getUpdateRoomImage(filePath, groupActivityViewModel.roomUuid.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                        .observe(RoomDetailsActivity.this, getUserUpdateObserver);
            } else {
                Utils.showInternalUnavailableConnectionToServerAlert(RoomDetailsActivity.this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.ApplicationLogout(RoomDetailsActivity.this);
                    }
                }).show();
            }
        }
    };

    private final Observer<JsonObject> getUserUpdateObserver = new Observer<JsonObject>() {
        @Override
        public void onChanged(JsonObject jsonObject) {
            if (jsonObject != null && jsonObject.get("ok").getAsString().equals("true")) {
                Utils.showGroupUpdated(RoomDetailsActivity.this)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showImageUploadProcessing.dismiss();
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("newGroupImage", newGroupImagePath);
                                setResult(Utils.GROUP_IMAGE_UPDATED, resultIntent);

                                finish();
                            }
                        })
                        .show();
            }
        }
    };

    public void configureToolbar() {

        detailsBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        detailsBinding.toolbar.setTitle(groupActivityViewModel.roomTitle.getValue());
        Picasso.get()
                .load(RetrofitInstance.BASE_URL + "public/files/" + groupActivityViewModel.roomImage.getValue())
                .placeholder(R.drawable.loading)
                .into(detailsBinding.expandedImage);
    }

    public void configurePermissions() {
        if (homeActivityViewModel.userMutableLiveData.getValue().getType().equalsIgnoreCase("Cliente")) {
            detailsBinding.toolbar.getMenu().getItem(0).setVisible(false);
            detailsBinding.buttonUploadImage.setVisibility(View.INVISIBLE);
        }
    }

    public void configureRecyclerFilesView(List<MessageModel> messagesAsFiles) {
        RecyclerView recyclerView = detailsBinding.roomDetailsRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RoomDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RoomDetailsFilesAdapter roomDetailsFilesAdapter = new RoomDetailsFilesAdapter(messagesAsFiles, groupActivityViewModel.roomTitle.getValue(), this);

        recyclerView.setAdapter(roomDetailsFilesAdapter);
    }

    @Override
    public void onRoomDetailsFileClick(int position) {

        Intent intent = new Intent(RoomDetailsActivity.this, PictureActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("groupImage", groupActivityViewModel.roomImage.getValue());
        bundle.putString("groupTitle", groupActivityViewModel.roomTitle.getValue());
        bundle.putString("groupImageFile", filesAsMessages.get(position).getContent());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            File photoFile = null;
            Bitmap bitmap = null;
            FileOutputStream fileOutputStream = null;

            try {
                photoFile = Utils.createImageFile(RoomDetailsActivity.this);
                fileOutputStream = new FileOutputStream(photoFile);

                if (requestCode == Utils.CAMERA_REQUEST) bitmap = (Bitmap) data.getExtras().get("data");
                else if (requestCode == Utils.PICK_IMAGE) bitmap = BitmapFactory.decodeFile(Utils.getRealPathFromURI(RoomDetailsActivity.this, data.getData()));

                bitmap = Utils.fixOrientation(bitmap);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                fileOutputStream.write(bytes.toByteArray());

                fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestBody mFile = null;
            if (photoFile != null) {
                mFile = RequestBody.create(MediaType.parse("image/jpeg"), photoFile);
            }
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("newFile", photoFile.getName(), mFile);

            //Create request body with text description and text media type
            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");


            showImageUploadProcessing.show();
            groupActivityViewModel.uploadImage(fileToUpload, description, homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                    .observe(this, getUploadImage);
        }
    }
}
