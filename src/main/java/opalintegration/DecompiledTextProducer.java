package opalintegration;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.bi.AccessFlagsContexts;
import org.opalj.br.*;
import org.opalj.collection.RefIterator;
import org.opalj.collection.immutable.RefArray;

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

    decompiledText += Tables.annotationsToJava(classFile.annotations(), "", "\n");
    decompiledText += classHeader(classFile);
    decompiledText += attributes(classFile);
    decompiledText += fields(classFile);
    decompiledText += methods(classFile); // contains the template method

    return decompiledText;
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

  @NotNull
  private String attributes(@NotNull ClassFile classFile) {
    StringBuilder classAttributes = new StringBuilder();

    if (classFile.innerClasses().isEmpty() && classFile.bootstrapMethodTable().isEmpty()) {
      return "";
    }

    classAttributes.append(beginArea("Attributes"));
    classAttributes.append(Tables.innerClassTable(classFile));
    classAttributes.append(endArea("Attributes"));

    return classAttributes.toString();
  }

  /**
   * A hook which can be used to produce text that contains field information. It returns an empty
   * string by default.
   *
   * @param classFile The class file which contains the fields
   * @return Information about the fields as a String
   */
  @NotNull
  private String fields(@NotNull ClassFile classFile) {
    StringBuilder fieldsText = new StringBuilder();
    RefArray<Field> fields = classFile.fields();

    // don't show fields if there are none
    if (fields.length() == 0) {
      return "";
    }

    fieldsText.append(beginArea("Fields"));
    for (int j = 0; j < fields.length(); j++) {
      Field field = fields.apply(j);
      String accessFlags = AccessFlags.toString(field.accessFlags(), AccessFlagsContexts.FIELD());
      fieldsText
          .append("\t")
          .append(accessFlags)
          .append(accessFlags.isEmpty() ? "" : " ")
          .append(field.fieldType().toJava())
          .append(" ")
          .append(field.name())
          .append("\n");
    }
    fieldsText.append(endArea("Fields"));

    return fieldsText.toString();
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
  @NotNull
  private String methods(@NotNull ClassFile classFile) {
    StringBuilder methodText = new StringBuilder();
    RefIterator<Method> methods = classFile.methods().iterator();

    methodText.append(beginArea("Methods")).append("\n");
    while (methods.hasNext()) {
      Method method = methods.next();
      methodText.append(Tables.annotationsToJava(method.annotations(), "\n", "\n"));
      methodText
          .append(methodDescriptor(method))
          .append(Tables.thrownExceptions(method.exceptionTable()))
          .append(" { ");

      // call to template method
      methodText.append(methodBody(method));
      methodText.append("}").append("\n\n\n");
    }
    methodText.append(endArea("Methods"));

    return methodText.toString();
  }

  @NotNull
  private String methodDescriptor(@NotNull Method method) {
    MethodDescriptor descriptor = method.descriptor();
    StringBuilder methodDescriptorBuilder = new StringBuilder();
    String flag = AccessFlags.toString(method.accessFlags(), AccessFlagsContexts.METHOD()) + " ";
    if (flag.length() > 0) methodDescriptorBuilder.append(flag);
    methodDescriptorBuilder
        .append(descriptor.returnType().toJava())
        .append(" ")
        .append(method.name())
        .append("(");
    FieldType[] parameters = new FieldType[descriptor.parametersCount()];
    descriptor.parameterTypes().copyToArray(parameters);
    RefArray<RefArray<Annotation>> invisibleParameterAnnotations =
        method.runtimeInvisibleParameterAnnotations();
    RefArray<RefArray<Annotation>> visibleParameterAnnotations =
        method.runtimeVisibleParameterAnnotations();
    for (int i = 0; i < parameters.length; i++) {
      if (i < invisibleParameterAnnotations.size()
          && invisibleParameterAnnotations.apply(i).size() > 0)
        methodDescriptorBuilder.append(
            Tables.annotationsToJava(invisibleParameterAnnotations.apply(i), "", " "));
      if (i < visibleParameterAnnotations.size() && visibleParameterAnnotations.apply(i).size() > 0)
        methodDescriptorBuilder.append(
            Tables.annotationsToJava(visibleParameterAnnotations.apply(i), "", " "));
      methodDescriptorBuilder.append(parameters[i].toJava()).append(",");
    }
    // delete last comma
    if (methodDescriptorBuilder.charAt(methodDescriptorBuilder.length() - 1) == ',')
      methodDescriptorBuilder.deleteCharAt(methodDescriptorBuilder.length() - 1);
    // methodDescriptorBuilder.append(Arrays.stream(parameters).map(p->
    // p.toJava()).collect(Collectors.joining(",")));
    methodDescriptorBuilder.append(")");
    return methodDescriptorBuilder.toString();
  }

  @NotNull
  @Contract(pure = true)
  private String beginArea(String areaName) {
    return areaName + " {\n";
  }

  @NotNull
  @Contract(pure = true)
  private String endArea(String areaName) {
    return "} //" + areaName + "\n\n\n";
  }

  /**
   * This is a temporary method that is used to fix a bug that comes from OPAL where an escaping
   * backslash will be removed, e.g. '(id=\")' becomes '(id="|)' which causes errors, so we add an
   * escape \ in front of every \ -> '(id=\\")' becomes '(id=\"|)'
   *
   * @param instructionLine The current line to fix
   * @return The line with fixed escape characters, if contained any
   */
  String opalStringBugFixer(String instructionLine) {
    if (!instructionLine.matches(".*\"(.|\n|\r|\t|\b)*\".*")) {
      return instructionLine;
    }

    // replaces a \ with a \\ -> needed because e.g. LDC("s\") would escape the last " and
    // thus break the syntax
    instructionLine = instructionLine.replaceAll("\\\\", "\\\\\\\\");

    int firstDoubleQuote = instructionLine.indexOf('"');
    int lastDoubleQuote = instructionLine.lastIndexOf('"');
    String stringContent = instructionLine.substring(firstDoubleQuote + 1, lastDoubleQuote);
    stringContent = stringContent.replace("\"", "\\\"");
    stringContent = stringContent.replace("\n", "\\n");
    stringContent = stringContent.replace("\t", "\\t");
    stringContent = stringContent.replace("\r", "\\r");
    stringContent = stringContent.replace("\b", "\\b");
    instructionLine =
        instructionLine.substring(0, firstDoubleQuote + 1)
            + stringContent
            + instructionLine.substring(lastDoubleQuote);

    return instructionLine;
  }
}
