package com.github.dkharrat.nexusdialog.sample;

import android.text.InputType;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.CheckBoxController;
import com.github.dkharrat.nexusdialog.controllers.CurrencyController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;

public class ComplexForm extends FormWithAppCompatActivity {

    @Override
    public void initForm(final FormController formController) {
        setTitle("Form");

        final FormSectionController section = new FormSectionController(this, "Personal Info");
        section.addElement(new EditTextController(this, "a", "First name", "Change me", true, InputType.TYPE_CLASS_TEXT, true));

        CurrencyController currencyController = new CurrencyController(this, "b", "Amount", "€50", "€", true, true);
        section.addElement(currencyController);

        section.addElement(new CheckBoxController(this, "c", "Chub?", true, true));
        formController.addSection(section);

        formController.getModel().setValue("a", "Bob");
        formController.getModel().setValue("b", "5.00");
        formController.getModel().setValue("c", true);
    }
}
