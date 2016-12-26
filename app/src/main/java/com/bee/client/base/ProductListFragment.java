package com.bee.client.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.bee.client.entity.Category;
import com.bee.client.entity.Product;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ProductListFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(ProductListFragment.class.getName());
    private static final String PRODUCT_TAG = "PRODUCT_TAG";

    @BindView(R.id.category_list)
    protected RecyclerView productList;

    private class ProductListAdapter extends RecyclerView.Adapter<ProductListFragment.ProductListAdapter.ViewHolder> {

        private final List<Product> products;

        public ProductListAdapter(List<Product> products) {
            this.products = products;
        }

        @Override
        public ProductListFragment.ProductListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = productList.getChildLayoutPosition(view);
                    Product product = products.get(itemPosition);
                    EventBus.getDefault().post(product);
                }
            });
            return new ProductListFragment.ProductListAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ProductListFragment.ProductListAdapter.ViewHolder holder, int position) {
            Product product = products.get(position);

            holder.productName.setText(product.getName());
            holder.organisationName.setText(product.getOrganisation());
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView productName;
            private TextView organisationName;

            public ViewHolder(View itemView) {
                super(itemView);
                productName = (TextView) itemView.findViewById(R.id.product_name);
                organisationName = (TextView) itemView.findViewById(R.id.organisation_name);
            }
        }
    }

    public static ProductListFragment newInstance(Product[] products) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(PRODUCT_TAG, products);
        productListFragment.setArguments(bundle);

        return productListFragment;
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        Product[] products = (Product[]) getArguments().get(PRODUCT_TAG);
        productList.setAdapter(new ProductListFragment.ProductListAdapter(Arrays.asList(products)));
        productList.setLayoutManager(new LinearLayoutManager(getContext()));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Choose product");
    }

    @Override
    protected int getLayout() {
        return R.layout.category_list;
    }
}
