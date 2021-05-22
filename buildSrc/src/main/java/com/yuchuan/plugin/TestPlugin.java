package com.yuchuan.plugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;

public class TestPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        AppExtension android = (AppExtension) project.getProperties().get("android");
        android.registerTransform(new TestTransform(project), Collections.EMPTY_LIST);
    }
}
