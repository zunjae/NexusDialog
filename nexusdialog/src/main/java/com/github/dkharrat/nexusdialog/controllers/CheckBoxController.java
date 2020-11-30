package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.github.dkharrat.nexusdialog.R;

public class CheckBoxController extends LabeledFieldController {

    public CheckBoxController(
            Context ctx,
            String identifier,
            String labelText,
            boolean isRequired,
            boolean fieldEnabled
    ) {
        super(ctx, identifier, labelText, isRequired, fieldEnabled);
    }

    @Override
    protected View createFieldView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.value_checkbox, null);
    }

    @Override
    protected void onRowClicked() {
        CheckBox checkBox = getFieldView().findViewById(R.id.checkbox);
        checkBox.setChecked(!checkBox.isChecked());
    }

    @Override
    public void refresh() {
        boolean value = (boolean) getModel().getValue(getFieldIdentifier());
        CheckBox checkBox = getFieldView().findViewById(R.id.checkbox);
        checkBox.setChecked(value);
    }
}