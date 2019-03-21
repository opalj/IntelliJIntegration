// This is a generated file. Not intended for manual editing.
package syntaxHighlighting;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

public interface TACJType extends TAC_namedElement {

  @NotNull
  PsiElement getJavaType();

  @Nullable
  String getJavaTypeString();

  String getName();

  PsiElement setName(String newName);

  @Nullable
  PsiElement getNameIdentifier();

  PsiReference[] getReferences();

}
