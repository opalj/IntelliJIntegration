package opalintegration;

import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import java.io.File;
import java.net.URL;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.br.ClassFile;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.collection.immutable.RefArray;
import org.opalj.tac.*;
import org.opalj.value.KnownTypedValue;
import scala.Function1;

/**
 * Is responsible for creating and providing the three-address-code (TAC) representation of a class
 * file
 */
public class TacProducer {

  /**
   * @param project the project the file belongs to
   * @param virtualFile the class file of which we want the TAC representation
   * @return the file containing the TAC representation of the passed in class file
   */
  public static VirtualFile prepareTAC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    return OpalUtil.prepare(project, GlobalData.DISASSEMBLED_FILE_ENDING_TAC, virtualFile, null);
  }

  /**
   * @param classFile the (OPAL!) class file of which we want the TAC representation
   * @param filepath the path of the underlying virtual class file (needed to create the uri
   *     project)
   * @return the three-address-code of the class file
   */
  @NotNull
  static String createTacString(@NotNull ClassFile classFile, String filepath) {
    StringBuilder tacCodeString = new StringBuilder();
    Project<URL> uriProject = Project.apply(new File(filepath));
    JavaProject javaProject = new JavaProject(uriProject);
    Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>> methodTACodeFunction =
        javaProject.project().get(DefaultTACAIKey$.MODULE$);

    tacCodeString.append(createClassHeader(classFile));
    tacCodeString.append("\n");

    // iterate through the methods and generate the TAC for each
    RefArray<Method> methods = classFile.methods();
    for (int j = 0; j < methods.length(); j++) {
      Method method = methods.apply(j);
      if (method.body().isDefined()) {
        tacCodeString.append(method.toString()).append(" {\n");
        TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode =
            methodTACodeFunction.apply(method);
        tacCodeString.append(ToTxt.apply(TacCode).mkString("\n"));
        tacCodeString.append("\n}");
        tacCodeString.append("\n\n\n");
      }
    }

    return tacCodeString.toString();
  }

  private static String createClassHeader(ClassFile classFile) {
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
        .append(") -- size: \n");

    return classHeader.toString();
  }
}
