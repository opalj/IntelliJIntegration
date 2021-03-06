/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.JavaByteCodeLanguage.psi.impl;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.opalj.intellijintegration.JavaByteCodeLanguage.autoGenerated.psi.*;

/**
 * A utility class that serves as a delegate for our PSI-elements that are automatically generated
 * by the parser.
 *
 * <p>(In the grammar, see JavaByteCode.bnf file, we can define methods for selected tree elements.
 * Since they are generated by the parser, we delegate the implementations to this utility class, so
 * that the definitions aren't lost when re-generating the code.)
 */
public class JavaByteCodePsiImplUtil {

  // =============================
  // =========DefMethodName=======
  // =============================

  /**
   * @see PsiElement#getReferences() for details
   * @param element an invoked method in our JavaByteCode-Editor
   * @return the Java-references to this method
   */
  @NotNull
  public static PsiReference[] getReferences(JavaByteCodeDefMethodName element) {
    return DefMethodNameImplUtil.getReferences(element);
  }

  public static String getName(JavaByteCodeDefMethodName element) {
    return DefMethodNameImplUtil.getName(element);
  }

  /**
   * @param element an invoked method, e.g. println in java.io.PrintStream { void println() }
   * @return the name of the method as a String (e.g. "println")
   */
  @Nullable
  public static String getStringVar(@NotNull JavaByteCodeDefMethodName element) {
    return DefMethodNameImplUtil.getStringVar(element);
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  @Nullable
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeDefMethodName element) {
    return DefMethodNameImplUtil.getNameIdentifier(element);
  }

  // =============================
  // =============JType===========
  // =============================

  /**
   * @see PsiElement#getReferences() for details
   * @param element a class type (as FQN, e.g. java.lang.String)
   * @return the references to each element in the FQN (e.g. ["java", "lang", "String"])
   */
  public static PsiReference[] getReferences(JavaByteCodeJType element) {
    return JTypeImplUtil.getReferences(element);
  }

  public static String getName(JavaByteCodeJType element) {
    return JTypeImplUtil.getName(element);
  }

  /**
   * @param element a Java type, i.e. a class type (e.g. java.lang.String)
   * @return the String representation of the type
   */
  @Nullable
  public static String getJavaType(@NotNull JavaByteCodeJType element) {
    return JTypeImplUtil.getJavaType(element);
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  @Nullable
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeJType element) {
    return JTypeImplUtil.getNameIdentifier(element);
  }

  // =============================
  // ======MethodDeclaration======
  // =============================

  public static String getName(JavaByteCodeMethodDeclaration element) {
    return MethodDeclarationImplUtil.getName(element);
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeMethodDeclaration element) {
    return MethodDeclarationImplUtil.getNameIdentifier(element);
  }

  public static ItemPresentation getPresentation(JavaByteCodeMethodDeclaration element) {
    return MethodDeclarationImplUtil.getPresentation(element);
  }

  public static void navigate(JavaByteCodeMethodDeclaration element, boolean requestFocus) {
    MethodDeclarationImplUtil.navigate(element, requestFocus);
  }

  // =============================
  // ======FieldsDeclaration======
  // =============================

  public static String getName(JavaByteCodeFieldsDeclaration element) {
    return FieldsDeclarationImplUtil.getName(element);
  }

  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeFieldsDeclaration element) {
    return FieldsDeclarationImplUtil.getNameIdentifier(element);
  }

  public static ItemPresentation getPresentation(JavaByteCodeFieldsDeclaration element) {
    return FieldsDeclarationImplUtil.getPresentation(element);
  }

  public static void navigate(JavaByteCodeFieldsDeclaration element, boolean requestFocus) {
    FieldsDeclarationImplUtil.navigate(element, requestFocus);
  }

  // =============================
  // ==ExceptionTableDeclaration==
  // =============================

  public static String getName(JavaByteCodeExceptionTableDeclaration element) {
    return ExceptionTableDeclarationImplUtil.getName(element);
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(
      @NotNull JavaByteCodeExceptionTableDeclaration element) {
    return ExceptionTableDeclarationImplUtil.getNameIdentifier(element);
  }

  public static ItemPresentation getPresentation(JavaByteCodeExceptionTableDeclaration element) {
    return ExceptionTableDeclarationImplUtil.getPresentation(element);
  }

  public static void navigate(JavaByteCodeExceptionTableDeclaration element, boolean requestFocus) {
    ExceptionTableDeclarationImplUtil.navigate(element, requestFocus);
  }

  // =============================
  // ===LocVarTableDeclaration====
  // =============================

  public static String getName(JavaByteCodeLocVarTableDeclaration element) {
    return LocVarTableDeclarationImplUtil.getName(element);
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(@NotNull JavaByteCodeLocVarTableDeclaration element) {
    return LocVarTableDeclarationImplUtil.getNameIdentifier(element);
  }

  public static ItemPresentation getPresentation(JavaByteCodeLocVarTableDeclaration element) {
    return LocVarTableDeclarationImplUtil.getPresentation(element);
  }

  public static void navigate(JavaByteCodeLocVarTableDeclaration element, boolean requestFocus) {
    LocVarTableDeclarationImplUtil.navigate(element, requestFocus);
  }

  // =============================
  // ==StackMapTableDeclaration===
  // =============================

  public static String getName(JavaByteCodeStackMapTableDeclaration element) {
    return StackMapTableDeclarationImplUtil.getName(element);
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(
      @NotNull JavaByteCodeStackMapTableDeclaration element) {
    return StackMapTableDeclarationImplUtil.getNameIdentifier(element);
  }

  public static ItemPresentation getPresentation(JavaByteCodeStackMapTableDeclaration element) {
    return StackMapTableDeclarationImplUtil.getPresentation(element);
  }

  public static void navigate(JavaByteCodeStackMapTableDeclaration element, boolean requestFocus) {
    StackMapTableDeclarationImplUtil.navigate(element, requestFocus);
  }

  // =============================
  // =LocVarTypeTableDeclaration==
  // =============================

  public static String getName(JavaByteCodeLocVarTypeTableDeclaration element) {
    return LocVarTypeTableDeclarationImplUtil.getName(element);
  }

  /** @see PsiNameIdentifierOwner#getNameIdentifier() */
  public static PsiElement getNameIdentifier(
      @NotNull JavaByteCodeLocVarTypeTableDeclaration element) {
    return LocVarTypeTableDeclarationImplUtil.getNameIdentifier(element);
  }

  public static ItemPresentation getPresentation(JavaByteCodeLocVarTypeTableDeclaration element) {
    return LocVarTypeTableDeclarationImplUtil.getPresentation(element);
  }

  public static void navigate(
      JavaByteCodeLocVarTypeTableDeclaration element, boolean requestFocus) {
    LocVarTypeTableDeclarationImplUtil.navigate(element, requestFocus);
  }
}
