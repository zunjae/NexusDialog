package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.github.dkharrat.nexusdialog.R;

public class CheckBoxController extends LabeledFieldController {

    private boolean checked;

    public CheckBoxController(Context ctx, String fieldIdentifier, String labelText, boolean checked) {
        super(ctx, fieldIdentifier, labelText, false);
        this.checked = checked;
    }

    @Override
    protected View createFieldView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.value_checkbox, null);
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setChecked(checked);
        return view;
    }

    @Override
    protected void onRowClicked() {
        CheckBox checkBox = getFieldView().findViewById(R.id.checkbox);
        checkBox.setChecked(!checkBox.isChecked());
    }

    public void refresh() {
        boolean value = (boolean) getModel().getValue(getFieldIdentifier());
        CheckBox checkBox = getFieldView().findViewById(R.id.checkbox);
        checkBox.setChecked(value);
    }
}