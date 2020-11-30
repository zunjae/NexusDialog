package com.github.dkharrat.nexusdialog.controllers;

/*
public class SelectionController extends LabeledFieldController {

    private final int spinnerId = View.generateViewId();

    private final String prompt;
    private final List<String> items;
    private final List<?> values;

    public SelectionController(Context ctx, String fieldIdentifier, String labelText, Set<InputValidator> validators, String prompt, List<String> items, boolean useItemsAsValues) {
        this(ctx, fieldIdentifier, labelText, validators, prompt, items, useItemsAsValues ? items : null);
    }

    public SelectionController(Context ctx, String fieldIdentifier, String labelText, Set<InputValidator> validators, String prompt, List<String> items, List<?> values) {
        super(ctx, fieldIdentifier, labelText, validators);
        this.prompt = prompt;
        this.items = new ArrayList<>(items);
        this.items.add(prompt);     // last item is used for the 'prompt' by the SpinnerView
        this.values = new ArrayList<>(values);
    }

    public SelectionController(Context ctx, String fieldIdentifier, String labelText, boolean isRequired, String prompt, List<String> items, boolean useItemsAsValues) {
        this(ctx, fieldIdentifier, labelText, isRequired, prompt, items, useItemsAsValues ? items : null);
    }

    public SelectionController(Context ctx, String fieldIdentifier, String labelText, boolean isRequired, String prompt, List<String> items, List<?> values) {
        super(ctx, fieldIdentifier, labelText, isRequired);
        this.prompt = prompt;
        this.items = new ArrayList<>(items);
        this.items.add(prompt);     // last item is used for the 'prompt' by the SpinnerView
        this.values = new ArrayList<>(values);
    }

    public Spinner getSpinner() {
        return (Spinner) getView().findViewById(spinnerId);
    }

    @Override
    protected void onRowClicked() {
        getSpinner().performClick();
    }

    @Override
    protected View createFieldView() {
        Spinner spinnerView = new Spinner(getContext());
        spinnerView.setId(spinnerId);
        spinnerView.setPrompt(prompt);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    TextView itemView = view.findViewById(android.R.id.text1);
                    itemView.setText("");
                    itemView.setHint(getItem(getCount()));
                }

                return view;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // don't display last item (it's used for the prompt)
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerView.setAdapter(spinnerAdapter);

        spinnerView.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object value;
                // if no values are specified, set the index on the model
                if (values == null) {
                    value = pos;
                } else {
                    // last pos indicates nothing is selected
                    if (pos == items.size() - 1) {
                        value = null;
                    } else {    // if something is selected, set the value on the model
                        value = values.get(pos);
                    }
                }

                getModel().setValue(getFieldIdentifier(), value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        refresh(spinnerView);

        return spinnerView;
    }

    private void refresh(Spinner spinner) {
        Object value = getModel().getValue(getFieldIdentifier());
        int selectionIndex = items.size() - 1;    // index of last item shows the 'prompt'

        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).equals(value)) {
                    selectionIndex = i;
                    break;
                }
            }
        } else if (value instanceof Integer) {
            selectionIndex = (Integer) value;
        }

        spinner.setSelection(selectionIndex);
    }

    @Override
    public void refresh() {
        refresh(getSpinner());
    }
}
*/