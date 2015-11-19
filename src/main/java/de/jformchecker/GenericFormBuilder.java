package de.jformchecker;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Builds: a generic form the label-elements
 * 
 * @author jochen
 *
 */
public class GenericFormBuilder {

  String errStyle = "error";
  String okStyle = "";
  String requiredChars = " *";
  String addToLabel = ": ";
  String labelStyle = "";
  String submitLabel = "OK";
  String submitClass = "";

  final public String getGenericForm(String id, String formAction,
      Map<String, FormCheckerElement> elements, boolean isMultipart, boolean firstRun,
      FormChecker fc) {
    StringBuilder formHtml = new StringBuilder();
    String novalidateAddition = "";
    if (fc.getForm() != null && fc.getForm().html5Validation == false) {
      novalidateAddition = " novalidate ";
    }
    if (isMultipart) {
      formHtml.append("<form name=\"" + id + "\" "+novalidateAddition+"id=\"form_" + id + "\" action=\"" + formAction
          + "\" method=\"GET\" enctype=\"multipart/form-data\">\n");
    } else {
      formHtml.append("<form name=\"" + id + "\" "+novalidateAddition+"id=\"form_" + id + "\" "+
          Utils.buildAttributes(getFormAttributes())+" action=\"" + formAction
          + "\" method=\"GET\" >\n");
    }
    formHtml.append(getSubmittedTag(id));
    if (fc.protectedAgainstCSRF) {
      formHtml.append(fc.buildCSRFTokens());
    }
    int lastTabIndex = 0;
    for (String key : elements.keySet()) {
      // label
      FormCheckerElement elem = elements.get(key);
      Wrapper elementWrapper = getWrapperForElem(elem);
      formHtml.append(elementWrapper.start);
      formHtml.append(getErrors(elem, firstRun));
      boolean displayLabel = !StringUtils.isEmpty(elem.getDescription()); 
      if (displayLabel) {
        formHtml.append(getLabelForElement(elem, getLabelAttributes(elem), firstRun)).append("\n");
      }
      // input tag
      Map<String, String> attribs = new LinkedHashMap<>();
      addAttributesToInputFields(attribs, elem);
      Wrapper inputWrapper = getWrapperForInput(elem);
      formHtml.append(inputWrapper.start);
      formHtml.append(elem.getInputTag(attribs));
      if (displayLabel) {
        formHtml.append("\n<br>"); // only append nl, if something was given
                               // out
      }
      formHtml.append(inputWrapper.end);
      formHtml.append(elementWrapper.end);
      lastTabIndex = elem.getLastTabIndex();
    }
    formHtml.append(getSubmit(lastTabIndex + 1));
    formHtml.append("</form>\n");

    return formHtml.toString();
  }


  public TagAtrributes getLabelAttributes(FormCheckerElement elem) {
    TagAtrributes attributes = new TagAtrributes();
    attributes.put("class", "col-sm-2 control-label");
    return attributes;
  }


  public Wrapper getWrapperForInput(FormCheckerElement elem) {
    return new Wrapper("<div class=\"col-sm-10\">", "</div>");
  }


  public TagAtrributes getFormAttributes() {
    TagAtrributes attributes = new TagAtrributes();
    attributes.put("class", "form-horizontal");
    return attributes;
  }

  public void addAttributesToInputFields(Map<String, String> attribs, FormCheckerElement elem) {
    attribs.put("class", "form-control");
  }


  // returns the HTML code that should be given out, before and after an input-element is written
  public Wrapper getWrapperForElem(FormCheckerElement elem) {
    return new Wrapper("<div class=\"form-group\">", "</div>");
  }


  public String getSubmit(int tabOrder) {
    return "<input tabindex=\"" + tabOrder + "\" class=\"" + submitClass
        + "\" type=\"submit\" value=\"" + submitLabel + "\">\n";
  }

  public String getSubmit() {
    return this.getSubmit(0);
  }



  public String getErrors(FormCheckerElement e, boolean firstRun) {
    if (!firstRun && !e.isValid()) {
      return ("Problem: " + e.getErrorMessage() + "!!<br>");
    }
    return "";
  }

  public String getLabelForElement(FormCheckerElement e, TagAtrributes attribs,
      boolean firstRun) {

    
    String statusClassToAdd = errStyle;
    if (firstRun || e.isValid()) {
      statusClassToAdd = okStyle;
    } 

    attribs.addToAttribute("class", statusClassToAdd);
    
    return ("<label "+Utils.buildAttributes(attribs)+" for=\"form_" + e.getName() + "\""
        + " id=\"" + e.getName() + "_label\">" + e.getDescription() + addToLabel
       + (e.isRequired() ? requiredChars : "") + "</label>");
  }

  public String getSubmittedTag(String id) {
    return "<input type=\"hidden\" name=\"" + FormChecker.SUBMIT_KEY + "\" value=\""
        + FormChecker.SUBMIT_VALUE_PREFIX + id + "\">";
  }

}
