package com.bee.client.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.bee.client.entity.Category;
import com.bee.client.entity.Product;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.logging.Logger;

public class ProductListFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(ProductListFragment.class.getName());
    private static final String CATEGORY_TAG = "CATEGORY_TAG";

    @BindView(R.id.category_list)
    protected RecyclerView productList;

    private class ProductListAdapter extends RecyclerView.Adapter<ProductListFragment.ProductListAdapter.ViewHolder> {

        private final List<Product> products;

        public ProductListAdapter(List<Product> products) {
            this.products = products;
        }

        @Override
        public ProductListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
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

    public static ProductListFragment newInstance(Category category) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CATEGORY_TAG, category);
        productListFragment.setArguments(bundle);

        return productListFragment;
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        Category category = (Category) getArguments().get(CATEGORY_TAG);

        LoadProductsInCategoryService loadProductsInCategoryService = LoadProductsInCategorySingleton.getInstance();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.choose_product_str);

        loadProductsInCategoryService
                .loadProducts(category.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<Product>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        productList.setAdapter(new ProductListAdapter(products));
                        productList.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.category_list;
    }
}
