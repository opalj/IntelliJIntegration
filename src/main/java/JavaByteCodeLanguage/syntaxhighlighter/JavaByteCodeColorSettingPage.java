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

/**
 * Provides an interface to allow the user to configure the colors used for highlighting specific
 * items (of our JavaByteCode language).
 *
 * <p>The user can access it from the Settings menu (currently: Settings -> Editor -> Color Scheme
 * -> JavaByteCode).
 *
 * <p>Note that the settings page must be registered in the com.intellij.colorSettingsPage extension
 * point.
 */
public class JavaByteCodeColorSettingPage implements ColorSettingsPage {
  private static final AttributesDescriptor[] DESCRIPTORS =
      new AttributesDescriptor[] {
        new AttributesDescriptor("Keyword", JavaByteCodeSyntaxHighlighter.TYPE),
        new AttributesDescriptor("Number", JavaByteCodeSyntaxHighlighter.NUMBER),
        new AttributesDescriptor("Instruction", JavaByteCodeSyntaxHighlighter.MNEMONIC),
        new AttributesDescriptor("Comment", JavaByteCodeSyntaxHighlighter.COMMENT),
        new AttributesDescriptor("String", JavaByteCodeSyntaxHighlighter.STRING),
        new AttributesDescriptor("Annotation", JavaByteCodeSyntaxHighlighter.ANNOTATION),
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
    return "public class java.io.ObjectStreamField extends java.lang.Object implements java.lang.Comparable \n"
        + "// Source File: ObjectStreamField.java -- Version: (Java 8) -- Size: \n"
        + "\n"
        + "Fields {\n"
        + "private final java.lang.reflect.Field field\n"
        + "private final java.lang.String name\n"
        + "private int offset\n"
        + "private final java.lang.String signature\n"
        + "private final java.lang.Class type\n"
        + "private final boolean unshared\n"
        + "}\n"
        + "\n"
        + "\n"
        + "Methods {\n"
        + "\n"
        + "@java.lang.Deprecated\n"
        + "public void <init>(java.lang.String,java.lang.Class) { // [size :8 bytes, max stack: 4 bytes, max locals: 3] \n"
        + "\tPC     Line   Instruction\n"
        + "\t0      67     ALOAD_0\n"
        + "\t1      67     ALOAD_1\n"
        + "\t2      67     ALOAD_2\n"
        + "\t3      67     ICONST_0\n"
        + "\t4      67     INVOKESPECIAL(java.io.ObjectStreamField{ void <init>(java.lang.String,java.lang.Class,boolean) })\n"
        + "\t1      203    INVOKEVIRTUAL(java.io.ObjectStreamField{ boolean isPrimitive() })\n"
        + "\t6      1482   LOOKUPSWITCH(default:34[(case:-127,31)(case:128,26)])\n"
        + "\t261    122    LDC(\"illegal signature\")\n"
        + "\t7      68     RETURN\n"
        + "}\n"
        + "\n"
        + "} ";
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
