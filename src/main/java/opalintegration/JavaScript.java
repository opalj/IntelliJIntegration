package opalintegration;

@Deprecated
public class JavaScript {

  protected static String jsOpenField() {
    String script =
        "<script>\n"
            + "function openFields() {\n"
            + "   document.getElementsByClassName(\"fields\")[0].getElementsByTagName(\"details\")[0].open = true;"
            + "}\n"
            + "</script>\n";

    return script;
  }

  protected static String jsOpenMethod() {
    String script =
        "<script>\n"
            + "function openMethods() {\n"
            + "   document.getElementsByClassName(\"methods\")[0].getElementsByTagName(\"details\")[0].open = true;"
            + "}\n"
            + "</script>\n";

    return script;
  }

  protected static String jsScrollToField() {
    // differentiate between light and dark IDE theme
    String lightThemeHighlight = "\"#FDFF47\"";
    String darkThemeHighlight = "\"#ABCDEF\"";

    //    String highlightColor = JBColor.isBright() ? lightThemeHighlight : darkThemeHighlight;
    String highlightColor = lightThemeHighlight;
    String originalColor = "\"#FFFFFF\"";

    // orig (e.g. get it from CSS)?
    String script =
        "<script>\n"
            + "function scrollToField(dataName) {\n"
            + "    var elements = document.getElementsByClassName(\"field details\");\n"
            + "    for(var i=0; i < elements.length; i++) {\n"
            + "       var element = elements[i];\n"
            + "       if(element.getAttribute(\"data-name\") == dataName) {\n"
            + "         element.scrollIntoView();\n"
            + "         element.style.backgroundColor = "
            + highlightColor
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

    return script;
  }

  protected static String jsScrollToID() {
    // differentiate between light and dark IDE theme
    String lightThemeHighlight = "\"#FDFF47\"";
    String darkThemeHighlight = "\"#ABCDEF\"";

    //    String highlightColor = JBColor.isBright() ? lightThemeHighlight : darkThemeHighlight;
    String highlightColor = lightThemeHighlight;
    String originalColor = "\"#F7F7F7\"";

    // orig (e.g. get it from CSS)?
    String script =
        "<script>\n"
            + "function scrollTo(elementId) {\n"
            + "    var element = document.getElementById(elementId);\n"
            + "    element.scrollIntoView(); \n"
            + "    element.open  = true;\n"
            + "    element.style.backgroundColor = "
            + highlightColor
            + ";\n"
            + "    window.setTimeout(function(){\n"
            + "    element.style.backgroundColor = "
            + originalColor
            + ";\n"
            + "    }, 2000);\n"
            + "}\n"
            + "</script>\n";

    return script;
  }
}
