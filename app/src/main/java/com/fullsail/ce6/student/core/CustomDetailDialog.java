package com.fullsail.ce6.student.core;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.fullsail.ce6.student.R;


public class CustomDetailDialog extends Dialog {

    TextView tvTitle,tvDescriprion,tvClose;

    public CustomDetailDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.detail_dailog_layout);
        setCancelable(false);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescriprion = findViewById(R.id.tvDescription);
        tvClose = findViewById(R.id.tvClose);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void showDalog(String title,String description) {
        try {
            tvTitle.setText(title);
            tvDescriprion.setText(description);
            show();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void closeDialog() {
        setCancelable(true);
        dismiss();
        cancel();
    }
}