package com.strange.kam6512.strange.di.module;

import android.app.Activity;
import android.content.Context;
import com.strange.kam6512.strange.di.ActivityContext;
import dagger.Module;
import dagger.Provides;
import org.intellij.lang.annotations.PrintFormat;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context privideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }
}
