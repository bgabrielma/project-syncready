package com.example.syncreadyapp.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.databinding.EditProfileBinding;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.models.usermodel.User;
import com.example.syncreadyapp.models.userupdate.UserUpdate;
import com.example.syncreadyapp.services.RetrofitInstance;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {
    private EditProfileBinding editProfileBinding;
    private HomeActivityViewModel homeActivityViewModel;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivityViewModel = ViewModelProviders.of(EditProfileActivity.this).get(HomeActivityViewModel.class);

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

                user = responseUser.getData().get(0);
                UserUpdate userUpdate = new UserUpdate();

                userUpdate.setFullname(user.getFullname());
                userUpdate.setAddress(user.getAddress());
                userUpdate.setEmail(user.getEmail());
                userUpdate.setContacto(user.getTelephone());
                userUpdate.setCc(user.getCitizencard());
                userUpdate.setType(user.getTypeOfUserUuidTypeOfUsers());
                userUpdate.setSecretUUIDToUpdate(user.getPkUuid());

                editProfileBinding.setUserUpdate(userUpdate);

                Picasso.get()
                        .load(RetrofitInstance.BASE_URL + "public/files/" + user.getImage())
                        .placeholder(R.drawable.loading)
                        .into(editProfileBinding.userImageEdit);

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
}
