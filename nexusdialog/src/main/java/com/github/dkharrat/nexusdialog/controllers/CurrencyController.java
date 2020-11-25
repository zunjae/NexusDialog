package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.libs.CurrencyEditText;

public class CurrencyController extends LabeledFieldController {

    @Nullable
    private String currency;
    private String placeholder;

    public CurrencyController(Context ctx, String fieldIdentifier, String labelText, String placeholder, @Nullable String currency, boolean isRequired) {
        super(ctx, fieldIdentifier, labelText, isRequired);
        this.placeholder = placeholder;
        this.currency = currency;
    }

    @Override
    protected View createFieldView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.form_currency, null);
        CurrencyEditText currencyEditText = view.findViewById(R.id.etInput);
        if (currency != null) {
            currencyEditText.setCurrency(currency);
        }
        currencyEditText.setDelimiter(false);
        currencyEditText.setSpacing(false);
        currencyEditText.setDecimals(true);
        currencyEditText.setSeparator(".");
        currencyEditText.setHint(placeholder);
        return view;
    }

    @Override
    protected void onRowClicked() {

    }

    @Override
    public void refresh() {
        View root = getView();
        CurrencyEditText currencyEditText = root.findViewById(R.id.etInput);

        Object value = getModel().getValue(getFieldIdentifier());
        String valueStr = value != null ? value.toString() : "";
        currencyEditText.setText(valueStr);
    }
}
