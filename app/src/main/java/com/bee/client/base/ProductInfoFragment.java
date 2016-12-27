package com.bee.client.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.nsu.alexander.apptemplate.BaseFragment;
import com.nsu.alexander.apptemplate.R;
import com.bee.client.entity.Comment;
import com.bee.client.entity.Product;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ProductInfoFragment  extends BaseFragment implements SensorEventListener {
    private static final Logger logger = Logger.getLogger(ProductInfoFragment.class.getName());
    private final static String DEFAULT_SITE = "http://androidtraining.noveogroup.com";

    private static final String PRODUCT_TAG = "PRODUCT_TAG";

    private static final float SHAKE_THRESHOLD = 2.7F;
    private static final int MIN_PERIOD_BETWEEN_SHAKE_EVENTS = 10;
    private static final int SHAKE_STOP_TIME = 3000;

    private long lastShakeEventTime;
    private AtomicInteger shakeCount = new AtomicInteger(0);

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private AsyncTask<Void, Integer, Void> asyncTask;

    @BindView(R.id.toolbar_image)
    protected ImageView productImage;

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

    private class ColorAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                while(shakeCount.get() > 0 && !isCancelled()) {
                    Thread.sleep(200);
                    shakeCount.decrementAndGet();
                    publishProgress();
                }
            } catch (InterruptedException e) {
                logger.info("Interrupted");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            final int newColor = 255 - shakeCount.get() * 10;

            if (newColor < 0) {
                return;
            }

            listOfComments.setBackgroundColor(Color.rgb(255,255, newColor));

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            asyncTask = null;
        }
    }

    public static ProductInfoFragment newInstance(Product product) {
        ProductInfoFragment productInfoFragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_TAG, product);
        productInfoFragment.setArguments(args);

        return productInfoFragment;
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onPostViewCreated(view, savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //ArrayList<Comment> comments = getArguments().getParcelableArrayList(COMMENTS_TAG);
        Product product = getArguments().getParcelable(PRODUCT_TAG);
        organisationName.setText(getString(R.string.organisation_pattern, getString(R.string.organisation_string), product.getOrganisation()));
        averageRate.setText(getString(R.string.product_rating_pattern, getString(R.string.rating), product.getAverageRate()));
        description.setText(product.getDescription());

        LoadProductCommentsSingleton
                .getInstance()
                .loadComments(product.getOrganisation(), product.getName())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        CommentAdapter commentAdapter = new CommentAdapter(comments);
                        listOfComments.setAdapter(commentAdapter);
                        listOfComments.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });

        Glide
                .with(this)
                .load(DEFAULT_SITE + "/" + product.getOrganisation() + "/" + product.getName() + "/image")
                .into(productImage);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.organisation_pattern, product.getOrganisation(), product.getName()));
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.product_info_layout;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce > SHAKE_THRESHOLD) {
            final long now = System.currentTimeMillis();

            if (lastShakeEventTime + MIN_PERIOD_BETWEEN_SHAKE_EVENTS > now) {
                return;
            }

            if (lastShakeEventTime + SHAKE_STOP_TIME < now) {
                shakeCount.set(0);
                onShakeEventStopped();
            }

            lastShakeEventTime = now;
            shakeCount.incrementAndGet();

            onShakeEventHappened(shakeCount.get());
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void onShakeEventHappened(final int shakeCount) {
        appBarLayout.setExpanded(false, true);
        final int newColor = 255 - shakeCount * 10;

        if (newColor < 0) {
            return;
        }

        listOfComments.setBackgroundColor(Color.rgb(255,255, newColor));

        if (null == asyncTask) {
            asyncTask = new ColorAsyncTask();
            asyncTask.execute();
        }
    }

    private void onShakeEventStopped() {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
