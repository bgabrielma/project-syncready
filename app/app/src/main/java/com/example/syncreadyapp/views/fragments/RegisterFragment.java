package com.example.syncreadyapp.views.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.syncreadyapp.R;
import com.example.syncreadyapp.Utils;
import com.example.syncreadyapp.databinding.RegisterBinding;
import com.example.syncreadyapp.models.registermodel.RegisterModel;
import com.example.syncreadyapp.models.repositories.RepositoryResponse;
import com.example.syncreadyapp.models.userinsert.ResponseUserInsert;
import com.example.syncreadyapp.userregistervalidate.ValidateRegisterModel;
import com.example.syncreadyapp.viewmodels.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {

    private RegisterBinding registerBinding;
    private MainActivityViewModel mainActivityViewModel;

    private View prevView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // prevent fragment to creating again the whole process. Prevent also execute "last operation made before switching fragments"
        if (prevView != null) {
            return prevView;
        }

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        registerBinding = DataBindingUtil.inflate(inflater, R.layout.register, container, false);
        registerBinding.setLifecycleOwner(this);
        registerBinding.setMainActivityViewModel(mainActivityViewModel);

        mainActivityViewModel.validadeRegisterFields().observe(this, new Observer<RegisterModel>() {
            @Override
            public void onChanged(RegisterModel registerModel) {
                boolean hasError = false;

                if(TextUtils.isEmpty(registerModel.getFullname())) {
                    registerBinding.txtInputNameRegister.setError("Campo em falta");
                    registerBinding.txtInputNameRegister.requestFocus();
                    hasError = true;
                } else registerBinding.txtInputNameRegister.setErrorEnabled(false);

                if(TextUtils.isEmpty(registerModel.getEmail())) {
                    registerBinding.txtInputEmailRegister.setError("Campo em falta");
                    registerBinding.txtInputEmailRegister.requestFocus();
                    hasError = true;
                } else registerBinding.txtInputEmailRegister.setErrorEnabled(false);

                if(TextUtils.isEmpty(registerModel.getAddress())) {
                    registerBinding.txtInputAddressRegister.setError("Campo em falta");
                    registerBinding.txtInputAddressRegister.requestFocus();
                    hasError = true;
                } else registerBinding.txtInputAddressRegister.setErrorEnabled(false);

                if(TextUtils.isEmpty(registerModel.getCc())) {
                    registerBinding.txtInputCCRegister.setError("Campo em falta");
                    registerBinding.txtInputCCRegister.requestFocus();
                    hasError = true;
                } else registerBinding.txtInputCCRegister.setErrorEnabled(false);

                if(TextUtils.isEmpty(registerModel.getContacto())) {
                    registerBinding.txtInputTelRegister.setError("Campo em falta");
                    registerBinding.txtInputTelRegister.requestFocus();
                    hasError = true;
                } else registerBinding.txtInputTelRegister.setErrorEnabled(false);

                if(TextUtils.isEmpty(registerModel.getUsername())) {
                    registerBinding.txtInputUserRegister.setError("Campo em falta");
                    registerBinding.txtInputUserRegister.requestFocus();
                    hasError = true;
                } else registerBinding.txtInputUserRegister.setErrorEnabled(false);

                if(TextUtils.isEmpty(registerModel.getPassword())) {
                    registerBinding.txtInputPassRegister.setError("Campo em falta");
                    registerBinding.txtInputPassRegister.requestFocus();
                    hasError = true;
                } else {
                    registerBinding.txtInputUserRegister.setErrorEnabled(false);
                }

                if(TextUtils.isEmpty(registerModel.getConfirmPassword())) {
                    registerBinding.txtInputConfirmarPassRegister.setError("Campo em falta");
                    registerBinding.txtInputConfirmarPassRegister.requestFocus();
                    hasError = true;
                } else {
                    registerBinding.txtInputConfirmarPassRegister.setErrorEnabled(false);
                }

                /*
                if(registerModel.getPassword() != registerModel.getConfirmPassword()) {

                    registerBinding.txtInputPassRegister.setError("A password não coincide com a confirmação");
                    registerBinding.txtInputConfirmarPassRegister.setError("A confirmação não coincide com a palavra-passe");

                    registerBinding.txtInputConfirmarPassRegister.requestFocus();
                    hasError = true;
                } */
                if(!hasError) {

                    mainActivityViewModel.getValidateRegister().observe(registerBinding.getLifecycleOwner(), new Observer<RepositoryResponse>() {
                        @Override
                        public void onChanged(RepositoryResponse repositoryResponse) {
                            ValidateRegisterModel validateRegisterModel = (ValidateRegisterModel) repositoryResponse.getGenericMutableLiveData().getValue();

                            if(!Boolean.valueOf(validateRegisterModel.getCc())) {
                                registerBinding.txtInputCCRegister.setError("O número do cartão de contribuinte é inválido!");
                                registerBinding.txtInputCCRegister.requestFocus();
                            }
                        }
                    });

                    // mainActivityViewModel.getUserInsert().observe(registerBinding.getLifecycleOwner(), observer);
                    // registerBinding.btnCriarConta.setEnabled(false);
                }
            }
        });

        // store "actual" preview for future backs in this fragment
        prevView = registerBinding.getRoot();

        return registerBinding.getRoot();
    }

    final Observer<RepositoryResponse> observer = new Observer<RepositoryResponse>() {
        @Override
        public void onChanged(RepositoryResponse registerUserMutableLiveData) {

            ResponseUserInsert responseUserInsert = (ResponseUserInsert) registerUserMutableLiveData.getGenericMutableLiveData().getValue();
            Boolean isNetworkTrouble = registerUserMutableLiveData.getIsNetworkLiveData().getValue();

            if (responseUserInsert.getOk()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.ic_action_add);
                builder.setCancelable(false);
                builder.setTitle("Utilizador inserido");
                builder.setMessage("O utilizador foi criado com sucesso. Verifque o seu email para continuar");
                builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
                builder.show();
            } else if (!isNetworkTrouble.booleanValue()) {
                Snackbar.make(registerBinding.getRoot(), "Ocorreu um erro interno ao tentar registar o utilizador", Snackbar.LENGTH_LONG).show();
            }

            if (isNetworkTrouble.booleanValue()) {
                Utils.showInternalUnavailableConnectionToServerAlert(getActivity());
            }
            registerBinding.btnCriarConta.setEnabled(true);
        }
    };
}
