package com.bee.client.auth;

import butterknife.OnClick;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;

import java.util.logging.Logger;

public class LoginFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(LoginFragment.class.getName());

    @Override
    protected int getLayout() {
        return R.layout.login;
    }

    @OnClick(R.id.link_signup)
    void onLinkSignUpClicked() {
        EventBus.getDefault().post(new ToRegisterFragmentEvent());
    }
}
