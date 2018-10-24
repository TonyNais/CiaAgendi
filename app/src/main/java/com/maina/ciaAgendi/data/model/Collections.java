package com.maina.ciaAgendi.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({Collections.HYMNALS})
@Retention(RetentionPolicy.RUNTIME)
public @interface Collections {

    String HYMNALS = "hymnals";
}
