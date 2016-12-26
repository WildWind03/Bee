package com.bee.client.auth;

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
    protected int getLayout() {
        return R.layout.register;
    }

    @OnClick(R.id.link_login)
    void onLinkLoginClick() {
        EventBus.getDefault().post(new ToLoginFragmentEvent());
    }
}
