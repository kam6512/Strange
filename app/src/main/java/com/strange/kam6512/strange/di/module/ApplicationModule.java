package com.strange.kam6512.strange.di.module;

import android.app.Activity;
import android.content.Context;
import com.strange.kam6512.strange.App;
import com.strange.kam6512.strange.di.ApplicationContext;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return app;
    }

    @Provides
    App provideApp() {
        return app;
    }

}
