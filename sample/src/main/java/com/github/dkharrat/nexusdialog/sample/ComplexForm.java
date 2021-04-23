package com.github.dkharrat.nexusdialog.sample;

import android.text.InputType;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.CheckBoxController;
import com.github.dkharrat.nexusdialog.controllers.DatePickerController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;

import java.util.Date;

public class ComplexForm extends FormWithAppCompatActivity {

    @Override
    public void initForm(final FormController formController) {
        setTitle("Form");

        final FormSectionController section = new FormSectionController(this, "Personal Info");
        section.addElement(new EditTextController(this, "a", "First name jdeiw dewij deiwjdwed", "Change me", true, InputType.TYPE_CLASS_TEXT, true));

        //CurrencyController currencyController = new CurrencyController(this, "b", "Amount", "€50", "€", true, true);
        //section.addElement(currencyController);
        //currencyController.setEnabled(true);

        section.addElement(new CheckBoxController(this, "c", "Chub?", true, true));
        formController.addSection(section);

        section.addElement(new DatePickerController(this, "d", "Pick a date", true, DatePickerController.getDefaultFormatter(), true));
        section.addElement(new DatePickerController(this, "e", "Pick a date (disabled)", true, DatePickerController.getDefaultFormatter(), false));

        formController.getModel().setValue("a", "Bob");
        formController.getModel().setValue("b", "5.00");
        formController.getModel().setValue("c", false);
        formController.getModel().setValue("d", new Date());
    }
}
