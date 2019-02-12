// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JavaByteCodeMethodName extends JavaByteCodeNamedElement {

  @NotNull
  List<JavaByteCodeType> getTypeList();

  @Nullable
  PsiElement getStringvar();

  String getKey();

  //WARNING: getValue(...) is skipped
  //matching getValue(JavaByteCodeMethodName, ...)
  //methods are not found in JavaByteCodePsiImplUtil

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

}
