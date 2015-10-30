package de.jformchecker.example;

import java.util.LinkedHashMap;

import de.jformchecker.FormCheckerForm;
import de.jformchecker.criteria.Criteria;
import de.jformchecker.elements.CheckboxInput;
import de.jformchecker.elements.DateInput;
import de.jformchecker.elements.Headline;
import de.jformchecker.elements.HiddenInput;
import de.jformchecker.elements.LongTextInput;
import de.jformchecker.elements.PasswordInput;
import de.jformchecker.elements.RadioInput;
import de.jformchecker.elements.SelectInput;
import de.jformchecker.elements.TextInput;

public class ExampleForm extends FormCheckerForm {

  public ExampleForm() {
    add(TextInput.build("firstname").setDescription("Your Firstname").setPreSetValue("Jochen")
        .setCriterias(Criteria.accept("Jochen", "Max")));

    add(TextInput.build("lastname").setDescription("Your Lastname").setPreSetValue("pier")
        .setCriterias(Criteria.accept("Pan", "Mustermann")));

    add(TextInput.build("middelname").setDescription("Your Middelname")
        .setCriterias(new CustomValidation()));

    add(Headline.build("headline").setDescription("Example Headline"));

    add(HiddenInput.build("hidden").setPreSetValue("something to remember"));

    add(DateInput.build("date").setDescription("Birthday"));



    add(PasswordInput.build("password").setDescription("Password"));

    add(LongTextInput.build("description").setRequired().setDescription("Your Description"));

    LinkedHashMap<String, String> radioEntries = new LinkedHashMap<>();
    radioEntries.put("one", "One $");
    radioEntries.put("two", "Two $");
    radioEntries.put("three", "Three $");

    // RFE: simple map-builder

    add(RadioInput.build("rdio").setPossibleValues(radioEntries).setDescription("Your Choice"));

    LinkedHashMap<String, String> selectEntries = new LinkedHashMap<>();
    selectEntries.put("green", "Green");
    selectEntries.put("blue", "Blue");
    selectEntries.put("yellow", "Yellow");

    // RFE: simple map-builder

    add(SelectInput.build("select").setPossibleValues(selectEntries)
        .setDescription("Your Selection"));

    add(CheckboxInput.build("check").setDescription("I order everything"));

  }
}
