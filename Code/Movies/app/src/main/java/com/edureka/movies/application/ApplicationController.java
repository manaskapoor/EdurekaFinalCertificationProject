package com.edureka.movies.application;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class ApplicationController extends android.app.Application {
        private RequestQueue mRequestQueue;
        private static ApplicationController mInstance;

        public static synchronized ApplicationController getInstance() {
            return mInstance;
        }

        public static Context getAppContext(){
            return mInstance.getApplicationContext();
        }

        public ApplicationController() {
            super();
        }
        @Override
        public void onCreate() {
            super.onCreate();
            mInstance = this;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }

            return mRequestQueue;
        }
}
