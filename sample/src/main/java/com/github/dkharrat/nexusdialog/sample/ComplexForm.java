package com.github.dkharrat.nexusdialog.sample;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.controllers.ValueController;
import com.github.dkharrat.nexusdialog.utils.MessageUtil;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * Demonstrates the following functionality:
 * <ul>
 *  <li>SearchableSelectionController</li>
 *  <li>Event handling</li>
 *  <li>Using a Custom Element</li>
 *  <li>Basic Validations</li>
 *  <li>Property change notifications</li>
 * </ul>
 */
public class ComplexForm extends FormWithAppCompatActivity {

    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String FULL_NAME = "fullName";
    private final static String GENDER = "gender";
    private final static String FAVORITE_COLOR = "favColor";
    private final static String CUSTOM_ELEM = "customElem";
    private final static String EVEN_NUMBER = "evenNumber";

    private final Stack<String> addedElements = new Stack<String>();

    @Override
    public void initForm(final FormController formController) {
        setTitle("Complex Form");

        final FormSectionController section = new FormSectionController(this, "Personal Info");
        section.addElement(new EditTextController(this, FIRST_NAME, "First name", "Change me", true));
        section.addElement(new EditTextController(this, LAST_NAME, "Last name"));
        section.addElement(new ValueController(this, FULL_NAME, "Full name"));
        section.addElement(new SelectionController(this, GENDER, "Gender", true, "Select", Arrays.asList("Male", "Female"), true));

        CustomElement customElem = new CustomElement(this, CUSTOM_ELEM, "Custom Element");
        customElem.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String name = "elem_" + addedElements.size();
                addedElements.add(name);
                section.addElement(new ValueController(ComplexForm.this, name, name));
                recreateViews();
            }
        });
        customElem.getRemoveButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!addedElements.isEmpty()) {
                    section.removeAllElements();
                    //section.removeElement(addedElements.pop());
                    recreateViews();
                }
            }
        });
        section.addElement(customElem);

        final FormSectionController validationSection = new FormSectionController(this, "Some checked input");

        HashSet<InputValidator> inputValidators = new HashSet<InputValidator>();
        inputValidators.add(new CustomValidation());
        inputValidators.add(new RequiredFieldValidator());
        validationSection.addElement(new EditTextController(
            this, EVEN_NUMBER, "enter an even number", "Put a number here",
            inputValidators, InputType.TYPE_CLASS_NUMBER
        ));

        formController.addSection(section);
        formController.addSection(validationSection);

        PropertyChangeListener nameFieldListener = new PropertyChangeListener() {
            @Override public void propertyChange(PropertyChangeEvent event) {
                Object firstName = formController.getModel().getValue(FIRST_NAME);
                Object lastName = formController.getModel().getValue(LAST_NAME);

                // setting a field value will automatically refresh the form element
                formController.getModel().setValue(FULL_NAME, firstName + " " + lastName);
            }
        };

        // add a listener to get notifications for any field changes
        formController.getModel().addPropertyChangeListener(FIRST_NAME, nameFieldListener);
        formController.getModel().addPropertyChangeListener(LAST_NAME, nameFieldListener);

        // initialize field with a value
        formController.getModel().setValue(LAST_NAME, "Smith");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        super.onOptionsItemSelected(item);

        getFormController().resetValidationErrors();
        if (getFormController().isValidInput()) {
            Object firstName = getModel().getValue(FIRST_NAME);
            Object lastName = getModel().getValue(LAST_NAME);
            Object gender = getModel().getValue(GENDER);
            Object favColor = getModel().getValue(FAVORITE_COLOR);
            Object number= getModel().getValue(EVEN_NUMBER);

            String msg = "First name: " + firstName + "\n"
                    + "Last name: " + lastName + "\n"
                    + "Gender: " + gender + "\n"
                    + "Favorite Color: " + favColor + "\n"
                    + "Even number: " + number + "\n";
            MessageUtil.showAlertMessage("Field Values", msg, this);
        } else {
            getFormController().showValidationErrors();
        }
        return true;
    }
}
