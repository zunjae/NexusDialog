package com.github.dkharrat.nexusdialog.controllers;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.utils.ControllerBuilder;
import com.github.dkharrat.nexusdialog.validations.InputValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * Represents a field that allows selecting a specific date via a date picker.
 * <p/>
 * For the field value, the associated FormModel must return a {@link Date} instance. No selected date can be
 * represented by returning {@code null} for the value of the field.
 */
public class DatePickerController extends LabeledFieldController {
    private final int editTextId = View.generateViewId();

    private DatePickerDialog datePickerDialog = null;
    private final SimpleDateFormat displayFormat;
    private final TimeZone timeZone;

    /**
     * Constructs a new instance of a date picker field.
     *
     * @param ctx             the Android context
     * @param fieldIdentifier the fieldIdentifier of the field
     * @param labelText       the label to display beside the field. Set to {@code null} to not show a label.
     * @param validators      contains the validations to process on the field
     * @param displayFormat   the format of the date to show in the text box when a date is set
     */
    public DatePickerController(Context ctx, String fieldIdentifier, String labelText, Set<InputValidator> validators, SimpleDateFormat displayFormat) {
        super(ctx, fieldIdentifier, labelText, validators);
        this.displayFormat = displayFormat;
        this.timeZone = displayFormat.getTimeZone();
    }

    /**
     * Constructs a new instance of a date picker field.
     *
     * @param ctx             the Android context
     * @param fieldIdentifier the fieldIdentifier of the field
     * @param labelText       the label to display beside the field. Set to {@code null} to not show a label.
     * @param isRequired      indicates if the field is required or not
     * @param displayFormat   the format of the date to show in the text box when a date is set
     */
    public DatePickerController(Context ctx, String fieldIdentifier, String labelText, boolean isRequired, SimpleDateFormat displayFormat) {
        super(ctx, fieldIdentifier, labelText, isRequired);
        this.displayFormat = displayFormat;
        this.timeZone = displayFormat.getTimeZone();
    }

    /**
     * Constructs a new instance of a date picker field, with the selected date displayed in "MMM d, yyyy" format.
     *
     * @param fieldIdentifier the fieldIdentifier of the field
     * @param labelText       the label to display beside the field
     */
    public DatePickerController(Context context, String fieldIdentifier, String labelText) {
        this(context, fieldIdentifier, labelText, false, new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()));
    }

    @Override
    protected View createFieldView() {
        return ControllerBuilder.createBasicRow(getContext());
    }

    @Override
    protected void onRowClicked() {
        showDatePickerDialog();
    }

    private void showDatePickerDialog() {
        // don't show dialog again if it's already being shown
        final Context context = getContext();
        if (datePickerDialog == null) {
            Date date = (Date) getModel().getValue(getFieldIdentifier());
            if (date == null) {
                date = new Date();
            }
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeZone(timeZone);
            calendar.setTime(date);

            datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeZone(timeZone);
                    calendar.set(year, monthOfYear, dayOfMonth);
                    getModel().setValue(getFieldIdentifier(), calendar.getTime());
                    refresh();

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    datePickerDialog = null;
                }
            });

            datePickerDialog.show();
        }
    }

    @Override
    public void refresh() {
        Date value = (Date) getModel().getValue(getFieldIdentifier());
        View fieldView = getFieldView();
        TextView valueTextField = fieldView.findViewById(R.id.value);
        valueTextField.setText(value != null ? displayFormat.format(value) : "");
    }
}
