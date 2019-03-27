package opalintegration;

@Deprecated
public class JavaScript {

  static String jsOpenField() {

    return "<script>\n"
        + "function openFields() {\n"
        + "   document.getElementsByClassName(\"fields\")[0].getElementsByTagName(\"details\")[0].open = true;"
        + "}\n"
        + "</script>\n";
  }

  static String jsOpenMethod() {

    return "<script>\n"
        + "function openMethods() {\n"
        + "   document.getElementsByClassName(\"methods\")[0].getElementsByTagName(\"details\")[0].open = true;"
        + "}\n"
        + "</script>\n";
  }

  static String jsScrollToField() {
    // differentiate between light and dark IDE theme
    String lightThemeHighlight = "\"#FDFF47\"";
    String darkThemeHighlight = "\"#ABCDEF\"";

    //    String highlightColor = JBColor.isBright() ? lightThemeHighlight : darkThemeHighlight;
    String originalColor = "\"#FFFFFF\"";

    // orig (e.g. get it from CSS)?

    return "<script>\n"
        + "function scrollToField(dataName) {\n"
        + "    var elements = document.getElementsByClassName(\"field details\");\n"
        + "    for(var i=0; i < elements.length; i++) {\n"
        + "       var element = elements[i];\n"
        + "       if(element.getAttribute(\"data-name\") == dataName) {\n"
        + "         element.scrollIntoView();\n"
        + "         element.style.backgroundColor = "
        + lightThemeHighlight
        + ";\n"
        + "         window.setTimeout(function(){\n"
        + "           element.style.backgroundColor = "
        + originalColor
        + ";\n"
        + "         }, 2000);\n"
        + "         return;\n"
        + "       }\n"
        + "    }\n"
        + "}\n"
        + "</script>\n";
  }

  static String jsScrollToID() {
    // differentiate between light and dark IDE theme
    String lightThemeHighlight = "\"#FDFF47\"";
    String darkThemeHighlight = "\"#ABCDEF\"";

    //    String highlightColor = JBColor.isBright() ? lightThemeHighlight : darkThemeHighlight;
    String originalColor = "\"#F7F7F7\"";

    // orig (e.g. get it from CSS)?

    return "<script>\n"
        + "function scrollTo(elementId) {\n"
        + "    var element = document.getElementById(elementId);\n"
        + "    element.scrollIntoView(); \n"
        + "    element.open  = true;\n"
        + "    element.style.backgroundColor = "
        + lightThemeHighlight
        + ";\n"
        + "    window.setTimeout(function(){\n"
        + "    element.style.backgroundColor = "
        + originalColor
        + ";\n"
        + "    }, 2000);\n"
        + "}\n"
        + "</script>\n";
  }
}
