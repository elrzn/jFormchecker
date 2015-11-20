package de.jformchecker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

public class Utils {
  public static void fillBean(List<FormCheckerElement> elements, Object bean) {
    for (FormCheckerElement elem : elements) {
      String key = elem.getName();
      if (PropertyUtils.isWriteable(bean, key)) {
        try {
          // TODO: Destinguish between Strings/Dates/Boolean...
          PropertyUtils.setSimpleProperty(bean, key, elem.getValue());
        } catch (Exception e) {
          System.err.println("problem with setting bean-property:" + key);
        }
      }
    }
  }
  
   public static String buildAttributes(Map<String, String> attributes) {
    StringBuilder attrStr = new StringBuilder();
    for (String attribute : attributes.keySet()) {
      attrStr.append(attribute).append("=\"").append(attributes.get(attribute)).append("\" ");
    }
    return attrStr.toString();
  }

   public static String buildAttributes(TagAttributes attributes) {
     StringBuilder attrStr = new StringBuilder();
     for (String attribute : attributes.keySet()) {
       attrStr.append(attribute).append("=\"").append(attributes.get(attribute)).append("\" ");
     }
     return attrStr.toString();
   }

   
  /**
   * Return a nicely formated form of the form for debugging or other purposes
   * @param fc-elements 
   * @return
   */
  public static String getDebugOutput(Map<String, FormCheckerElement> elements) {
    int maxLen = 0;
    for (String key : elements.keySet()) {
      if (key.length() > maxLen) {
        maxLen = key.length();
      }
    }
    maxLen += 3;
    StringBuilder debugOutput = new StringBuilder();
    for (String key : elements.keySet()) {
      
      debugOutput.append(key).append(StringUtils.leftPad(":", maxLen - key.length()));
      debugOutput.append(elements.get(key).getValue()).append("\n");
    }
    return debugOutput.toString();
  }
}
