package com.bee.client.kernel;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.bee.client.auth.LoginFragment;
import com.bee.client.auth.RegisterFragment;
import com.bee.client.auth.ToLoginFragmentEvent;
import com.bee.client.auth.ToRegisterFragmentEvent;
import com.bee.client.base.CategoryListFragment;
import com.bee.client.base.ProductInfoFragment;
import com.bee.client.base.ProductListFragment;
import com.bee.client.entity.Category;
import com.bee.client.entity.Product;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.nsu.alexander.apptemplate.BaseActivity;
import com.nsu.alexander.apptemplate.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IProfile iProfile = new ProfileDrawerItem()
                .withEmail(getString(R.string.email_profile))
                .withName(getString(R.string.username_profile));

        PrimaryDrawerItem listOfCategoriesItem = new PrimaryDrawerItem();
        listOfCategoriesItem
                .withTextColorRes(android.R.color.black)
                .withSelectedTextColorRes(R.color.colorPrimaryDark)
                .withName(R.string.categories_item)
                .withIcon(R.drawable.ic_list_black_24dp);

        toolbar.setTitle(getString(R.string.choose_category_str));
        setSupportActionBar(toolbar);

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTextColorRes(android.R.color.black)
                .addProfiles(iProfile)
                .withSelectionListEnabled(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        onAccountHeaderClicked();
                        return false;
                    }
                })
                .build();

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(listOfCategoriesItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        onCategoryListClicked();
                        return false;
                    }
                }).build();

        toolbar.setCollapsible(true);

        if (null == getSupportFragmentManager().findFragmentByTag(CategoryListFragment.class.getName())) {
            CategoryListFragment categoryListFragment = CategoryListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_for_fragments, categoryListFragment, CategoryListFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    private void onCategoryListClicked() {
        CategoryListFragment categoryListFragment = CategoryListFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_fragments, categoryListFragment, ProductInfoFragment.class.getName())
                .addToBackStack(ProductInfoFragment.class.getName())
                .commit();
    }

    private void onAccountHeaderClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_fragments, new LoginFragment(), LoginFragment.class.getName())
                .addToBackStack(LoginFragment.class.getName())
                .commit();
    }

    @Subscribe
    void onToLoginFragmentEvent(ToLoginFragmentEvent toLoginFragmentEvent) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_fragments, new LoginFragment(), LoginFragment.class.getName())
                .addToBackStack(LoginFragment.class.getName())
                .commit();
    }

    @Subscribe
    void onRegisterFragmentEvent(ToRegisterFragmentEvent toRegisterFragmentEven) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_fragments, new RegisterFragment(), RegisterFragment.class.getName())
                .addToBackStack(RegisterFragment.class.getName())
                .commit();
    }

    @Subscribe
    void onCategorySelected(Category category) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_fragments, ProductListFragment.newInstance(category), ProductListFragment.class.getName())
                .addToBackStack(ProductListFragment.class.getName())
                .commit();
    }

    @Subscribe
    void onProductSelected(Product product) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_fragments, ProductInfoFragment.newInstance(product), ProductInfoFragment.class.getName())
                .addToBackStack(ProductInfoFragment.class.getName())
                .commit();
    }
}