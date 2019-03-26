package taclanguage.syntaxhighlighter;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.*;
import org.jetbrains.annotations.*;
import taclanguage.languageandfiletype.TAC_filetype;

import javax.swing.*;
import java.util.Map;

public class TAC_colorSettingsPage implements ColorSettingsPage {
  private static final AttributesDescriptor[] DESCRIPTORS =
          new AttributesDescriptor[] {
                  new AttributesDescriptor("Keyword", TAC_syntaxHighlighter.TYPE),
                  new AttributesDescriptor("Number", TAC_syntaxHighlighter.NUMBER),
                  new AttributesDescriptor("Instruction", TAC_syntaxHighlighter.LEVEL),
                  new AttributesDescriptor("Comment", TAC_syntaxHighlighter.COMMENT),
                  new AttributesDescriptor("String", TAC_syntaxHighlighter.STRING),
                  new AttributesDescriptor("Annotation", TAC_syntaxHighlighter.ANNOTATION),
          };

  @Nullable
  @Override
  public Icon getIcon() {
    return TAC_filetype.ICON;
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new TAC_syntaxHighlighter();
  }

  @NotNull
  @Override
  public String getDemoText() {
    return "0: lv0 = \"Argument for @NotNull parameter '%s' of %s.%s must not be null\"\n"
        + "1: lv1 = 3\n"
        + "2: lv2 = new java.lang.Object[{lv1}]\n"
        + "3: switch({param1}){\n"
        + "0: goto 4;\n"
        + "1: goto 8;\n"
        + "default: goto 4\n"
        + "}\n"
        + "// 3 →\n"
        + "6: {lv2}[{lv4}] = {lv5}\n"
        + "17: lv11 = java.lang.String.format({lv0}, {lv2})\n"
        + "//\t<uncaught exception  abnormal return>";
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    return null;
  }

  @NotNull
  @Override
  public AttributesDescriptor[] getAttributeDescriptors() {
    return DESCRIPTORS;
  }

  @NotNull
  @Override
  public ColorDescriptor[] getColorDescriptors() {
    return ColorDescriptor.EMPTY_ARRAY;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "TAC";
  }
}
