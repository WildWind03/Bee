package com.bee.client.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import com.bee.client.entity.Comment;
import com.bee.client.entity.Product;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ProductInfoFragment  extends BaseFragment {
    private static final Logger logger = Logger.getLogger(ProductInfoFragment.class.getName());
    private static final String COMMENTS_TAG = "COMMENTS_TAG";
    private static final String PRODUCT_TAG = "PRODUCT_TAG";

    @BindView(R.id.list_of_comments)
    protected RecyclerView listOfComments;

    @BindView(R.id.main_appbar)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.organisation_name)
    protected TextView organisationName;

    @BindView(R.id.average_rate)
    protected TextView averageRate;

    @BindView(R.id.description)
    protected TextView description;

    public static ProductInfoFragment newInstance(ArrayList<Comment> comments, Product product) {
        ProductInfoFragment productInfoFragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(COMMENTS_TAG, comments);
        args.putParcelable(PRODUCT_TAG, product);
        productInfoFragment.setArguments(args);

        return productInfoFragment;
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        ArrayList<Comment> comments = getArguments().getParcelableArrayList(COMMENTS_TAG);
        Product product = getArguments().getParcelable(PRODUCT_TAG);
        organisationName.setText(getString(R.string.organisation_pattern, getString(R.string.organisation_string), product.getOrganisation()));
        averageRate.setText(getString(R.string.product_rating_pattern, getString(R.string.rating), product.getAverageRate()));
        description.setText(product.getDescription());

        CommentAdapter commentAdapter = new CommentAdapter(comments);

        listOfComments.setAdapter(commentAdapter);
        listOfComments.setLayoutManager(new LinearLayoutManager(getContext()));

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.organisation_pattern, product.getOrganisation(), product.getName()));
    }

    @Override
    protected int getLayout() {
        return R.layout.product_info_layout;
    }
}
