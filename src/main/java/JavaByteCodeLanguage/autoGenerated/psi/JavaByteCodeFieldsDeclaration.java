// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi;

import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.*;

public interface JavaByteCodeFieldsDeclaration extends JavaByteCodeNamedElement {

  @NotNull
  JavaByteCodeDefMethodName getDefMethodName();

  @Nullable
  JavaByteCodeModifierV getModifierV();

  @NotNull
  JavaByteCodeType getType();

  String getName();

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  void navigate(boolean requestFocus);
}