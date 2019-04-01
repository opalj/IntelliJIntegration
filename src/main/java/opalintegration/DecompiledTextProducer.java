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
    decompiledText += "\n}";              // end of class file

    return decompiledText;
  }

  /**
   * Creates the class header.
   *
   * @param classFile The class in question
   * @return A String that contains information about the class (e.g. annotations, class name, super
   *     types, interface types, Java version, etc.)
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
        // no comma at the end
        if(j == classFile.interfaceTypes().length() - 1) {
          classHeader.append(classFile.interfaceTypes().apply(j).toJava()).append(" ");
          break;
        }

        classHeader.append(classFile.interfaceTypes().apply(j).toJava()).append(", ");
      }
    }
    classHeader
        .append(" {")
        .append("\n// Source File: ")
        .append(classFile.sourceFile().isDefined() ? classFile.sourceFile().get() : "")
        .append(" -- Version: (")
        .append(classFile.jdkVersion())
        .append(")\n\n");

    return classHeader.toString();
  }

  /**
   * Creates the string representation of the attributes area of a class.
   * The attributes area contains the table of the inner classes.
   *
   * @param classFile The class file which contains the attributes
   * @return Information about the attributes as a String, contained within an area: Attributes { ... }
   */
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
   * Creates the text output for all the fields (including access flags and type) of a class
   *
   * @param classFile The class file which contains the fields
   * @return Information about the fields as a String, contained within an area: Fields { ... }
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
   * @return Information about the methods as a String, contained within an area: Methods { ... }
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

  /**
   * Creates the string representation of the method head (apart from exceptions), by linking together
   * all necessary pieces (e.g. modifiers, return type, parameter list, etc.)
   *
   * @param method The method for which the method head should be created
   * @return The method head as a string, e.g.: public static void someMethod(int, @NotNull java.lang.String)
   */
  @NotNull
  private String methodDescriptor(@NotNull Method method) {
    MethodDescriptor descriptor = method.descriptor();
    StringBuilder methodDescriptorBuilder = new StringBuilder();

    // access flags contain access modifiers such as public and static
    String flag = AccessFlags.toString(method.accessFlags(), AccessFlagsContexts.METHOD()) + " ";
    if (flag.length() > 0) methodDescriptorBuilder.append(flag);
    methodDescriptorBuilder
        .append(descriptor.returnType().toJava())
        .append(" ")
        .append(method.name())
        .append("(");

    // parameter list (including annotations for parameters)
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

    methodDescriptorBuilder.append(")");
    return methodDescriptorBuilder.toString();
  }

  /**
   * Marks the beginning of an area
   *
   * @param areaName The name of the area
   * @return The string of beginning of the area, e.g.: Methods {
   */
  @NotNull
  @Contract(pure = true)
  private String beginArea(String areaName) {
    return areaName + " {\n";
  }

  /**
   * Marks the end of an area
   *
   * @param areaName The name of the area
   * @return The string of the end of an area, e.g.: } // Methods
   */
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
    // if the line does not have escaping backslashes there is nothing to do
    if (!instructionLine.matches(".*\"(.|\n|\r|\t|\b)*\".*")) {
      return instructionLine;
    }

    // replaces a \ with a \\ -> needed because e.g. LDC("s\") would escape the last " and
    // thus break the syntax
    instructionLine = instructionLine.replaceAll("\\\\", "\\\\\\\\");

    // take care of every other occurrence of a \, e.g. in OPAL \" becomes ", but should stay \"
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
