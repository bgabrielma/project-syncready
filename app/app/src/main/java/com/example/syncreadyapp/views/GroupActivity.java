package com.example.syncreadyapp.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.syncreadyapp.databinding.GroupBinding;
import com.example.syncreadyapp.models.messagemodel.MessageModel;
import com.example.syncreadyapp.models.messagemodel.ResponseMessage;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.GroupActivityViewModel;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class GroupActivity extends AppCompatActivity {
    private Socket socket;
    private HomeActivityViewModel homeActivityViewModel;
    private GroupActivityViewModel groupActivityViewModel;
    private GroupBinding groupBinding;
    private Bundle bundle;
    private List<MessageModel> messageModels;

    protected RecyclerView recyclerViewMessages;

    private String usersGroupFormated = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.group);
        try {
            socket = IO.socket(RetrofitInstance.BASE_URL);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        groupActivityViewModel = ViewModelProviders.of(this).get(GroupActivityViewModel.class);

        bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));
        groupActivityViewModel.roomUuid.setValue(bundle.getString("syncready_room_uuid", null));
        groupActivityViewModel.roomTitle.setValue(bundle.getString("syncready_room_title", null));
        groupActivityViewModel.roomImage.setValue(bundle.getString("syncready_room_image", null));

        // loading user data
        homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUserDataObserver);
    }

    private final Observer<ResponseMessage> getMessagesByRoomObserver = new Observer<ResponseMessage>() {
        @Override
        public void onChanged(ResponseMessage responseMessage) {
            if (responseMessage.getData() != null) {
                // configure adapter
                recyclerViewMessages = groupBinding.recyclerMessages;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GroupActivity.this);
                linearLayoutManager.setStackFromEnd(true);

                recyclerViewMessages.setLayoutManager(linearLayoutManager);
                messageModels = responseMessage.getData();

                recyclerViewMessages.setAdapter(new MessageListAdapter(messageModels, homeActivityViewModel.uuidMutableLiveData.getValue()));
            }
        }
    };

    private final Observer<ResponseUser> getUsersByRoomObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {

            if (responseUser.getData() != null) {

                socket.on("refreshGroupMessages", onNewMessage);
                for (User user : responseUser.getData()) {
                    usersGroupFormated = usersGroupFormated.concat(user.getFullname().split(" ")[0] + (user == responseUser.getData().get(responseUser.getData().size() - 1) ? "" : ", "));
                }

                // set group's user text value
                groupBinding.groupToolbarInclude.toolbarGroupUsersFormatted.setText(usersGroupFormated);

                groupActivityViewModel.getMessagesByRoom(groupActivityViewModel.roomUuid.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                        .observe(GroupActivity.this, getMessagesByRoomObserver);
            }
        }
    };

    private final Observer<ResponseUser> getUserDataObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {

            if (responseUser.getData() != null) {
                groupBinding = DataBindingUtil.setContentView(GroupActivity.this, R.layout.group);
                configureToolbar();

                configureMessageButtons();

                homeActivityViewModel.userMutableLiveData.setValue(responseUser.getData().get(0));

                groupActivityViewModel.getUsersByRoom(groupActivityViewModel.roomUuid.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                        .observe(GroupActivity.this, getUsersByRoomObserver);
            }
        }
    };

    private final Observer<JsonObject> getUploadImage = new Observer<JsonObject>() {
        @Override
        public void onChanged(JsonObject responseBody) {
            String filePath = responseBody.get("filename").getAsString();
            socket.emit("newMessage", homeActivityViewModel.uuidMutableLiveData.getValue(), groupActivityViewModel.roomUuid.getValue(), filePath, 1);
            groupBinding.editTextNewMessage.setText("");
        }
    };


    public void configureToolbar() {
        groupBinding.groupToolbarInclude.toolbarGroupTitle.setText(groupActivityViewModel.roomTitle.getValue());

        // set image
        Picasso.get()
                .load(RetrofitInstance.BASE_URL + "public/files/" + groupActivityViewModel.roomImage.getValue())
                .placeholder(R.drawable.loading)
                .into(groupBinding.groupToolbarInclude.groupImage);

        groupBinding.groupToolbarInclude.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        groupBinding.groupToolbarInclude.linearDetailsZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent roomDetailsActivity = new Intent(GroupActivity.this, RoomDetailsActivity.class);
                roomDetailsActivity.putExtras(bundle);

                startActivity(roomDetailsActivity);
            }
        });
    }

    public void configureMessageButtons() {
        groupBinding.messageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageContent = groupBinding.editTextNewMessage.getText().toString();

                if(!messageContent.isEmpty()) {
                    socket.emit("newMessage", homeActivityViewModel.uuidMutableLiveData.getValue(), groupActivityViewModel.roomUuid.getValue(), messageContent);
                    groupBinding.editTextNewMessage.setText("");
                }
                else
                    Snackbar.make(groupBinding.getRoot(), "Não é possível enviar mensagens com o corpo do texto em branco.", Snackbar.LENGTH_SHORT).show();
            }
        });

        groupBinding.buttonAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.requestFile(GroupActivity.this, true, false);
            }
        });

        groupBinding.buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.requestFile(GroupActivity.this, true, true);
            }
        });
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            GroupActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        jsonObject = jsonObject.getJSONArray("newMessage").getJSONObject(0);

                        messageModels.add(new MessageModel(
                                jsonObject.getString("fullname"),
                                jsonObject.getString("pk_uuid"),
                                jsonObject.getInt("pk_message"),
                                jsonObject.getString("content"),
                                jsonObject.getString("created_at"),
                                jsonObject.getString("update_at"),
                                jsonObject.getString("uuid_message"),
                                jsonObject.getString("Status_Message_uuid_status_message"),
                                jsonObject.getString("type"),
                                jsonObject.getString("sent_at"),
                                jsonObject.getInt("isImage")
                        ));

                        recyclerViewMessages.getAdapter().notifyItemInserted(messageModels.size() - 1);
                        recyclerViewMessages.smoothScrollToPosition(messageModels.size() - 1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.off("refreshGroupMessages", onNewMessage);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Socket state", "" + socket.connected());
    }

    @Override
    protected void onResume() {
        super.onResume();
        socket.connected();
        Log.d("Socket state", "" + socket.connected());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            File photoFile = null;
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;

            try {
                photoFile = Utils.createImageFile(GroupActivity.this);
                fileOutputStream = new FileOutputStream(photoFile);


                if (requestCode == Utils.CAMERA_REQUEST) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                    fileOutputStream.write(bytes.toByteArray());
                }

                if (requestCode == Utils.PICK_IMAGE) {
                    inputStream = GroupActivity.this.getContentResolver().openInputStream(data.getData());
                    Utils.copyStream(inputStream, fileOutputStream);
                    inputStream.close();
                }

                fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestBody mFile = RequestBody.create(MediaType.parse("image/jpg"), photoFile);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("newFile", photoFile.getName(), mFile);

            groupActivityViewModel.uploadImage(fileToUpload, mFile, homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUploadImage);
        }
    }
}
