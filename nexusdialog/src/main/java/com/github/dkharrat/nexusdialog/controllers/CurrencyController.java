package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.libs.CurrencyEditText;
import com.github.dkharrat.nexusdialog.libs.TextUpdatedInterface;

public class CurrencyController extends LabeledFieldController {

    @Nullable
    private String currency;
    private String placeholder;

    public CurrencyController(
            Context context,
            String identifier,
            String labelText,
            String placeholder,
            @Nullable String currency,
            boolean isRequired,
            boolean enabled

    ) {
        super(context, identifier, labelText, isRequired, enabled);
        this.placeholder = placeholder;
        this.currency = currency;
    }

    @Override
    protected View createFieldView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.form_currency, null);

        CurrencyEditText currencyEditText = view.findViewById(R.id.etInput);
        currencyEditText.setEnabled(isEnabled());

        if (currency != null) {
            currencyEditText.setCurrency(currency);
        }

        currencyEditText.setDelimiter(false);
        currencyEditText.setSpacing(false);
        currencyEditText.setDecimals(true);
        currencyEditText.setSeparator(".");
        currencyEditText.setHint(placeholder);

        currencyEditText.setListener(new TextUpdatedInterface() {
            @Override
            public void onNewText(String text) {
                String withoutCurrency = text.replaceAll(currency, "");
                getModel().setValue(getFieldIdentifier(), withoutCurrency);
                // move cursor to end
                currencyEditText.setSelection(currencyEditText.getText().length());
            }
        });

        updateUI(currencyEditText);

        return view;
    }

    @Override
    protected void onRowClicked() {

    }

    private void updateUI(CurrencyEditText currencyEditText) {
        String currentText = currencyEditText.getText().toString();

        String value = (String) getModel().getValue(getFieldIdentifier());
        if (value == null) {
            currencyEditText.setText("");
            return;
        }

        if (currency != null) {
            if (!currentText.startsWith(currency)) {
                currencyEditText.setText(String.format("%s%s", currency, value));
            } else {
                currencyEditText.setText(String.format("%s%s", currency, value));
            }
        } else {
            currencyEditText.setText(value);
        }
    }

    @Override
    public void refresh() {
        View root = getFieldView();
        CurrencyEditText currencyEditText = root.findViewById(R.id.etInput);
        updateUI(currencyEditText);
    }
}
