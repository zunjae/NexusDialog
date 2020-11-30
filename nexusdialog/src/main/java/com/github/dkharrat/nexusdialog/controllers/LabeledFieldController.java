package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dkharrat.nexusdialog.FormElementController;
import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;
import com.github.dkharrat.nexusdialog.validations.ValidationError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An abstract class that represents a generic form field with an associated label.
 */
public abstract class LabeledFieldController extends FormElementController {
    private static final RequiredFieldValidator REQUIRED_FIELD_VALIDATOR = new RequiredFieldValidator();
    private final String labelText;
    private View fieldView;
    private TextView errorView;
    private Set<InputValidator> validators;
    private boolean fieldEnabled;

    public LabeledFieldController(
            Context context,
            String identifier,
            String labelText,
            boolean isRequired,
            boolean fieldEnabled
    ) {
        super(context, identifier);
        this.validators = new HashSet<>();
        this.labelText = labelText;
        this.fieldEnabled = fieldEnabled;
        setIsRequired(isRequired);
    }

    public boolean isFieldEnabled() {
        return fieldEnabled;
    }

    public String getLabel() {
        return labelText;
    }

    public void setIsRequired(boolean required) {
        if (!required) {
            validators.remove(REQUIRED_FIELD_VALIDATOR);
        } else if (!isRequired()) {
            validators.add(REQUIRED_FIELD_VALIDATOR);
        }
    }

    /**
     * Changes the validators for the given field.
     *
     * @param newValidators THe new validators to use.
     */
    public void setValidators(Set<InputValidator> newValidators) {
        validators = newValidators;
    }

    /**
     * Indicates whether this field requires an input value.
     *
     * @return true if this field is required to have input, otherwise false
     */
    public boolean isRequired() {
        return validators.contains(REQUIRED_FIELD_VALIDATOR);
    }

    /**
     * Indicates whether the input of this field has any validation errors.
     *
     * @return true if there are some validation errors, otherwise false
     */
    public boolean isValidInput() {
        return validateInput().isEmpty();
    }

    /**
     * Runs a validation on the user input and returns all the validation errors of this field.
     * Previous error messages are removed when calling {@code validateInput()}.
     *
     * @return a list containing all the validation errors
     */
    public List<ValidationError> validateInput() {
        List<ValidationError> errors = new ArrayList<>();
        Object value = getModel().getValue(getFieldIdentifier());
        ValidationError error;
        for (InputValidator validator : validators) {
            error = validator.validate(value, getFieldIdentifier(), getLabel());
            if (error != null) {
                errors.add(error);
            }
        }
        return errors;
    }

    /**
     * Returns the associated view for the field (without the label view) of this element.
     *
     * @return the view for this element
     */
    public View getFieldView() {
        if (fieldView == null) {
            fieldView = createFieldView();
        }
        return fieldView;
    }

    /**
     * Constructs the view associated with this field without the label. It will be used to combine with the label.
     *
     * @return the newly created view for this field
     */
    protected abstract View createFieldView();

    protected abstract void onRowClicked();

    @Override
    protected View createView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.form_labeled_element, null);
        LinearLayout root = view.findViewById(R.id.root);
        errorView = view.findViewById(R.id.field_error);

        TextView label = view.findViewById(R.id.field_label);
        if (labelText == null) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(labelText);
        }

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldEnabled) {
                    onRowClicked();
                }
            }
        });

        FrameLayout container = view.findViewById(R.id.field_container);
        container.addView(getFieldView());

        return view;
    }

    @Override
    public void setError(String message) {
        if (message == null) {
            errorView.setVisibility(View.GONE);
        } else {
            errorView.setText(message);
            errorView.setVisibility(View.VISIBLE);
        }
    }
}
