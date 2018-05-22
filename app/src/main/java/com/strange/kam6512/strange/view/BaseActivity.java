package com.strange.kam6512.strange.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.strange.kam6512.strange.App;
import com.strange.kam6512.strange.di.component.ActivityComponent;
import com.strange.kam6512.strange.di.component.DaggerActivityComponent;
import com.strange.kam6512.strange.di.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    private ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
//                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.get(this).getAppComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }
}
