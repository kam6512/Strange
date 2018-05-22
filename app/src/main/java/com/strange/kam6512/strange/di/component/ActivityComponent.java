package com.strange.kam6512.strange.di.component;

import com.strange.kam6512.strange.view.BaseActivity;
import com.strange.kam6512.strange.di.PerActivity;
import com.strange.kam6512.strange.di.module.ActivityModule;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);
}
