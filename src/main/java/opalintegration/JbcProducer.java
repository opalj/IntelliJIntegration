package opalintegration;

import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.bi.AccessFlagsContexts;
import org.opalj.br.*;
import org.opalj.br.instructions.Instruction;
import org.opalj.collection.IntIterator;
import org.opalj.collection.RefIterator;
import org.opalj.collection.immutable.RefArray;

/** Is responsible for creating and providing the bytecode representation of a class file */
public class JbcProducer {

  /**
   * @param project the project the file belongs to
   * @param virtualFile the class file of which we want the bytecode representation
   * @return the file containing the bytecode representation of the passed in class file
   */
  public static VirtualFile prepareJBC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    return OpalUtil.prepare(project, GlobalData.DISASSEMBLED_FILE_ENDING_JBC, virtualFile, null);
  }

  /**
   * create the bytecode representation (of OPAL) for a given class file
   *
   * @param classFile ...
   * @return the bytecode representation as a String
   */
  static String createBytecodeString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    byteCodeString
        .append(AccessFlags.classFlagsToJava(classFile.accessFlags()))
        .append(" ")
        .append(classFile.fqn().replace("/", "."));
    //    ByteCodeString.append(AccessFlags.toString(classFile.accessFlags(),
    // AccessFlagsContexts.CLASS())).append( " ").append(classFile.fqn().replace("/", "."));
    if (classFile.superclassType().isDefined()) {
      byteCodeString.append(" extends ").append(classFile.superclassType().get().toJava());
    }
    if (classFile.interfaceTypes().length() > 0) {
      byteCodeString.append(" implements ");
      for (int j = 0; j < classFile.interfaceTypes().length(); j++) {
        byteCodeString.append(classFile.interfaceTypes().apply(j).toJava()).append(" ");
      }
    }
    byteCodeString
        .append("\n// Source File: ")
        .append(classFile.sourceFile().isDefined() ? classFile.sourceFile().get() : "")
        .append(" -- Version: (")
        .append(classFile.jdkVersion())
        .append(") -- Size: \n");
    // TODO Constant Pool Maybe working with DA.
    byteCodeString.append(byteCodeFieldToString(classFile));
    RefArray<Annotation> annotations = classFile.annotations();
    for (int i = 0; i < annotations.length(); i++) {
      Annotation annotation = annotations.apply(i);
      byteCodeString.append("//").append(annotation.toJava()).append("\n");
    }
    byteCodeString.append(byteCodeMethodsToString(classFile));

    return byteCodeString.toString();
  }

  /**
   * creates a bytecode representation of the implemented methods of the passed classfile
   *
   * @param classFile ...
   * @return the method's bytecode representation as a String
   */
  private static String byteCodeMethodsToString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    StringVisitor stringVisitor = new StringVisitor();
    // RefArray<Method> methods = classFile.methods();
    RefIterator<Method> methods = classFile.methodsWithBody();
    byteCodeString.append("Methods\n\n");
    // for (int j = 0; j < methods.length(); j++) {
    // Method method = methods.apply(j);
    while (methods.hasNext()) {
      Method method = methods.next();
      if (method.body().isDefined()) {
        Code body = method.body().get();
        Instruction[] instructions = body.instructions();
        byteCodeString
            .append(method.toString().replaceFirst("\\).*", ")"))
            .append(" { ")
            .append("// [size :")
            .append(body.codeSize())
            .append(" bytes, max Stack: ")
            .append(body.maxStack())
            .append("] \n");
        byteCodeString.append("\tPC\tLine\tInstruction\n");
        for (int k = 0; k < instructions.length; k++) {
          if (instructions[k] != null) {
            try {
              String instruction;
              instruction = stringVisitor.accept(instructions[k]);
              // }else(instructions[k] instanceof INVOKEVIRTUAL)
              // TODO replace \n (and \t...??) and the likes with spaces
              instruction = instruction.replaceAll("\n", " ");
              instruction = instruction.replaceAll("\t", " ");

              byteCodeString
                  .append("\t")
                  .append(k)
                  .append("\t")
                  .append(body.lineNumber(k).get().toString())
                  .append("\t\t")
                  .append(instruction)
                  .append("\n");
            } catch (NoSuchElementException e) {
              return "Issue with Java 5? \n\n " + e.getMessage();
            }
          }
        }
        if (body.localVariableTable().isDefined()) {
          RefArray<LocalVariable> refArrayOption = body.localVariableTable().get();
          byteCodeString
              .append("\n\nLocalVariableTable // [size: ")
              .append(refArrayOption.length())
              .append(" item(s)]\n");
          for (int k = 0; k < refArrayOption.length(); k++) {
            LocalVariable localVariable = refArrayOption.apply(k);
            byteCodeString
                .append("[")
                .append(localVariable.startPC())
                .append(" > ")
                .append(localVariable.length())
                .append(") => ")
                .append(localVariable.fieldType().toJava())
                .append(" ")
                .append(localVariable.name())
                .append("\n");
          }
        }
        // method.attributes
        //        RefArray<Attribute> attributes = method.attributes();
        //        for (int i = 0; i < attributes.length(); i++) {
        //          Attribute attribute = attributes.apply(i);
        //          byteCodeString.append(
        //                  // attribute.toString() + " " +
        // attribute.getClass().getAnnotations().length + "\n");
        //        }
        // method.attributes
        // method.exexceptionTable()
        //        if (method.exceptionTable().isDefined()) {
        //          ExceptionTable exceptionTable = method.exceptionTable().get();
        //          byteCodeString.append("ExceptionTable\n");
        //          RefIterator<ObjectType> exceptions = exceptionTable.exceptions().iterator();
        //          while (exceptions.hasNext()) {
        //            ObjectType exception = exceptions.next();
        //
        // byteCodeString.append(exception.id()).append("\t").append(exception.toJava()).append("\n");
        //          }
        //        }
        // end method.exceptionTable
        //        if (method.runtimeVisibleAnnotations().iterator().hasNext()) {
        //          byteCodeString.append("RuntimeVisibleAnnotations\n");
        //          RefIterator<Annotation> annotationRefIterator =
        // method.runtimeVisibleAnnotations().iterator();
        //          while (annotationRefIterator.hasNext()) {
        //            byteCodeString.append(annotationRefIterator.next().toJava());
        //          }
        //        }
        //        if (method.runtimeInvisibleAnnotations().iterator().hasNext()) {
        //          byteCodeString.append("RuntimeInvisibleAnnotations\n");
        //          RefIterator<Annotation> annotationRefIterator =
        // method.runtimeInvisibleAnnotations().iterator();
        //          while (annotationRefIterator.hasNext()) {
        //            byteCodeString.append(annotationRefIterator.next().toJava());
        //          }
        //        }
        if (body.stackMapTable().isDefined()) {
          StackMapTable stackMapTable = body.stackMapTable().get();
          RefArray<StackMapFrame> stackMapFrameRefArray = stackMapTable.stackMapFrames();
          byteCodeString
              .append("StackMapTable")
              .append("\n")
              .append("//PC\tName\tframeType\toffsetDelta\n");
          IntIterator pcIterator = stackMapTable.pcs().iterator();
          for (int i = 0; i < stackMapFrameRefArray.length(); i++) {
            StackMapFrame stackMapFrame = stackMapFrameRefArray.apply(i);
            Class<? extends StackMapFrame> frameClass = stackMapFrame.getClass();
            int pc = pcIterator.next();
            byteCodeString
                .append(pc)
                .append(" ")
                .append(frameClass.getName())
                .append(" ")
                .append(stackMapFrame.frameType())
                .append(" ")
                .append(stackMapFrame.offset(0) - 1)
                .append("\n");
          }
        }
      }
      byteCodeString.append("}").append("\n\n\n");
    }
    return byteCodeString.toString();
  }

  /**
   * creates a bytecode representation of the implemented fields of the passed classfile
   *
   * @param classFile ...
   * @return the fields' bytecode representation as a String
   */
  private static String byteCodeFieldToString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    RefArray<Field> fields = classFile.fields();
    byteCodeString.append("Fields\n");
    for (int j = 0; j < fields.length(); j++) {
      Field field = fields.apply(j);
      // TODO: if access flags empty don't insert empty space before fieldType()
      byteCodeString
          .append(AccessFlags.toString(field.accessFlags(), AccessFlagsContexts.FIELD()))
          .append(" ")
          .append(field.fieldType().toJava())
          .append(" ")
          .append(field.name())
          .append("\n");
    }
    byteCodeString.append("\n\n\n");
    return byteCodeString.toString();
  }
}
