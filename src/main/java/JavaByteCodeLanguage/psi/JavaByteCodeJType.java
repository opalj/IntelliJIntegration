// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.*;

public interface JavaByteCodeJType extends JavaByteCodeNamedElement {

  @Nullable
  String getJavaType();

  String getName();

  PsiElement setName(String newName);

  @Nullable
  PsiElement getNameIdentifier();

  PsiReference[] getReferences();
}
