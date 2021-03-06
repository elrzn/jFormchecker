package de.jformchecker.example.forms;

import java.util.LinkedHashMap;

import de.jformchecker.FormCheckerForm;
import de.jformchecker.criteria.Criteria;
import de.jformchecker.elements.ButtonInput;
import de.jformchecker.elements.CheckboxInput;
import de.jformchecker.elements.DateInput;
import de.jformchecker.elements.DateInputCompound;
import de.jformchecker.elements.HTMLSnippet;
import de.jformchecker.elements.HiddenInput;
import de.jformchecker.elements.LongTextInput;
import de.jformchecker.elements.PasswordInput;
import de.jformchecker.elements.RadioInput;
import de.jformchecker.elements.SelectInput;
import de.jformchecker.elements.TextInput;
import de.jformchecker.example.CustomValidation;
import de.jformchecker.example.PasswordFormValidator;

public class ExampleForm extends FormCheckerForm {

	public void init() {
		add(TextInput.build("firstname").setDescription("Your Firstname").setPreSetValue("Peter").setRequired()
				.setCriterias(Criteria.accept("Peter", "Max"), Criteria.maxLength(10)));

		add(TextInput.build("lastname").setPlaceholerText("Mustermann").setDescription("Your Lastname")
				.setHelpText("This is an example Helptext for describing this lastname field")
				.setCriterias(Criteria.accept("Pan", "Mustermann")));

		add(TextInput.build("middelname").setDescription("Your Middelname").setCriterias(new CustomValidation()));

		add(HTMLSnippet.build("headline").setHTML("<h1>Headline</h1>"));

		add(HiddenInput.build("hidden").setPreSetValue("something to remember"));

		add(ButtonInput.build("btn").setButtonText("Add...").setPreSetValue("add"));

		add(DateInput.build("date").setDescription("Birthday"));

		add(DateInputCompound.build("date2").setDescription("Mein Tag"));

		add(PasswordInput.build("password1").setRequired().setDescription("Password"));

		add(PasswordInput.build("password2").setRequired().setDescription("Repeat password"));

		add(LongTextInput.build("description").setRequired().setDescription("Your Description"));

		// RFE: simple map-builder
		LinkedHashMap<String, String> radioEntries = createRadioMap();

		add(RadioInput.build("rdio").setPossibleValues(radioEntries).setDescription("Your Choice"));

		LinkedHashMap<String, String> selectEntries = createSelectMap();

		add(SelectInput.build("select").setPossibleValues(selectEntries).setDescription("Your Selection"));

		add(CheckboxInput.build("check").setDescription("I order everything"));

		addFormValidator(new PasswordFormValidator());

		this.disableHtml5Validation();

	}

	private LinkedHashMap<String, String> createSelectMap() {
		LinkedHashMap<String, String> selectEntries = new LinkedHashMap<>();
		selectEntries.put("green", "Green");
		selectEntries.put("blue", "Blue");
		selectEntries.put("yellow", "Yellow");
		return selectEntries;
	}

	private LinkedHashMap<String, String> createRadioMap() {
		LinkedHashMap<String, String> radioEntries = new LinkedHashMap<>();
		radioEntries.put("one", "One $");
		radioEntries.put("two", "Two $");
		radioEntries.put("three", "Three $");
		return radioEntries;
	}
}
