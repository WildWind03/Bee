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

public class CategoryListFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(CategoryListFragment.class.getName());

    private static final String CATEGORIES_TAG = "CATEGORIES_TAG";

    @BindView(R.id.category_list)
    protected RecyclerView categoryList;

    private class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

        private final List<Category> categories;

        public CategoryListAdapter(List<Category> categories) {
            this.categories = categories;
        }

        @Override
        public CategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = categoryList.getChildLayoutPosition(view);
                    Category category = categories.get(itemPosition);
                    EventBus.getDefault().post(new CategorySelectedItemEvent(category.getId()));
                }
            };

            v.setOnClickListener(listener);

            final TextView textView =  (TextView) v.findViewById(R.id.category_name);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView1 = (TextView) view;

                    String value = textView1.getText().toString();

                    int pos = 0;
                    for (Category category : categories) {
                        if (category.getName().equals(value)) {
                            break;
                        }

                        pos++;
                    }

                    EventBus.getDefault().post(new CategorySelectedItemEvent(categories.get(pos).getId()));
                }
            });
            return new CategoryListAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CategoryListAdapter.ViewHolder holder, int position) {
            Category category = categories.get(position);
            holder.categoryName.setText(category.getName());
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView categoryName;

            public ViewHolder(View itemView) {
                super(itemView);
                categoryName = (TextView) itemView.findViewById(R.id.category_name);
            }
        }
    }

    public static CategoryListFragment newInstance(Category[] categories) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(CATEGORIES_TAG, categories);

        CategoryListFragment categoryItemFragment = new CategoryListFragment();
        categoryItemFragment.setArguments(bundle);

        return categoryItemFragment;
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        Category[] categories = (Category[]) getArguments().get(CATEGORIES_TAG);
        categoryList.setAdapter(new CategoryListAdapter(Arrays.asList(categories)));
        categoryList.setLayoutManager(new LinearLayoutManager(getContext()));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Choose category");

    }

    @Override
    protected int getLayout() {
        return R.layout.category_list;
    }
}
