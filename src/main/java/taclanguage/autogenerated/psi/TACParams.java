// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.*;

public interface TACParams extends PsiElement {

  @NotNull
  List<TACAnnotation> getAnnotationList();

  @NotNull
  List<TACType> getTypeList();

  @Nullable
  PsiElement getLbracket();

  @Nullable
  PsiElement getRbracket();
}
