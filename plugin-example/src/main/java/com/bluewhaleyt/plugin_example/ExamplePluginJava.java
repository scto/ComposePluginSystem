package com.bluewhaleyt.plugin_example;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bluewhaleyt.plugin_api.Plugin;

public class ExamplePluginJava extends Plugin {

    public ExamplePluginJava(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public String name() {
        return "ExamplePluginJava";
    }

    @NonNull
    @Override
    public String description() {
        return "";
    }

    @NonNull
    @Override
    public String author() {
        return "BlueWhaleYT";
    }
}
