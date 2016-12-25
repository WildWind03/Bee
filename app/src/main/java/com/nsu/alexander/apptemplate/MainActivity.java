package com.nsu.alexander.apptemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bee.client.base.ProductInfoFragment;
import com.bee.client.entity.Comment;
import com.bee.client.entity.Product;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == getSupportFragmentManager().findFragmentByTag(ProductInfoFragment.class.getName())) {
            Comment[] comments = new Comment[] {new Comment("Disgusting", "user10101", 0), new Comment("The best latte in Novosibirsk", "Ivan", 6), new Comment("The worst coffee I have ever tasted", "Roman", 0)};
            Product product = new Product("Latte", "Can be made with any syrup you like", "Kuzina", 80);

            ProductInfoFragment productInfoFragment = ProductInfoFragment.newInstance(new ArrayList<>(Arrays.asList(comments)), product);
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, productInfoFragment, ProductInfoFragment.class.getName()).commit();
        }
    }
}
