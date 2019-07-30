// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import JavaByteCodeLanguage.psi.JavaByteCodeNamedElement;
import com.intellij.navigation.ItemPresentation;

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
