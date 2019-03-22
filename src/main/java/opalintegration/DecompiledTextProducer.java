package opalintegration;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.bi.AccessFlagsContexts;
import org.opalj.br.*;
import org.opalj.collection.RefIterator;
import org.opalj.collection.immutable.RefArray;
import scala.Option;
import scala.collection.Iterator;
import scala.collection.immutable.List;

/**
 * A class that is responsible for creating the skeleton of the decompiled text for both Java
 * bytecode (JBC) and three-address-code (TAC).
 *
 * <p>Both JBC and TAC can produce specific output by overriding the hooks and the template method
 * this class provides.
 */
abstract class DecompiledTextProducer {

  String decompiledText(ClassFile classFile) {
    String decompiledText = "";

    decompiledText += annotationsToJava(classFile.annotations(), "", "\n");
    decompiledText += classHeader(classFile);
    decompiledText += attributes(classFile);
    decompiledText += fields(classFile);
    decompiledText += methods(classFile); // contains the template method

    return decompiledText;
  }

  String attributes(ClassFile classFile) {
    return "";
  }

  /**
   * A hook which can be used to produce text that contains field information. It returns an empty
   * string by default.
   *
   * @param classFile The class file which contains the fields
   * @return Information about the fields as a String
   */
  String fields(ClassFile classFile) {
    return "";
  }

  /**
   * A template method which can be used to produce text that contains information about the method
   * bodies.
   *
   * @param method The method in question
   * @return A String that displays information about the method body
   */
  abstract String methodBody(Method method);

  /**
   * Creates text output for all methods. It delegates the creation of method bodies to its
   * subclasses via a call to a template method and handles everything else (i.e. the method head)
   * by itself.
   *
   * @param classFile The class file which contains the methods
   * @return A string of the decompiled methods
   */
  private String methods(ClassFile classFile) {
    StringBuilder methodText = new StringBuilder();
    RefIterator<Method> methods = classFile.methods().iterator();

    methodText.append(beginMethodArea());
    while (methods.hasNext()) {
      Method method = methods.next();
      if (method.methodTypeSignature().isDefined()) {
        // TODO "diamond operator"
        MethodTypeSignature methodTypeSignatureOption = method.methodTypeSignature().get();
        List<ThrowsSignature> throwsSignatureList = methodTypeSignatureOption.throwsSignature();
      }
      methodText.append(annotationsToJava(method.annotations(), "\n", "\n"));
      methodText
          .append(methodDescriptor(method))
          .append(thrownExceptions(method.exceptionTable()))
          .append(" { ");

      // call to template method
      methodText.append(methodBody(method));
    }
    methodText.append(endMethodArea());

    return methodText.toString();
  }

  /**
   * A hook that may be used to mark the beginning of a method region. It returns an empty String by
   * default.
   *
   * @return A String that marks the beginning of a method region
   */
  String beginMethodArea() {
    return "";
  }

  /**
   * A hook that may be used to mark the end of a method region. It returns an empty String by
   * default.
   *
   * @return A String that marks the end of a method region
   */
  String endMethodArea() {
    return "";
  }

