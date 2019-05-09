/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

// This is a generated file. Not intended for manual editing.
package taclanguage.autogenerated.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import taclanguage.psi.TACNamedElement;
import com.intellij.psi.PsiReference;

public interface TACJType extends TACNamedElement {

  @Nullable
  String getJavaType();

  String getName();

  @Nullable
  PsiElement getNameIdentifier();

  PsiReference[] getReferences();

}
