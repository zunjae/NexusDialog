package com.github.dkharrat.nexusdialog.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.dkharrat.nexusdialog.R;

public class ControllerBuilder {
    public static View createBasicRow(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.basic_row, null);
    }
}