  /**
   * @param classFile The class in question
   * @return A String that contains information about the class (e.g. annotations, class name, super
   *     types, Java version, etc.)
   */
  @NotNull
  private String classHeader(@NotNull ClassFile classFile) {
    StringBuilder classHeader = new StringBuilder();

    classHeader
        .append(AccessFlags.classFlagsToJava(classFile.accessFlags()))
        .append(" ")
        .append(classFile.fqn().replace("/", "."));
    if (classFile.superclassType().isDefined()) {
      classHeader.append(" extends ").append(classFile.superclassType().get().toJava());
    }
    if (classFile.interfaceTypes().length() > 0) {
      classHeader.append(" implements ");
      for (int j = 0; j < classFile.interfaceTypes().length(); j++) {
        classHeader.append(classFile.interfaceTypes().apply(j).toJava()).append(" ");
      }
    }
    classHeader
        .append("\n// Source File: ")
        .append(classFile.sourceFile().isDefined() ? classFile.sourceFile().get() : "")
        .append(" -- Version: (")
        .append(classFile.jdkVersion())
        .append(") -- Size: \n\n");

    return classHeader.toString();
  }
String methodDescriptor(Method method){
  MethodDescriptor descriptor = method.descriptor();
  StringBuilder methodDescriptorBuilder = new StringBuilder();
  String flag = AccessFlags.toString(method.accessFlags(), AccessFlagsContexts.METHOD())+" ";
  if(flag.length() > 0)
  methodDescriptorBuilder.append(flag);
  methodDescriptorBuilder.append(descriptor.returnType().toJava()).append(" ")
          .append(method.name()).append("(");
  FieldType[] parameters = new FieldType[descriptor.parametersCount()];
  descriptor.parameterTypes().copyToArray(parameters);
  RefArray<RefArray<Annotation>> invisibleParameterAnnotations = method.runtimeInvisibleParameterAnnotations();
  RefArray<RefArray<Annotation>> visibleParameterAnnotations = method.runtimeVisibleParameterAnnotations();
  for (int i = 0; i < parameters.length; i++) {
    if(i < invisibleParameterAnnotations.size() && invisibleParameterAnnotations.apply(i).size() > 0)
      methodDescriptorBuilder.append(annotationsToJava(invisibleParameterAnnotations.apply(i),""," "));
    if(i < visibleParameterAnnotations.size() && visibleParameterAnnotations.apply(i).size() > 0)
      methodDescriptorBuilder.append(annotationsToJava(visibleParameterAnnotations.apply(i),""," "));
    methodDescriptorBuilder.append(parameters[i].toJava()).append(",");
  }
  //delete last comma
  if(methodDescriptorBuilder.charAt(methodDescriptorBuilder.length()-1) == ',')
  methodDescriptorBuilder.deleteCharAt(methodDescriptorBuilder.length()-1);
  //methodDescriptorBuilder.append(Arrays.stream(parameters).map(p-> p.toJava()).collect(Collectors.joining(",")));
  methodDescriptorBuilder.append(")");
  return methodDescriptorBuilder.toString();
}
  // TODO: doesn't quite work yet (grammar, etc.)
  /** Converts a given list of annotations into a Java-like representation. */
  @NotNull
  private String annotationsToJava(@NotNull RefArray<Annotation> annotations, String before, String after) {
    if (!annotations.isEmpty()) {
      Annotation[] annotationsCopy = new Annotation[annotations.size()];
      annotations.copyToArray(annotationsCopy);
      String javaStyle =
          Arrays.stream(annotationsCopy).map(Annotation::toJava).collect(Collectors.joining("\n"));
      return before + javaStyle + after;
    } else {
      return "";
    }
  }

  // TODO: doesn't quite work yet (grammar, etc.)
  /** Converts a given list of attributes into a Java-like representation. */
  @NotNull
  String attributesToJava(@NotNull RefArray<Attribute> attributes, String before, String after) {
    if (!attributes.isEmpty()) {
      Attribute[] attributesCopy = new Attribute[attributes.size()];
      attributes.copyToArray(attributesCopy);
      String attributeString =
          Arrays.stream(attributesCopy).map(Attribute::toString).collect(Collectors.joining(" "));

      return before + attributeString + after;
    } else {
      return "";
    }
  }

  /**
   * E.g. if the method throws two exceptions, say IOException and RuntimeException, then
   * the output will be: throws java.io.IOException, java.lang.RuntimeException
   *
   * @param exceptionTable The table which contains the exceptions that the method throws
   * @return a string which contains a throws clause
   */
  @NotNull
  private String thrownExceptions(@NotNull Option<ExceptionTable> exceptionTable) {
    if (!exceptionTable.isDefined()) {
      return "";
    }
    StringBuilder throwStringBuilder = new StringBuilder();
    throwStringBuilder.append(" throws ");
    ObjectType[] exceptions = new ObjectType[exceptionTable.get().exceptions().size()];
    exceptionTable.get().exceptions().copyToArray(exceptions);
    throwStringBuilder.append(
            Arrays.stream(exceptions).map(ObjectType::toJava).collect(Collectors.joining(", ")));
    return throwStringBuilder.toString();
  }

}

