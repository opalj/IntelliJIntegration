/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package Editors.HTMLEditor;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.navigation.LocationPresentation;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.HtmlUtil;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class HtmlTagTreeElement extends PsiTreeElementBase<XmlTag> implements LocationPresentation {
  static final int MAX_TEXT_LENGTH = 50;

  private HTMLEditor htmlEditor;

  // xxx#someMethod(possiblySomeParams)retType.xxx
  private final String simpleDefaultPresentableTextRegex = "(.*)#.+[(].*[)](.*)\\.(.*)";

  // _TODO: move this to some utility class (maybe Opal)?
  private static final Map<String, String> primitiveTypesMap =
      Stream.of(
              new SimpleEntry<>("Z", "boolean"),
              new SimpleEntry<>("B", "byte"),
              new SimpleEntry<>("C", "char"),
              new SimpleEntry<>("S", "short"),
              new SimpleEntry<>("I", "int"),
              new SimpleEntry<>("J", "long"),
              new SimpleEntry<>("F", "float"),
              new SimpleEntry<>("D", "double"),
              new SimpleEntry<>("V", "void"))
          .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

  private static final Map<String, Integer> modifierSortOrder =
      Stream.of(
              new SimpleEntry<>("public", 0),
              new SimpleEntry<>("private", 0),
              new SimpleEntry<>("protected", 0),
              new SimpleEntry<>("static", 1),
              new SimpleEntry<>("final", 2),
              new SimpleEntry<>("abstract", 2),
              new SimpleEntry<>("default", 3))
          .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

  HtmlTagTreeElement(final XmlTag tag, HTMLEditor htmlEditor) {
    super(tag);
    this.htmlEditor = htmlEditor;
  }

  @Override
  public void navigate(boolean requestFocus) {
    WebEngine webEngine = htmlEditor.getWebEngine();

    // this.getPresentableText() contains the desired id, e.g.: "details#[my-id].method"
    String presentableText = this.getDefaultPresentableText();

    if (presentableText.matches(simpleDefaultPresentableTextRegex)) {
      int begin = presentableText.indexOf("#");
      int end = presentableText.indexOf(".");

      String id = presentableText.substring(begin + 1, end);

      Runnable run =
          () -> {
            webEngine.executeScript("openMethods()");
            webEngine.executeScript("scrollTo(\"" + id + "\")");
          };
      Platform.runLater(run);
    }
    // check if it's field ...
    else if (presentableText.equals("div.field.details")) {
      String dataName = getElement().getAttribute("data-name").getValue();
      Runnable run =
          () -> {
            webEngine.executeScript("openFields()");
            webEngine.executeScript("scrollToField(\"" + dataName + "\")");
          };
      Platform.runLater(run);
    }
  }

  // the default text representation is needed for retrieving the tag-ID of the method
  private String getDefaultPresentableText() {
    final XmlTag tag = getElement();
    if (tag == null) {
      return IdeBundle.message("node.structureview.invalid");
    }

    return HtmlUtil.getTagPresentation(tag);
  }

  @Override
  @NotNull
  public Collection<StructureViewTreeElement> getChildrenBase() {
    final XmlTag tag = getElement();
    if (tag == null || !tag.isValid()) return Collections.emptyList();

    List<StructureViewTreeElement> result = new ArrayList<>();
    for (XmlTag xmlTag : tag.getSubTags()) {
      // don't show children of <summary>
      if (xmlTag.getSubTags().length == 0) continue;

      XmlAttribute classAttribute = xmlTag.getAttribute("class");
      String classAttributeValue = classAttribute != null ? classAttribute.getValue() : "";

      // careful: abstract methods also have a div tag with class="details method
      // native_or_abstract"
      if (xmlTag.getName().equals("div")
          && !classAttributeValue.equals("details method native_or_abstract")
          && !classAttributeValue.equals("field details")) {
        continue;
      }
      // child of abstract: <span class="method_declaration">
      else if (xmlTag.getName().equals("span")
          && (classAttributeValue.equals("method_declaration")
              || classAttributeValue.equals("field_declaration"))) {
        continue;
      }

      result.add(new HtmlTagTreeElement(xmlTag, htmlEditor));
    }

    return result;
  }

  @Override
  public String getPresentableText() {
    final XmlTag tag = getElement();
    if (tag == null) {
      return IdeBundle.message("node.structureview.invalid");
    }

    String defaultPresentation = HtmlUtil.getTagPresentation(tag);

    // fields (maybe add initialized value as well? e.g. "myField: double = 2")
    XmlAttribute classAttribute = tag.getAttribute("class");
    if (classAttribute != null && classAttribute.getValue().equals("field details")) {
      return getPresentableTextForField(tag);
    }

    // methods
    if (defaultPresentation.matches(simpleDefaultPresentableTextRegex)) {
      return getPresentableTextForMethod(defaultPresentation);
    }

    // return the default in case something went wrong (e.g. unanticipated text format)
    return defaultPresentation;
  }

  @NotNull
  private String getPresentableTextForField(@NotNull XmlTag tag) {
    XmlTag field = tag.findFirstSubTag("span");
    String type = "";
    String name = "";

    for (XmlTag fieldSub : field.getSubTags()) {
      XmlAttribute classAttribute = fieldSub.getAttribute("class");
      if (classAttribute == null) {
        continue;
      } else if (classAttribute.getValue().contains("type")) {
        type = fieldSub.getValue().getText();
      } else if (classAttribute.getValue().equals("name")) {
        name = fieldSub.getValue().getText();
      }
    }

    if (type.contains(".")) {
      type = type.substring(type.lastIndexOf(".") + 1);
    }
    return name + ": " + type;
  }

  @NotNull
  private String getPresentableTextForMethod(@NotNull String defaultPresentation) {
    int begin = defaultPresentation.indexOf("#");
    int end = defaultPresentation.indexOf(".");

    String preParamFormat = defaultPresentation.substring(begin + 1, end - 1);
    String postParamFormat = formatParameters(preParamFormat);

    // the return type begins after the closing ')'
    int retTypeIdxBegin = defaultPresentation.indexOf(')') + 1;
    String returnType = formatReturnType(defaultPresentation.substring(retTypeIdxBegin));

    return postParamFormat + ": " + returnType;
  }

  @NotNull
  private String formatParameters(@NotNull String preParamFormat) {
    int begin = preParamFormat.indexOf('(');
    int end = preParamFormat.lastIndexOf(')');

    String opalParameters = preParamFormat.substring(begin + 1, end);
    String javaParameters = replaceOpalParamsWithJavaParams(opalParameters);

    String postParamFormat = preParamFormat.substring(0, begin + 1) + javaParameters + ")";
    return postParamFormat.replace(", )", ")"); // get rid of the last comma
  }

  @NotNull
  private String replaceOpalParamsWithJavaParams(@NotNull String opalParams) {
    int commaCount = 0;
    int arrayDim = 0;

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < opalParams.length(); ++i) {
      char c = opalParams.charAt(i);

      if (c == '[') {
        ++arrayDim;
        continue;
      }

      if (commaCount >= 7) {
        sb.append("..., ");
        break;
      }

      String primitiveType = primitiveTypesMap.get(c + "");
      if (primitiveType != null) {
        sb.append(primitiveType);
        ++commaCount;
      } else if (c == 'L') {
        int semicol = opalParams.indexOf(';', i);
        String temp = opalParams.substring(0, semicol);
        int lastSlash = temp.lastIndexOf('/');
        sb.append(temp.substring(lastSlash + 1));
        ++commaCount;
        i = semicol;
      }

      if ((primitiveType != null || c == 'L') && arrayDim > 0) {
        for (int j = 0; j < arrayDim; ++j) {
          sb.append("[]");
        }
        arrayDim = 0;
      }

      sb.append(", ");
    }

    return sb.toString();
  }

  private String formatReturnType(@NotNull String preFormatRetType) {
    int arrayDim = 0;

    char c;
    for (int i = 0; i < preFormatRetType.length(); ++i) {
      c = preFormatRetType.charAt(i);
      if (c == '[') {
        ++arrayDim;
      }
    }
    c = preFormatRetType.charAt(arrayDim);

    String retType = primitiveTypesMap.get(c + "");
    if (retType == null) {
      int begin = preFormatRetType.lastIndexOf('/');
      int end = preFormatRetType.indexOf(';');
      retType = preFormatRetType.substring(begin + 1, end);
    }

    for (int i = 0; i < arrayDim; ++i) {
      retType += "[]";
    }

    return retType;
  }

  @Override
  public Icon getIcon(boolean open) {
    final XmlTag xmlTag = getElement();

    // methods and fields
    if (xmlTag != null && xmlTag.getAttribute("data-access-flags") != null) {
      String modifiers = xmlTag.getAttribute("data-access-flags").getValue();
      boolean isField = xmlTag.getAttribute("class").getText().contains("field");
      return findIconBasedOnModifiers(modifiers, isField);
    }

    // rely on the "default" implementation for everything else (creates XML-tag symbol)
    // e.g. gets called for <summary>
    return getIconFallback();
  }

  @NotNull
  private Icon findIconBasedOnModifiers(@NotNull String modifiers, boolean isField) {
    final String legalMods = "public private protected default static final abstract";

    String[] mods = modifiers.split(" ");
    mods =
        Arrays.stream(mods)
            .filter(s -> legalMods.contains(s))
            // sort in case the order in OPAL ever gets changed
            .sorted(Comparator.comparing(modifierSortOrder::get))
            .toArray(String[]::new);

    String filePrefix = isField ? "fields/field_" : "methods/method_";
    StringBuilder fileNameOfIcon = new StringBuilder(filePrefix);
    for (String mod : mods) {
      fileNameOfIcon.append(mod);
      fileNameOfIcon.append("_");
    }

    // append the file extension (.png)
    int lastIdx = fileNameOfIcon.lastIndexOf("_");
    fileNameOfIcon.replace(lastIdx, lastIdx + 1, ".png");

    return IconLoader.getIcon("icons/" + fileNameOfIcon.toString());
  }

  @Nullable
  private Icon getIconFallback() {
    final PsiElement element = getElement();
    if (element != null) {
      int flags = Iconable.ICON_FLAG_READ_STATUS;
      if (!(element instanceof PsiFile) || !element.isWritable())
        flags |= Iconable.ICON_FLAG_VISIBILITY;
      return element.getIcon(flags);
    } else {
      return null;
    }
  }

  @Nullable
  @Override
  public String getLocationString() {
    final XmlTag tag = getElement();
    if (tag == null) {
      return null;
    }

    if (tag.getName().equalsIgnoreCase("img") || HtmlUtil.isScriptTag(tag)) {
      return getPathDescription(tag.getAttributeValue("src"));
    } else if (tag.getName().equalsIgnoreCase("link")) {
      return getPathDescription(tag.getAttributeValue("href"));
    } else {
      return StringUtil.nullize(normalizeSpacesAndShortenIfLong(tag.getValue().getTrimmedText()));
    }
  }

  private static String getPathDescription(String src) {
    if (StringUtil.isEmpty(src)) {
      return null;
    } else {
      return StringUtil.shortenPathWithEllipsis(src, MAX_TEXT_LENGTH, true);
    }
  }

  @Override
  public boolean isSearchInLocationString() {
    return true;
  }

  @Nullable
  public static String normalizeSpacesAndShortenIfLong(final @NotNull String text) {
    StringBuilder builder = normalizeSpaces(text);
    return builder == null ? null : shortenTextIfLong(builder);
  }

  @Nullable
  private static StringBuilder normalizeSpaces(@NotNull String text) {
    if (text.isEmpty()) {
      return null;
    }

    final StringBuilder buf = new StringBuilder(text.length());
    for (int i = 0, length = text.length(); i < length; i++) {
      char c = text.charAt(i);
      if (c <= ' ' || Character.isSpaceChar(c)) {
        if (buf.length() == 0 || buf.charAt(buf.length() - 1) != ' ') {
          buf.append(' ');
        }
      } else {
        buf.append(c);
      }
    }
    return buf;
  }

  @Nullable
  private static String shortenTextIfLong(@NotNull StringBuilder text) {
    if (text.length() <= MAX_TEXT_LENGTH) {
      return text.toString();
    }

    int index;
    for (index = MAX_TEXT_LENGTH; index > MAX_TEXT_LENGTH - 20; index--) {
      if (!Character.isLetter(text.charAt(index))) {
        break;
      }
    }

    text.setLength(Character.isLetter(index) ? MAX_TEXT_LENGTH : index);
    return text.append("\u2026").toString();
  }

  @Override
  public String getLocationPrefix() {
    return "  ";
  }

  @Override
  public String getLocationSuffix() {
    return "";
  }
}
