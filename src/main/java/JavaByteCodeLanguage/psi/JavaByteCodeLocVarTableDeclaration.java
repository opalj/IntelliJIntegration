// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;

public interface JavaByteCodeLocVarTableDeclaration extends JavaByteCodeNamedElement {

  @NotNull
  JavaByteCodeLocVarTableHead getLocVarTableHead();

  @NotNull
  List<JavaByteCodeType> getTypeList();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();
}
