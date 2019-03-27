package opalintegration;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.tac.*;
import org.opalj.value.KnownTypedValue;
import scala.Function1;

/**
 * Is responsible for creating and providing the three-address-code (TAC) representation of a class
 * file
 */
class TacProducer extends DecompiledTextProducer {

  private final Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>>
      methodTACodeFunction;

  TacProducer(String filepath) {
    Project<URL> uriProject = Project.apply(new File(filepath));
    JavaProject javaProject = new JavaProject(uriProject);
    methodTACodeFunction = javaProject.project().get(DefaultTACAIKey$.MODULE$);
  }

  @Override
  String methodBody(Method method) {
    StringBuilder methodBody = new StringBuilder();

    if (method.body().isDefined()) {
      methodBody.append("\n");
      TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode =
          methodTACodeFunction.apply(method);
      // Opal String error
      Scanner scanner = new Scanner(ToTxt.apply(TacCode).mkString("\n"));
      while(scanner.hasNextLine()){
        String line = scanner.nextLine();
        if(line.matches(".*\".*\".*")) {
          line = line.replaceAll("\\\\","\\\\\\\\");
          System.out.println(line);
          int firstDoubleQuote = line.indexOf('"');
          int lastDoubleQuote = line.lastIndexOf('"');
          String stringContent = line.substring(firstDoubleQuote + 1, lastDoubleQuote);
          stringContent = stringContent.replace("\"", "\\\"");
          line = line.substring(0, firstDoubleQuote + 1)
                  + stringContent
                  + line.substring(lastDoubleQuote);
        }
        methodBody.append(line).append("\n");
      }
      //methodBody.append(ToTxt.apply(TacCode).mkString("\n"));
      methodBody.append("\n");
    }

    return methodBody.toString();
  }
}
