// This is a generated file. Not intended for manual editing.
package JavaByteCodeLanguage.psi.impl;


import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;

import static JavaByteCodeLanguage.psi.JavaByteCodeTypes.*;
import JavaByteCodeLanguage.psi.*;

public class JavaByteCodeTypeImpl extends JavaByteCodeNamedElementImpl implements JavaByteCodeType {

    public JavaByteCodeTypeImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull JavaByteCodeVisitor visitor) {
        visitor.visitType(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JavaByteCodeVisitor) accept((JavaByteCodeVisitor)visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getJavatype() {
        return findChildByType(JAVATYPE);
    }

    @Override
    @Nullable
    public PsiElement getPrimitivetype() {
        return findChildByType(PRIMITIVETYPE);
    }

    public String getKey() {
        return JavaByteCodePsiImplUtil.getKey(this);
    }

    public String getName() {
        return JavaByteCodePsiImplUtil.getName(this);
    }

    public PsiElement setName(String newName) {
        return JavaByteCodePsiImplUtil.setName(this, newName);
    }

    public PsiElement getNameIdentifier() {
        return JavaByteCodePsiImplUtil.getNameIdentifier(this);
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        JavaClassReferenceProvider provider = new JavaClassReferenceProvider();
        try {
            PsiElementFactory factory; // = PsiElementFactory.SERVICE.getInstance();
            System.out.println(getName());
            PsiMember ss;
            PsiMethod sss;
            return provider.getReferencesByString(getName(), this, 0);
        } catch(Exception e) {
            return super.getReferences();
        }
    }
}