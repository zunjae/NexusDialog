package com.github.dkharrat.nexusdialog.controllers;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.utils.ControllerBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DatePickerController extends LabeledFieldController {
    private final SimpleDateFormat displayFormat;
    private final TimeZone timeZone;

    public static SimpleDateFormat getDefaultFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public DatePickerController(
            Context context,
            String identifier,
            String labelText,
            boolean isRequired,
            SimpleDateFormat displayFormat,
            boolean enabled
    ) {
        super(context, identifier, labelText, isRequired, enabled);
        this.displayFormat = displayFormat;
        this.timeZone = displayFormat.getTimeZone();
    }

    @Override
    protected View createFieldView() {
        View row = ControllerBuilder.createBasicRow(getContext());
        updateUI(row.findViewById(R.id.value));
        return row;
    }

    private void updateUI(TextView textView) {
        Date value = (Date) getModel().getValue(getFieldIdentifier());
        textView.setText(value != null ? displayFormat.format(value) : "");
    }

    @Override
    protected void onRowClicked() {
        showDatePickerDialog();
    }

    private void showDatePickerDialog() {
        final Context context = getContext();
        Date date = (Date) getModel().getValue(getFieldIdentifier());
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeZone(timeZone);
        calendar.setTime(date);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                calendar.setTimeZone(timeZone);
                calendar.set(year, monthOfYear, dayOfMonth);
                getModel().setValue(getFieldIdentifier(), calendar.getTime());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void refresh() {
        View fieldView = getFieldView();
        updateUI(fieldView.findViewById(R.id.value));
    }
}
