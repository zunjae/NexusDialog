package com.github.dkharrat.nexusdialog.controllers;

/*
public class ValueController extends LabeledFieldController {

    public ValueController(Context ctx, String fieldIdentifier, String labelText) {
        super(ctx, fieldIdentifier, labelText, false);
    }

    @Override
    protected View createFieldView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final TextView textView = (TextView) layoutInflater.inflate(R.layout.value_field, null);
        refresh(textView);

        return textView;
    }

    @Override
    protected void onRowClicked() {

    }

    private TextView getTextView() {
        return (TextView) getView().findViewById(R.id.value_text);
    }

    private void refresh(TextView textView) {
        Object value = getModel().getValue(getFieldIdentifier());
        textView.setText(value != null ? value.toString() : "");
    }

    public void refresh() {
        refresh(getTextView());
    }
}
 */