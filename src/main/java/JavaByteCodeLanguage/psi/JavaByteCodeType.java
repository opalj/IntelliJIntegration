// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JavaByteCodeType extends JavaByteCodeNamedElement {

  @Nullable
  PsiElement getJavatype();

  @Nullable
  PsiElement getPrimitivetype();

  String getKey();

  //WARNING: getValue(...) is skipped
  //matching getValue(JavaByteCodeType, ...)
  //methods are not found in JavaByteCodePsiImplUtil

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

}
