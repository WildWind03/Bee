package com.bee.client.kernel;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.bee.client.auth.LoginFragment;
import com.bee.client.auth.RegisterFragment;
import com.bee.client.auth.ToLoginFragmentEvent;
import com.bee.client.auth.ToRegisterFragmentEvent;
import com.bee.client.base.ProductInfoFragment;
import com.bee.client.entity.Comment;
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

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity {

    private final static int LOGIN_ID = 0;
    private final static int CATEGORY_LIST_ID = 1;

    private AccountHeader accountHeader;
    private Drawer drawer;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IProfile iProfile = new ProfileDrawerItem()
                .withEmail("Email")
                .withName("Username");

        PrimaryDrawerItem listOfCategoriesItem = new PrimaryDrawerItem();
        listOfCategoriesItem
                .withIdentifier(CATEGORY_LIST_ID)
                .withTextColorRes(android.R.color.black)
                .withSelectedTextColorRes(R.color.colorPrimaryDark)
                .withName(R.string.categories_item)
                .withIcon(R.drawable.ic_list_black_24dp);

        setSupportActionBar(toolbar);

        accountHeader = new AccountHeaderBuilder()
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

        drawer = new DrawerBuilder()
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

        if (null == getSupportFragmentManager().findFragmentByTag(ProductInfoFragment.class.getName())) {
            Comment[] comments = new Comment[]{new Comment("Disgusting", "user10101", 0), new Comment("The best latte in Novosibirsk", "Ivan", 6), new Comment("The worst coffee I have ever tasted", "Roman", 0)};
            Product product = new Product("Latte", "Can be made with any syrup you like", "Kuzina", 80);

            ProductInfoFragment productInfoFragment = ProductInfoFragment.newInstance(new ArrayList<>(Arrays.asList(comments)), product);
            getSupportFragmentManager().beginTransaction().add(R.id.container_for_fragments, productInfoFragment, ProductInfoFragment.class.getName()).commit();
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
        if (null == getSupportFragmentManager().findFragmentByTag(ProductInfoFragment.class.getName())) {
            Comment[] comments = new Comment[]{new Comment("Disgusting", "user10101", 0), new Comment("The best latte in Novosibirsk", "Ivan", 6), new Comment("The worst coffee I have ever tasted", "Roman", 0)};
            Product product = new Product("Latte", "Can be made with any syrup you like", "Kuzina", 80);

            ProductInfoFragment productInfoFragment = ProductInfoFragment.newInstance(new ArrayList<>(Arrays.asList(comments)), product);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_for_fragments, productInfoFragment, ProductInfoFragment.class.getName()).commit();
        }
    }

    private void onAccountHeaderClicked() {
        if (null == getSupportFragmentManager().findFragmentByTag(LoginFragment.class.getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_for_fragments, new LoginFragment()).commit();
            toolbar.setTitle("Sign in");
        }
    }

    @Subscribe
    void onToLoginFragmentEvent(ToLoginFragmentEvent toLoginFragmentEvent) {
        if (null == getSupportFragmentManager().findFragmentByTag(LoginFragment.class.getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_for_fragments, new LoginFragment()).commit();
            toolbar.setTitle("Sign in");
        }
    }

    @Subscribe
    void onRegisterFragmentEvent(ToRegisterFragmentEvent toRegisterFragmentEven) {
        if (null == getSupportFragmentManager().findFragmentByTag(LoginFragment.class.getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_for_fragments, new RegisterFragment()).commit();
            toolbar.setTitle("Sign up");
        }
    }
}