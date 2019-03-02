package JavaByteCodeLanguage.syntaxhighlighter;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import java.util.Map;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaByteCodeColorSettingPage implements ColorSettingsPage {
  private static final AttributesDescriptor[] DESCRIPTORS =
      new AttributesDescriptor[] {
        new AttributesDescriptor("Type", JavaByteCodeSyntaxHighlighter.TYPE),
        new AttributesDescriptor("Number", JavaByteCodeSyntaxHighlighter.NUMBER),
        new AttributesDescriptor("Instruction", JavaByteCodeSyntaxHighlighter.MNEMONIC),
        new AttributesDescriptor("Comment", JavaByteCodeSyntaxHighlighter.COMMENT),
        new AttributesDescriptor("String", JavaByteCodeSyntaxHighlighter.STRING)
      };

  @Nullable
  @Override
  public Icon getIcon() {
    return null;
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new JavaByteCodeSyntaxHighlighter();
  }

  @NotNull
  @Override
  public String getDemoText() {
    return "Das ist ein Test";
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
    return new ColorDescriptor[0];
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return "JavaByteCode";
  }
}
