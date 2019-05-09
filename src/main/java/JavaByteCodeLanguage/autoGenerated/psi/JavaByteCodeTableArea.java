/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.autoGenerated.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JavaByteCodeTableArea extends PsiElement {

  @Nullable
  JavaByteCodeExceptionTableDeclaration getExceptionTableDeclaration();

  @Nullable
  JavaByteCodeLocVarTableDeclaration getLocVarTableDeclaration();

  @Nullable
  JavaByteCodeLocVarTypeTableDeclaration getLocVarTypeTableDeclaration();

  @Nullable
  JavaByteCodeStackMapTableDeclaration getStackMapTableDeclaration();

  @NotNull
  PsiElement getLbracket();

  @NotNull
  PsiElement getRbracket();

}
