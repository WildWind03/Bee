package com.bee.client.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;

import java.util.logging.Logger;

public class RegisterFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(RegisterFragment.class.getName());

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Sign up");
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
        showMessage(getString(R.string.title_error), getString(R.string.text_error));
    }
}
