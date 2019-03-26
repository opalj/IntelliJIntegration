package opalintegration;

import java.io.File;
import java.net.URL;
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

  private Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>>
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
      methodBody.append(ToTxt.apply(TacCode).mkString("\n"));
    }

    return methodBody.toString();
  }
}
