package com.github.dkharrat.nexusdialog.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dkharrat.nexusdialog.R;

public class ControllerBuilder {
    public static View createBasicRow(Context context, boolean enabledIconControls) {
        View view = LayoutInflater.from(context).inflate(R.layout.basic_row, null);
        View icon = view.findViewById(R.id.icon);
        ViewGroup addContainer = view.findViewById(R.id.addContainer);
        if (enabledIconControls) {
            icon.setVisibility(View.VISIBLE);
            addContainer.setVisibility(View.VISIBLE);
        } else {
            icon.setVisibility(View.GONE);
            addContainer.setVisibility(View.GONE);
        }
        return view;
    }
}
