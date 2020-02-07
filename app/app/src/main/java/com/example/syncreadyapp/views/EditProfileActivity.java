package com.example.syncreadyapp.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.databinding.EditProfileBinding;
import com.example.syncreadyapp.models.repositories.RepositoryResponse;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.example.syncreadyapp.models.userupdate.UserUpdate;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.userregistervalidate.ResponseValidateRegister;
import com.example.syncreadyapp.userregistervalidate.ValidateRegisterModel;
import com.example.syncreadyapp.viewmodels.GroupActivityViewModel;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.example.syncreadyapp.viewmodels.MainActivityViewModel;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity {
    private EditProfileBinding editProfileBinding;
    private HomeActivityViewModel homeActivityViewModel;
    private MainActivityViewModel mainActivityViewModel;
    private GroupActivityViewModel groupActivityViewModel;
    private User user;
    private UserUpdate userUpdate;
    private AlertDialog showImageUploadProcessing;

    private boolean hasError = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivityViewModel = ViewModelProviders.of(EditProfileActivity.this).get(HomeActivityViewModel.class);
        mainActivityViewModel = ViewModelProviders.of(EditProfileActivity.this).get(MainActivityViewModel.class);
        groupActivityViewModel = ViewModelProviders.of(this).get(GroupActivityViewModel.class);

        Bundle bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));

        // loading user data
        homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUserDataObserver);
    }

    private final Observer<ResponseUser> getUserDataObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {
            if (responseUser != null) {
                editProfileBinding = DataBindingUtil.setContentView(EditProfileActivity.this, R.layout.edit_profile);
                showImageUploadProcessing = Utils.showImageUploadProcessing(EditProfileActivity.this);
                editProfileBinding.setLifecycleOwner(EditProfileActivity.this);

                user = responseUser.getData().get(0);
                userUpdate = new UserUpdate();

                userUpdate.setFullname(user.getFullname());
                userUpdate.setAddress(user.getAddress());
                userUpdate.setEmail(user.getEmail());
                userUpdate.setContacto(user.getTelephone());
                userUpdate.setCc(user.getCitizencard());
                userUpdate.setType(user.getTypeOfUserUuidTypeOfUsers());
                userUpdate.setSecretUUIDToUpdate(user.getPkUuid());

                editProfileBinding.setUserUpdate(userUpdate);
                editProfileBinding.editButtonUpdate.setOnClickListener(verifyFields);

                Picasso.get()
                        .load(RetrofitInstance.BASE_URL + "public/files/" + user.getImage())
                        .placeholder(R.drawable.loading)
                        .into(editProfileBinding.userImageEdit);

                editProfileBinding.floatingAtionButtonUserImageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.requestFile(EditProfileActivity.this, true,false);
                    }
                });

                editProfileBinding.editProfileToolbarInclude.editToolbar.setTitle(user.getFullname());

                editProfileBinding.editProfileToolbarInclude.editToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else {
                Utils.showInternalUnavailableConnectionToServerAlert(EditProfileActivity.this).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.ApplicationLogout(EditProfileActivity.this);
                    }
                }).show();
            }
        }
    };

    private final Observer<RepositoryResponse> validateRegisterModelObserver = new Observer<RepositoryResponse>() {
        @Override
        public void onChanged(RepositoryResponse repositoryResponse) {
            if (repositoryResponse != null) {
                ResponseValidateRegister responseValidateRegister = (ResponseValidateRegister) repositoryResponse.getGenericMutableLiveData().getValue();

                // Email is invalid? Verify if previous email was from user logged. Otherwise, email is truly invalid
                if (!userUpdate.getEmail().equals(user.getEmail())) {
                    editProfileBinding.editTextEmailEditLayout.setErrorEnabled(true);
                    editProfileBinding.editTextEmailEditLayout.setError("Email indicado já existente!");
                    hasError = true;
                } else {
                    editProfileBinding.editTextEmailEditLayout.setErrorEnabled(false);
                    editProfileBinding.editTextEmailEditLayout.setError("");
                }

                if (!responseValidateRegister.getIsCCValid()) {
                    editProfileBinding.editTextNCCEditLayout.setErrorEnabled(true);
                    editProfileBinding.editTextNCCEditLayout.setError("Cartão de cidadão inválido");
                    hasError = true;
                } else {
                    editProfileBinding.editTextNCCEditLayout.setErrorEnabled(false);
                    editProfileBinding.editTextNCCEditLayout.setError("");
                }

                if (!hasError) {
                    // update user
                    mainActivityViewModel.getUserUpdate(userUpdate).observe(EditProfileActivity.this, getUserUpdateObserver);
                }
            }
        }
    };

    private final Observer<JsonObject> getUserUpdateObserver = new Observer<JsonObject>() {
        @Override
        public void onChanged(JsonObject jsonObject) {
            if (jsonObject != null && jsonObject.get("ok").getAsString().equals("true")) {
                Utils.showUserUpdated(EditProfileActivity.this)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                .show();
            }
        }
    };

     private final Observer<JsonObject> getUploadImage = new Observer<JsonObject>() {
        @Override
        public void onChanged(JsonObject responseBody) {
            if (responseBody != null) {
                String filePath = responseBody.get("filename").getAsString();
                homeActivityViewModel.getUpdateUserImage(filePath, homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                        .observe(EditProfileActivity.this, getUserUpdateObserver);
            }
        }
    };

    private final View.OnClickListener verifyFields = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hasError = false;

            if (editProfileBinding.editTextNCompletoEdit.getText().toString().isEmpty()) {
                editProfileBinding.editTextNCompletoEditLayout.setErrorEnabled(true);
                editProfileBinding.editTextNCompletoEditLayout.setError("Campo vazio!");
                hasError = true;
            } else {
                editProfileBinding.editTextNCompletoEditLayout.setErrorEnabled(false);
                editProfileBinding.editTextNCompletoEditLayout.setError("");
            }

            if (editProfileBinding.editTextEmailEdit.getText().toString().isEmpty()) {
                editProfileBinding.editTextEmailEditLayout.setErrorEnabled(true);
                editProfileBinding.editTextEmailEditLayout.setError("Campo vazio!");
                hasError = true;
            } else if (!Utils.isEmailPreValid(editProfileBinding.editTextEmailEdit.getText().toString())){
                editProfileBinding.editTextEmailEditLayout.setErrorEnabled(true);
                editProfileBinding.editTextEmailEditLayout.setError("Email encontra-se num formato inválido!");
                hasError = true;
            } else {
                editProfileBinding.editTextEmailEditLayout.setErrorEnabled(false);
                editProfileBinding.editTextEmailEditLayout.setError("");
            }

            if (editProfileBinding.editTextNCCEdit.getText().toString().isEmpty()) {
                editProfileBinding.editTextNCCEditLayout.setErrorEnabled(true);
                editProfileBinding.editTextNCCEditLayout.setError("Campo vazio!");
                hasError = true;
            } else {
                editProfileBinding.editTextNCCEditLayout.setErrorEnabled(false);
                editProfileBinding.editTextNCCEditLayout.setError("");
            }

            if (editProfileBinding.editTextAddressEdit.getText().toString().isEmpty()) {
                editProfileBinding.editTextAddressEditLayout.setErrorEnabled(true);
                editProfileBinding.editTextAddressEditLayout.setError("Campo vazio!");
                hasError = true;
            } else {
                editProfileBinding.editTextAddressEditLayout.setErrorEnabled(false);
                editProfileBinding.editTextAddressEditLayout.setError("");
            }

            if (editProfileBinding.editTextTelemrEdit.getText().toString().isEmpty()) {
                editProfileBinding.editTextTelemrEditLayout.setErrorEnabled(true);
                editProfileBinding.editTextTelemrEditLayout.setError("Campo vazio!");
                hasError = true;
            } else {
                editProfileBinding.editTextTelemrEditLayout.setErrorEnabled(false);
                editProfileBinding.editTextTelemrEditLayout.setError("");
            }

            if (editProfileBinding.editTextNewPasswordEdit.getText().toString().isEmpty()) {
                userUpdate.setPassword(user.getPassword());
            }

            if (!hasError) {
                mainActivityViewModel.email.setValue(userUpdate.getEmail());
                mainActivityViewModel.cc.setValue(userUpdate.getCc());
                mainActivityViewModel.username.setValue(user.getNickname());
                mainActivityViewModel.getValidateRegister().observe(EditProfileActivity.this, validateRegisterModelObserver);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            File photoFile = null;
            Bitmap bitmap = null;
            FileOutputStream fileOutputStream = null;

            try {
                photoFile = Utils.createImageFile(EditProfileActivity.this);
                fileOutputStream = new FileOutputStream(photoFile);

                bitmap = BitmapFactory.decodeFile(Utils.getRealPathFromURI(EditProfileActivity.this, data.getData()));

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

            groupActivityViewModel.uploadImage(fileToUpload, description, homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                    .observe(this, getUploadImage);
        }
    }
}
