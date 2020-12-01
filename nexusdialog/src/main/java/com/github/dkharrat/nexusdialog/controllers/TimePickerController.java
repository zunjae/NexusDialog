package com.github.dkharrat.nexusdialog.controllers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimePickerController extends LabeledFieldController {
    private final int editTextId = View.generateViewId();

    private TimePickerDialog timePickerDialog = null;
    private final SimpleDateFormat displayFormat;
    private final TimeZone timeZone;
    private final boolean is24HourView = true;

    public TimePickerController(
            Context context,
            String identifier,
            String labelText,
            boolean isRequired,
            SimpleDateFormat displayFormat,
            boolean enabled
    ) {
        super(context, identifier, labelText, isRequired, enabled);
        this.displayFormat = displayFormat;
        this.timeZone = new SimpleDateFormat("hh:mm a", Locale.getDefault()).getTimeZone();
    }

    @Override
    protected View createFieldView() {
        final EditText editText = new EditText(getContext());
        editText.setId(editTextId);

        editText.setSingleLine(true);
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        editText.setKeyListener(null);
        refresh(editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getContext(), editText);
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerDialog(getContext(), editText);
                }
            }
        });

        if (isEnabled()) {
            editText.setEnabled(false);
        }

        return editText;
    }

    @Override
    protected void onRowClicked() {

    }

    private void showTimePickerDialog(Context context, final EditText editText) {
        // don't show dialog again if it's already being shown
        if (timePickerDialog == null) {
            Date date = (Date) getModel().getValue(getFieldIdentifier());
            if (date == null) {
                date = new Date();
            }
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeZone(timeZone);
            calendar.setTime(date);

            timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeZone(timeZone);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    getModel().setValue(getFieldIdentifier(), calendar.getTime());
                    editText.setText(displayFormat.format(calendar.getTime()));
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourView);

            timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    timePickerDialog = null;
                }
            });

            timePickerDialog.show();
        }
    }

    private EditText getEditText() {
        return (EditText) getView().findViewById(editTextId);
    }

    private void refresh(EditText editText) {
        Date value = (Date) getModel().getValue(getFieldIdentifier());
        editText.setText(value != null ? displayFormat.format(value) : "");
    }

    @Override
    public void refresh() {
        refresh(getEditText());
    }
}
