package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.dkharrat.nexusdialog.R;

public class CheckBoxController extends LabeledFieldController {

    public CheckBoxController(
            Context context,
            String identifier,
            String labelText,
            boolean isRequired,
            boolean enabled
    ) {
        super(context, identifier, labelText, isRequired, enabled);
    }

    private void updateElement(CheckBox checkBox) {
        boolean value = (boolean) getModel().getValue(getFieldIdentifier());
        checkBox.setChecked(value);
    }

    @Override
    protected View createFieldView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.value_checkbox, null);

        CheckBox checkBox = view.findViewById(R.id.checkbox);
        updateElement(checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getModel().setValue(getFieldIdentifier(), isChecked);
            }
        });

        return view;
    }

    @Override
    protected void onRowClicked() {
        CheckBox checkBox = getFieldView().findViewById(R.id.checkbox);
        checkBox.setChecked(!checkBox.isChecked());
    }

    @Override
    public void refresh() {
        CheckBox checkBox = getFieldView().findViewById(R.id.checkbox);
        updateElement(checkBox);
    }
}