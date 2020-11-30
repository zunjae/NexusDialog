package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Represents a field that allows free-form text.
 */
public class EditTextController extends LabeledFieldController {
    private final int editTextId = View.generateViewId();

    private int inputType;
    private final String placeholder;

    public EditTextController(
            Context ctx,
            String identifier,
            String labelText,
            String placeholder,
            boolean isRequired,
            int inputType,
            boolean fieldEnabled
    ) {
        super(ctx, identifier, labelText, isRequired, fieldEnabled);
        this.placeholder = placeholder;
        this.inputType = inputType;
    }

    private EditText getEditText() {
        return (EditText) getView().findViewById(editTextId);
    }

    public int getInputType() {
        return inputType;
    }

    private void setInputTypeMask(int mask, boolean enabled) {
        if (enabled) {
            inputType = inputType | mask;
        } else {
            inputType = inputType & ~mask;
        }
        if (isViewCreated()) {
            getEditText().setInputType(inputType);
        }
    }

    /**
     * Indicates whether this text box has multi-line enabled.
     *
     * @return true if this text box has multi-line enabled, or false otherwise
     */
    public boolean isMultiLine() {
        return (inputType | InputType.TYPE_TEXT_FLAG_MULTI_LINE) != 0;
    }

    /**
     * Enables or disables multi-line input for the text field. Default is false.
     *
     * @param multiLine if true, multi-line input is allowed, otherwise, the field will only allow a single line.
     */
    public void setMultiLine(boolean multiLine) {
        setInputTypeMask(InputType.TYPE_TEXT_FLAG_MULTI_LINE, multiLine);
    }

    /**
     * Indicates whether this text field hides the input text for security reasons.
     *
     * @return true if this text field hides the input text, or false otherwise
     */
    public boolean isSecureEntry() {
        return (inputType | InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0;
    }

    /**
     * Enables or disables secure entry for this text field. If enabled, input will be hidden from the user. Default is
     * false.
     *
     * @param isSecureEntry if true, input will be hidden from the user, otherwise input will be visible.
     */
    public void setSecureEntry(boolean isSecureEntry) {
        setInputTypeMask(InputType.TYPE_TEXT_VARIATION_PASSWORD, isSecureEntry);
    }

    @Override
    protected View createFieldView() {
        final EditText editText = new EditText(getContext());
        editText.setId(editTextId);
        editText.setEnabled(isFieldEnabled());

        editText.setSingleLine(!isMultiLine());
        if (placeholder != null) {
            editText.setHint(placeholder);
        }
        editText.setInputType(inputType);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getModel().setValue(getFieldIdentifier(), editText.getText().toString());
            }
        });


        refresh(editText);

        return editText;
    }

    @Override
    protected void onRowClicked() {

    }

    private void refresh(EditText editText) {
        Object value = getModel().getValue(getFieldIdentifier());
        editText.setText(value != null ? value.toString() : "");
    }

    @Override
    public void refresh() {
        refresh(getEditText());
    }
}
