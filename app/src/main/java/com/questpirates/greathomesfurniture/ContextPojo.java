package com.questpirates.greathomesfurniture;

import android.content.Context;

public class ContextPojo {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ContextPojo.context = context;
    }
}
