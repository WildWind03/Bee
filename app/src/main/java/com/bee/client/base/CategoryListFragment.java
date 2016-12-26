package com.bee.client.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.bee.client.entity.Category;
import com.bee.client.entity.Comment;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;

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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
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

    }

    @Override
    protected int getLayout() {
        return R.layout.category_list;
    }
}
