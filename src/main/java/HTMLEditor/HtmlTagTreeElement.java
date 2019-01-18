package HTMLEditor;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.navigation.LocationPresentation;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.HtmlUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

    // this.getPresentableText() contains the desired id: "details#[my-id].method"
    String presentableText = this.getDefaultPresentableText();
    if (presentableText.startsWith("details#") && presentableText.endsWith(".method")) {
      int begin = presentableText.indexOf("#");
      int end = presentableText.lastIndexOf(".");

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
      // don't show <summary>
      if (xmlTag.getSubTags().length == 0) continue;

      // TODO: how to get rid of sub-tags of methods?
      // MyXmlTag newTag = new MyXmlTag(xmlTag);

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

    // TODO: consider a method to extract ID of method
    String original = HtmlUtil.getTagPresentation(tag);
    if (original.startsWith("details#") && original.endsWith(".method")) {
      int begin = original.indexOf("#");
      int end = original.lastIndexOf(".");

      return original.substring(begin + 1, end - 1); // end-1 to get rid of the trailing 'V'
    }

    return HtmlUtil.getTagPresentation(tag);
  }

  @Override
  public Icon getIcon(boolean open) {
    final XmlTag xmlTag = getElement();

    if (xmlTag != null && xmlTag.getAttribute("data-access-flags") != null) {
      String modifier = xmlTag.getAttribute("data-access-flags").getValue();
      System.out.println(modifier);
      //      Messages.showInfoMessage(modifier, "Modifiers?");

      return foobar(modifier);
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

  Icon foobar(String modifier) {
    Icon iconRet;

    if (modifier.contains("static") && modifier.contains("final")) {
      if (modifier.contains("public")) {
        iconRet = OutlineIcons.METHOD_STATIC_FINAL_PUBLIC;
      } else if (modifier.contains("private")) {
        iconRet = OutlineIcons.METHOD_STATIC_FINAL_PRIVATE;
      } else if (modifier.contains("protected")) {
        iconRet = OutlineIcons.METHOD_STATIC_FINAL_PROTECTED;
      } else {
        iconRet = OutlineIcons.METHOD_STATIC_FINAL_PACKAGE;
      }
    } else if (modifier.contains("static")) {
      if (modifier.contains("public")) {
        iconRet = OutlineIcons.METHOD_STATIC_PUBLIC;
      } else if (modifier.contains("private")) {
        iconRet = OutlineIcons.METHOD_STATIC_PRIVATE;
      } else if (modifier.contains("protected")) {
        iconRet = OutlineIcons.METHOD_STATIC_PROTECTED;
      } else {
        iconRet = OutlineIcons.METHOD_STATIC_PACKAGE;
      }
    } else if (modifier.contains("final")) {
      if (modifier.contains("public")) {
        iconRet = OutlineIcons.METHOD_FINAL_PUBLIC;
      } else if (modifier.contains("private")) {
        iconRet = OutlineIcons.METHOD_FINAL_PRIVATE;

      } else if (modifier.contains("protected")) {
        iconRet = OutlineIcons.METHOD_FINAL_PROTECTED;
      } else {
        iconRet = OutlineIcons.METHOD_FINAL_PACKAGE;
      }
    } else {
      if (modifier.contains("public")) {
        iconRet = OutlineIcons.METHOD_PUBLIC;
      } else if (modifier.contains("private")) {
        iconRet = OutlineIcons.METHOD_PRIVATE;
      } else if (modifier.contains("protected")) {
        iconRet = OutlineIcons.METHOD_PROTECTED;
      } else {
        iconRet = OutlineIcons.METHOD_PACKAGE;
      }
    }

    return iconRet;
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
