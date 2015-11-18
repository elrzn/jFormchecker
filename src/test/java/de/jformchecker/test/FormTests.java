package de.jformchecker.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

import de.jformchecker.FormChecker;
import de.jformchecker.FormCheckerElement;
import de.jformchecker.FormCheckerForm;
import de.jformchecker.criteria.Criteria;
import de.jformchecker.elements.TextInput;

public class FormTests {

  @Test
  public void testXSS() {
    TextInput ti = buildExampleTextInput();
    HttpServletRequest request = RequestBuilders.buildExampleHttpRequest();
    ti.init(request, false);
    String inputTag = ti.getInputTag();
    assertEquals(
        "<input  id=\"form_firstname\" tabindex=\"0\" type=\"text\" name=\"firstname\" value=\"Jochen Pier&lt;bold&gt;\">",
        inputTag);
  }



  @Test
  public void testPrefillText() {
    TextInput ti = buildExampleTextInput();
    HttpServletRequest request = RequestBuilders.buildEmptyHttpRequest();
    ti.init(request, true);
    String inputTag = ti.getInputTag();
    assertEquals(
        "<input  id=\"form_firstname\" tabindex=\"0\" type=\"text\" name=\"firstname\" value=\"Jochen\">",
        inputTag);
  }


  @Test
  public void testPrebuildForm() {
    Map<String, String> reqVals = new HashMap<>();
    String formId = "h";
    reqVals.put("firstname", "Jochen2");
    reqVals.put(FormChecker.SUBMIT_KEY, FormChecker.SUBMIT_VALUE_PREFIX + formId);
    HttpServletRequest request = RequestBuilders.buildExampleHttpRequest(reqVals);

    ExampleFormTest form = new ExampleFormTest();
    FormChecker fc = new FormChecker(formId, request);
    fc.addForm(form);
    fc.run();
    assertEquals("Jochen2", fc.getValue("firstname"));
    Assert.assertTrue("FC should be not valid", !fc.isValid());
    // todo: Check if valid!

  }



  @Test
  public void testPrebuildForm2() {
    Map<String, String> reqVals = new HashMap<>();
    String firstname = "Max";
    String formId = "h";
    reqVals.put("firstname", firstname);
    reqVals.put("lastname", "Pan");
    reqVals.put("description", "eine Beschreibung");

    reqVals.put(FormChecker.SUBMIT_KEY, FormChecker.SUBMIT_VALUE_PREFIX + formId);

    HttpServletRequest request = RequestBuilders.buildExampleHttpRequest(reqVals);

    ExampleFormTest form = new ExampleFormTest();
    FormChecker fc = new FormChecker(formId, request);
    fc.addForm(form);
    fc.run();
    assertEquals(firstname, fc.getValue("firstname"));
    Assert.assertTrue("FC should be valid", fc.isValid());

  }

  @Test
  public void testLabel() {
    FormChecker fc = new FormChecker("", RequestBuilders.buildEmptyHttpRequest());
    fc.addForm(new FormCheckerForm() {
      @Override
      public void init() {
        add(TextInput.build("firstname")
            .setDescription("Your Firstname")
            .setPreSetValue("Peter")
            );
      }
    });
    fc.run();
    System.out.println(fc.getCompleteForm());
    Assert.assertTrue("Form should contain a label!", (fc.getCompleteForm().contains("label")));

  }

  @Test
  public void testNoLabel() {
    FormChecker fc = new FormChecker("", RequestBuilders.buildEmptyHttpRequest());
    fc.addForm(new FormCheckerForm() {
      @Override
      public void init() {
        add(TextInput.build("firstname")
            .setPreSetValue("Peter")
            );
      }
    });
    fc.run();
    System.out.println(fc.getCompleteForm());
    Assert.assertTrue("Form should not contain a label!", !(fc.getCompleteForm().contains("label")));

  }


  @Test
  public void testRequired() {
    // fail("Not yet implemented");
  }

  @Test
  public void testForbiddenSelect() {
    // fail("Not yet implemented");
  }



  private TextInput buildExampleTextInput() {
    TextInput ti = (TextInput) TextInput.build("firstname").setDescription("Vorname")
        .setPreSetValue("Jochen").setCriterias(Criteria.accept("Peter"));
    return ti;
  }

}