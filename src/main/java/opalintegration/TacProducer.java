/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package opalintegration;

import java.io.File;
import java.net.URL;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.tac.*;
import org.opalj.value.ValueInformation;
import scala.Function1;

/**
 * Is responsible for creating and providing the three-address-code (TAC) representation of a class
 * file
 */
class TacProducer extends DecompiledTextProducer {

  private final Function1<Method, AITACode<TACMethodParameter, ValueInformation>>
      methodTACodeFunction;

  TacProducer(String filepath) {
    Project<URL> uriProject = Project.apply(new File(filepath));
    JavaProject javaProject = new JavaProject(uriProject);
    methodTACodeFunction = javaProject.project().get(LazyDetachedTACAIKey$.MODULE$);
  }

  @Override
  String methodBody(Method method) {
    StringBuilder methodBody = new StringBuilder();

    if (method.body().isDefined()) {
      methodBody.append("\n");
      TACode<TACMethodParameter, DUVar<ValueInformation>> TacCode =
          methodTACodeFunction.apply(method);

      String[] body = ToTxt.apply(TacCode).mkString("#newline#").split("#newline#");
      for (String line : body) {
        line = opalStringBugFixer(line);
        methodBody.append(line).append("\n");
      }

      // methodBody.append(ToTxt.apply(TacCode).mkString("\n"));
      methodBody.append("\n");
    }

    return methodBody.toString();
  }
}
