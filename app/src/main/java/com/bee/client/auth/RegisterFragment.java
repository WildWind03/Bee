package com.bee.client.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.bee.client.util.ValidationResult;
import com.bee.client.util.ValidatorUtils;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;

import java.util.logging.Logger;

public class RegisterFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(RegisterFragment.class.getName());

    @BindView(R.id.input_email)
    protected EditText inputEmail;

    @BindView(R.id.input_password)
    protected EditText inputPassword;

    @BindView(R.id.input_name)
    protected EditText inputName;

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.sign_up_str);
    }

    @Override
    protected int getLayout() {
        return R.layout.register;
    }

    @OnClick(R.id.link_login)
    void onLinkLoginClick() {
        EventBus.getDefault().post(new ToLoginFragmentEvent());
    }

    @OnClick(R.id.btn_signup)
    void onClick() {
        ValidationResult validationResult = new ValidationResult();

        ValidatorUtils.validatePassword(inputPassword.getText().toString(), validationResult);
        ValidatorUtils.validateEmail(inputEmail.getText().toString(), validationResult);
        ValidatorUtils.validateUsername(inputEmail.getText().toString(), validationResult);

        if (validationResult.isNoProblems()) {
            showMessage(getString(R.string.title_error), getString(R.string.text_error));
        } else {
            showErrorMessage(validationResult);
        }
    }

    protected void showErrorMessage(ValidationResult validationResult) {
        StringBuilder resultMessage = new StringBuilder();

        for (ValidationResult.ValidationProblem validationProblem : validationResult.getValidationProblems()) {
            resultMessage.append(ValidatorUtils.getMessage(getContext(), validationProblem));
            resultMessage.append("\n");
        }

        showMessage(R.string.invalid_data, resultMessage.toString().trim());
    }
}
