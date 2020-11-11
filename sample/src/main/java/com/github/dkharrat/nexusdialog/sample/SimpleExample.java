package com.github.dkharrat.nexusdialog.sample;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.DatePickerController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.controllers.TimePickerController;

import java.util.Arrays;

/**
 * Demonstrates the bare minimum to display a form in an Activity.
 */
public class SimpleExample extends FormWithAppCompatActivity {

    @Override public void initForm(FormController formController) {
        setTitle("Simple Example");

        FormSectionController section = new FormSectionController(this, "Personal Info");
        section.addElement(new EditTextController(this, "firstName", "First name"));
        section.addElement(new EditTextController(this, "lastName", "Last name"));
        section.addElement(new SelectionController(this, "gender", "Gender", true, "Select", Arrays.asList("Male", "Female"), true));
        section.addElement(new DatePickerController(this, "date", "Choose Date"));
        section.addElement(new TimePickerController(this, "time", "Choose Time"));
        formController.addSection(section);
    }
}
