package com.bee.client.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.OnClick;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;

import java.util.logging.Logger;

public class LoginFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(LoginFragment.class.getName());

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Sign in");
    }

    @Override
    protected int getLayout() {
        return R.layout.login;
    }

    @OnClick(R.id.link_signup)
    void onLinkSignUpClicked() {
        EventBus.getDefault().post(new ToRegisterFragmentEvent());
    }

    @OnClick(R.id.btn_login)
    void onClick() {
        showMessage(getActivity().getString(R.string.title_error), getActivity().getString(R.string.text_error));
    }
}
