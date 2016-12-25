package com.bee.client.base;

import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;

import java.util.logging.Logger;

public class CategoryItemFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(CategoryItemFragment.class.getName());

    public static void newInstance() {

    }

    @Override
    protected int getLayout() {
        return R.layout.product_info_layout;
    }
}
