package com.strange.kam6512.strange.di.component;

import android.content.Context;
import com.strange.kam6512.strange.App;
import com.strange.kam6512.strange.di.ApplicationContext;
import com.strange.kam6512.strange.di.module.ApplicationModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(App app);

    @ApplicationContext
    Context getContext();

    App getApp();
}
