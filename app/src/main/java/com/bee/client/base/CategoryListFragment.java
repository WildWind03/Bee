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
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.logging.Logger;

public class CategoryListFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(CategoryListFragment.class.getName());

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
                    EventBus.getDefault().post(category);
                }
            };

            v.setOnClickListener(listener);

            final TextView textView = (TextView) v.findViewById(R.id.category_name);

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

                    EventBus.getDefault().post(categories.get(pos));
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

    public static CategoryListFragment newInstance() {
        return new CategoryListFragment();
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        LoadCategoriesService loadCategoriesService = LoadCategoriesSingleton.getInstance();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.choose_category_str);

        loadCategoriesService
                .loadCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Category> categories) {
                        categoryList.setAdapter(new CategoryListAdapter(categories));
                        categoryList.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });

    }

    @Override
    protected int getLayout() {
        return R.layout.category_list;
    }
}
