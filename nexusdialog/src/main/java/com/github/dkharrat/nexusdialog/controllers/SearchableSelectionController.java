package com.github.dkharrat.nexusdialog.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.validations.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Represents a field that allows a user to select from a list of items, with the ability to search for specific items.
 * It also allows for free-form entry if the input text does not exist in the predefined list. This field is useful when
 * the number of items are large. While the list of items are retrieved in the background from the data source, a
 * loading indicator is displayed. For similar functionality, but with a small number of items, use
 * {@link SelectionController} instead.
 * <p/>
 * For the field value, the associated FormModel must return a String representing the currently selected item.
 * If the value does not exist in the list, 'Other (x)' will be displayed, where 'x' is the field value. No selection
 * can be represented by returning {@code null} for the value of the field.
 */
public class SearchableSelectionController extends LabeledFieldController {
    private final int editTextId = View.generateViewId();

    private final String placeholder;
    private boolean isFreeFormTextAllowed = true;
    private Dialog selectionDialog = null;
    private final SelectionDataSource dataSource;
    private List<String> items = null;
    private final LoadItemsTask loadItemsTask;
    private boolean otherSelectionIsShowing = false;

    /**
     * Creates a new instance of a selection field.
     *
     * @param ctx         the Android context
     * @param name        the name of the field
     * @param labelText   the label to display beside the field. Set to {@code null} to not show a label.
     * @param isRequired  indicates if the field is required or not
     * @param placeholder a placeholder text to show when the input field is empty
     * @param dataSource  the data source that provides the list of items to display
     */
    public SearchableSelectionController(Context ctx, String name, String labelText, boolean isRequired, String placeholder, SelectionDataSource dataSource) {
        super(ctx, name, labelText, isRequired);
        this.placeholder = placeholder;
        this.dataSource = dataSource;

        loadItemsTask = new LoadItemsTask();
        loadItemsTask.execute();
    }

    /**
     * Creates a new instance of a selection field.
     *
     * @param ctx         the Android context
     * @param name        the name of the field
     * @param labelText   the label to display beside the field. Set to {@code null} to not show a label.
     * @param validators  contains the validations to process on the field
     * @param placeholder a placeholder text to show when the input field is empty
     * @param dataSource  the data source that provides the list of items to display
     */
    public SearchableSelectionController(Context ctx, String name, String labelText, Set<InputValidator> validators, String placeholder, SelectionDataSource dataSource) {
        super(ctx, name, labelText, validators);
        this.placeholder = placeholder;
        this.dataSource = dataSource;

        loadItemsTask = new LoadItemsTask();
        loadItemsTask.execute();
    }

    private void showSelectionDialog(final Context context, final EditText editText) {
        if (items == null) {
            loadItemsTask.runTaskOnFinished(new Runnable() {
                @Override
                public void run() {
                    showSelectionDialog(context, editText);
                }
            });
        } else if (selectionDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getLabel());

            View rootView = LayoutInflater.from(context).inflate(R.layout.searchable_listview, null);
            final List<String> filteredItems = new ArrayList<String>(items);
            final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, filteredItems);

            final EditText searchField = (EditText) rootView.findViewById(R.id.search_field);
            searchField.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString().trim();
                    String textLowerCase = text.toLowerCase(Locale.getDefault());
                    filteredItems.clear();
                    if (s.length() > 0) {
                        for (String item : items) {
                            if (item.toLowerCase(Locale.getDefault()).startsWith(textLowerCase)) {
                                filteredItems.add(item);
                            }
                        }
                    } else {
                        filteredItems.addAll(items);
                    }

                    otherSelectionIsShowing = false;
                    if (isFreeFormTextAllowed
                            && !text.isEmpty()
                            && (filteredItems.size() != 1 || !filteredItems.get(0).equalsIgnoreCase(textLowerCase))) {
                        filteredItems.add(0, "Other (" + text + ")");
                        otherSelectionIsShowing = true;
                    }

                    itemsAdapter.notifyDataSetChanged();
                }
            });

            final ListView listView = (ListView) rootView.findViewById(R.id.selection_list);
            listView.setAdapter(itemsAdapter);
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selection;
                    if (otherSelectionIsShowing && position == 0) {
                        selection = searchField.getText().toString();
                    } else {
                        selection = itemsAdapter.getItem(position);
                    }
                    getModel().setValue(getName(), selection);
                    editText.setText(selection);
                    selectionDialog.dismiss();
                }
            });

            builder.setView(rootView);
            //builder.setInverseBackgroundForced(true);
            selectionDialog = builder.create();
            selectionDialog.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    selectionDialog = null;
                }
            });

            selectionDialog.show();
        }
    }


    public void setFreeFormTextAllowed(boolean allowed) {
        isFreeFormTextAllowed = allowed;
    }

    public boolean isFreeFormTextAllowed() {
        return isFreeFormTextAllowed;
    }

    protected View createFieldView() {
        final EditText editText = new EditText(getContext());
        editText.setId(editTextId);

        editText.setSingleLine(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setKeyListener(null);
        if (placeholder != null) {
            editText.setHint(placeholder);
        }
        refresh(editText);
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectionDialog(getContext(), editText);
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showSelectionDialog(getContext(), editText);
                }
            }
        });

        return editText;
    }

    /**
     * An interface that provides the list of items to display for the {@link SearchableSelectionController}.
     */
    public interface SelectionDataSource {
        /**
         * Returns a list of all the items that can be selected or searched. This method will be called by the
         * {@link SearchableSelectionController} in a background thread.
         *
         * @return a list of all the items that can be selected or searched.
         */
        List<String> getItems();
    }

    private EditText getEditText() {
        return (EditText) getView().findViewById(editTextId);
    }

    private void refresh(EditText editText) {
        String value = (String) getModel().getValue(getName());
        editText.setText(value != null ? value : "");
    }

    @Override
    public void refresh() {
        refresh(getEditText());
    }

    private class LoadItemsTask extends AsyncTask<Void, Void, List<String>> {

        Runnable doneRunnable;

        @Override
        protected List<String> doInBackground(Void... params) {
            return dataSource.getItems();
        }

        @Override
        protected void onPostExecute(List<String> results) {
            items = results;

            if (doneRunnable != null) {
                doneRunnable.run();
            }
        }

        protected void runTaskOnFinished(Runnable runnable) {
            doneRunnable = runnable;
        }
    }
}
