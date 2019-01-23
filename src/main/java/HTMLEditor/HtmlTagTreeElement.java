package HTMLEditor;

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

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HtmlTagTreeElement extends PsiTreeElementBase<XmlTag> implements LocationPresentation {
  static final int MAX_TEXT_LENGTH = 50;

  private MyHtmlEditor htmlEditor;

  HtmlTagTreeElement(final XmlTag tag, MyHtmlEditor htmlEditor) {
    super(tag);
    this.htmlEditor = htmlEditor;
  }

  @Override
  public void navigate(boolean requestFocus) {
    WebEngine webEngine = htmlEditor.getWebEngine();

    // this.getPresentableText() contains the desired id, e.g.: "details#[my-id].method"
    String presentableText = this.getDefaultPresentableText();

    // presentableText may have different forms (e.g. for abstract methods), but in general looks like this:
    // xxx#someMethodName(maybeSomeParams)[*some capital letter*].yyy[...]
    String simpleRegex = "(.*)#(.+[(].*[)][A-Z])\\.(.*)";

    if(presentableText.matches(simpleRegex)) {
      int begin = presentableText.indexOf("#");
      int end = presentableText.indexOf(".");

      // TODO: init() should be <init>() ... problem: what if there is an actual init() method
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
      // TODO: probably have to adjust the HTML file to contain field names in tag-attributes
      // (currently ambiguous)
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

      // this basically gets rid of the children
      // just keep it in mind in case you make changes to MyStructureViewTreeElement#getChildrenBase()
      // because currently it assumes that we start at the proper method level
      XmlAttribute classAttribute = xmlTag.getAttribute("class");
      String classAttributeValue = classAttribute != null ? classAttribute.getValue() : "";

      // careful: abstract methods also have a div tag with class="details method native_or_abstract"
      if(xmlTag.getName().equals("div") && !classAttributeValue.equals("details method native_or_abstract")) {
        continue;
      }
      // child of abstract: <span class="method_declaration">
      else if(xmlTag.getName().equals("span") && classAttributeValue.equals("method_declaration")) {
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

    // TODO: verify that this regex is correct in all cases
    // explanation:
    // \\w+: one or more word characters (details, or div)
    // #: a single #
    // .+: the method name TODO: is .+ ok? maybe replace the .+ with actual legal characters for Java identifiers
    // [(].*[)]: ( param-list ) TODO: is .* ok?
    // [A-Z]: a single capital letter (mostly V or I, but maybe there are others?)
    // \\.: a single dot
    // \\w+(\\.\\w+)*: one more word (then 0-inf many words, all separated by a dot)
    String regex = "\\w+#.+[(].*[)][A-Z]\\.\\w+(\\.\\w+)*";

    // TODO: consider a method to extract ID of method
    String original = HtmlUtil.getTagPresentation(tag);
//    System.out.println(original);

    if (original.startsWith("details#") && original.endsWith(".method")
          || original.matches(regex)) {
      int begin = original.indexOf("#");
      int end = original.indexOf(".");

      String preParamFormat = original.substring(begin + 1, end - 1);
      String postParamFormat = formatParameters(preParamFormat);

      // TODO: properly format parameter list, and try to append the <summary> stuff?
//      return original.substring(begin + 1, end - 1); // end-1 to get rid of the trailing 'V'
      return postParamFormat;
    }

    return HtmlUtil.getTagPresentation(tag);
  }

  private String formatParameters(String preParamFormat) {
    String postParamFormat = "";

    int begin = preParamFormat.indexOf('(');
    int end = preParamFormat.lastIndexOf(')');

    String parameters = preParamFormat.substring(begin + 1, end);

    int commaCount = 0;
    StringBuilder sb = new StringBuilder();
    for(int i=0; i < parameters.length(); ++i) {
      char c = parameters.charAt(i);

      if(commaCount >= 7) {
        sb.append("..., ");
        break;
      }

      if("ZBCSIJFD[L".contains(c + "")) {
        ++commaCount;
      }

      switch(c) {
        case 'Z':
          sb.append("boolean, ");
          break;
        case 'B':
          sb.append("byte, ");
          break;
        case 'C':
          sb.append("char, ");
          break;
        case 'S':
          sb.append("short, ");
          break;
        case 'I':
          sb.append("int, ");
          break;
        case 'J':
          sb.append("long, ");
          break;
        case 'F':
          sb.append("float, ");
          break;
        case 'D':
          sb.append("double, ");
          break;
        case 'L':
        case '[':
          int semicol = parameters.indexOf(';', i);
          String temp = parameters.substring(0, semicol);
          int lastSlash = temp.lastIndexOf('/');
          sb.append(temp.substring(lastSlash + 1));
          if(c == '[') sb.append("[], ");
          else sb.append(", ");
          i = semicol;
          break;
      }
    }

    // primitives
//    parameters = parameters.replaceAll("Z && ?!(L.*/Z.*;).*?", "boolean, ");
//    parameters = parameters.replaceAll("B && ?!(L.*/B.*;).*?", "byte, ");
//    parameters = parameters.replaceAll("C && ?!(L.*/C.*;).*?", "char, ");
//    parameters = parameters.replaceAll("S && ?!(L.*/S.*;).*?", "short, ");
//    parameters = parameters.replaceAll("I && ?!(L.*/I.*;).*?", "int, ");
//    parameters = parameters.replaceAll("L && ?!(L.*/L.*;).*?", "long, ");
//    parameters = parameters.replaceAll("F && ?!(L.*/F.*;).*?", "float, ");
//    parameters = parameters.replaceAll("D && ?!(L.*/D.*;).*?", "double, ");
//
//    // Objects begin with L, arrays begin with [
//    String objectRegex = "[\\[]?L.*/\\w+;";
//    while(parameters.matches("(.*)" + objectRegex + "(.*)")) {
//      int next = parameters.indexOf(";");
//      String temp = parameters.substring(0, next);
//
//      int lastSlashIdx = temp.lastIndexOf("/");
//      temp = temp.substring(lastSlashIdx + 1);
//
//      if(parameters.contains("[L") && parameters.indexOf("[L") < next) {
//        parameters = parameters.replaceFirst("[\\[]?L.*/\\w+;", temp + "[], "
//                + parameters.substring(next + 1));
//      }
//      else {
//        parameters = parameters.replaceFirst("[\\[]?L.*/\\w+;", temp + ", "
//                + parameters.substring(next + 1));
//      }
//
//      System.out.println(parameters);
//    }

//    postParamFormat = preParamFormat.substring(0, begin + 1) + parameters + ")";
    postParamFormat = preParamFormat.substring(0, begin + 1) + sb.toString() + ")";
    return postParamFormat.replace(", )", ")"); // get rid of the last comma
  }

  @Override
  public Icon getIcon(boolean open) {
    final XmlTag xmlTag = getElement();

    if (xmlTag != null && xmlTag.getAttribute("data-access-flags") != null) {
      String modifiers = xmlTag.getAttribute("data-access-flags").getValue();
//      System.out.println(modifiers);

      return findIconBasedOnModifiers(modifiers);
    }

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

  // TODO: sort modifiers so that it won't break when order is changed in HTML file
  Icon findIconBasedOnModifiers(String modifiers) {
    // considering only methods:
    // [public|private|protected] static final
    // abstract is incompatible with private, static, and final
    // default always comes last, e.g. static final default
    // get rid of /*SYNTHETIC*/

    final String legalMods = "public private protected default static final abstract";

    String[] mods = modifiers.split(" ");
    mods = Arrays.stream(mods).filter(s -> legalMods.contains(s)).toArray(String[]::new);

    StringBuilder fileNameOfIcon = new StringBuilder("method_");
    for(String mod : mods) {
      fileNameOfIcon.append(mod);
      fileNameOfIcon.append("_");
    }

    // append the file extension (.png)
    int lastIdx = fileNameOfIcon.lastIndexOf("_");
    fileNameOfIcon.replace(lastIdx, lastIdx+1, ".png");


    return IconLoader.getIcon("icons/methods/" + fileNameOfIcon.toString());
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
