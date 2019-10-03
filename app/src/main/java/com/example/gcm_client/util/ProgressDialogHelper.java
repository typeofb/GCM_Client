package com.example.gcm_client.util;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressDialogHelper {

    public static void showProgressHelper(final Activity context, final Runnable runner) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("잠시만 기다려 주십시오.");
        progressDialog.show();
        final Thread t = new Thread("ProgressBar") {
            @Override
            public void run() {
                runner.run();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                }});
            }
        };
        t.start();
    }
}
