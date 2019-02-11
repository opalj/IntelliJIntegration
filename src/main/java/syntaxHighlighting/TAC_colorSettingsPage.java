package syntaxHighlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.*;
import java.util.Map;
import javax.swing.*;
import org.jetbrains.annotations.*;

public class TAC_colorSettingsPage implements ColorSettingsPage {
  private static final AttributesDescriptor[] DESCRIPTORS =
      new AttributesDescriptor[] {
        new AttributesDescriptor("Key", syntaxHighlighting.SyntaxHighlighter.KEY),
        new AttributesDescriptor("Separator", syntaxHighlighting.SyntaxHighlighter.SEPARATOR),
        new AttributesDescriptor("Value", syntaxHighlighting.SyntaxHighlighter.VALUE),
      };

  @Nullable
  @Override
  public Icon getIcon() {
    return TAC_filetype.ICON;
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new syntaxHighlighting.SyntaxHighlighter();
  }

  @NotNull
  @Override
  public String getDemoText() {
    return "# You are reading the \".properties\" entry.\n"
        + "! The exclamation mark can also mark text as comments.\n"
        + "website = http://en.wikipedia.org/\n"
        + "language = English\n"
        + "# The backslash below tells the application to continue reading\n"
        + "# the value onto the next line.\n"
        + "message = Welcome to \\\n"
        + "          Wikipedia!\n"
        + "# Add spaces to the key\n"
        + "key\\ with\\ spaces = This is the value that could be looked up with the key \"key with spaces\".\n"
        + "# Unicode\n"
        + "tab : \\u0009";
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
