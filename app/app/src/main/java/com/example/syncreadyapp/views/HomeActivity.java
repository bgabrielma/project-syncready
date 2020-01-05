package com.example.syncreadyapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.databinding.MainBinding;
import com.example.syncreadyapp.models.usermodel.ResponseUser;
import com.example.syncreadyapp.viewmodels.HomeActivityViewModel;
import com.example.syncreadyapp.views.fragments.AccountFragment;
import com.example.syncreadyapp.views.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private HomeActivityViewModel homeActivityViewModel;
    private MainBinding mainBinding;

    private MenuItem lastMenuItemClicked;
    private boolean isRoomItemClicked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivityViewModel = ViewModelProviders.of(HomeActivity.this).get(HomeActivityViewModel.class);

        Bundle bundle = this.getIntent().getExtras();
        homeActivityViewModel.uuidMutableLiveData.setValue(bundle.getString("sycnready_user_uuid", null));
        homeActivityViewModel.tokenAccessMutableLiveData.setValue("Bearer " + bundle.getString("syncready_user_token_access", null));

        // loading user data
        homeActivityViewModel.getUserData(homeActivityViewModel.uuidMutableLiveData.getValue(), homeActivityViewModel.tokenAccessMutableLiveData.getValue())
                .observe(this, getUserDataObserver);
    }

    /* Observers */
    private final Observer<ResponseUser> getUserDataObserver = new Observer<ResponseUser>() {
        @Override
        public void onChanged(ResponseUser responseUser) {

            if (responseUser.getData() != null) {
                homeActivityViewModel.userMutableLiveData.setValue(responseUser.getData().get(0));

                mainBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.main);

                // set click listeners for toolbars
                setToolbarClickListeners();

                // set click listeners for bottom navigation's item
                setBottomNavClickListeners();

                mainBinding.BottomNavHome.bottomNavigationView.setSelectedItemId(R.id.HomeIcon);
            }
        }
    };

    private void setToolbarClickListeners() {

        mainBinding.ToolbarHome.toolbarMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_menu_action_logout: {
                        Toast.makeText(getApplicationContext(), "Logout option clicked!", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                return false;
            }
        });

        mainBinding.ToolbarHome.toolbarQrcode.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                startActivity(intent);

                return false;
            }
        });
    }

    private void setBottomNavClickListeners() {
        // do stuff on future
        mainBinding.BottomNavHome.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ChatIcon: {
                        item.setCheckable(false);
                        isRoomItemClicked = true;

                        Intent roomIntent = new Intent(getApplicationContext(), RoomActivity.class);

                        if (HomeActivity.this.getIntent().getExtras() != null) {
                            roomIntent.putExtras(HomeActivity.this.getIntent().getExtras());
                        }

                        startActivity(roomIntent);
                        break;
                    }
                    case R.id.HomeIcon: {
                        lastMenuItemClicked = item;
                        changeFragments(R.id.HomeIcon);
                        break;
                    }
                    case R.id.UserIcon: {
                        lastMenuItemClicked = item;
                        changeFragments(R.id.UserIcon);
                        break;
                    }
                    case R.id.AlertIcon: {
                        lastMenuItemClicked = item;

                        break;
                    }
                    default: {
                        Toast.makeText(getApplicationContext(), "You clicked on " + item.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
    }

    private void changeFragments(int layoutId) {
        Fragment fragment = null;
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();

        switch (layoutId) {
            case R.id.HomeIcon:
                fragment = fManager.findFragmentByTag(HomeFragment.class.getName());
                if (fragment == null) {
                    fTransaction.add(R.id.mainContent, new HomeFragment(), HomeFragment.class.getName());
                }
                else { // re-use the old fragment
                    fTransaction.replace(R.id.mainContent, fragment, HomeFragment.class.getName());
                }
                break;
            case R.id.UserIcon:
                fragment = fManager.findFragmentByTag(AccountFragment.class.getName());
                if (fragment == null) {
                    fTransaction.add(R.id.mainContent, new AccountFragment(), AccountFragment.class.getName());
                }
                else { // re-use the old fragment
                    fTransaction.replace(R.id.mainContent, fragment, AccountFragment.class.getName());
                }
                break;
        }

        fTransaction
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mainBinding != null && isRoomItemClicked) {
            if(lastMenuItemClicked == null) {
                lastMenuItemClicked = mainBinding.BottomNavHome.bottomNavigationView.getMenu().getItem(0);
            }

            lastMenuItemClicked.setChecked(true);
            isRoomItemClicked = false;
        }
    }
}
