/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package opalintegration;

import java.io.File;
import java.net.URL;

import config.BytecodeConfig;
import org.opalj.ai.domain.l0.PrimitiveTACAIDomain;
import org.opalj.ai.domain.l1.DefaultDomainWithCFGAndDefUse;
import org.opalj.ai.domain.l2.DefaultPerformInvocationsDomainWithCFGAndDefUse;
import org.opalj.ai.fpcf.properties.AIDomainFactoryKey$;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.tac.*;
import org.opalj.value.ValueInformation;
import scala.Function1;
import scala.collection.Seq$;
import scala.collection.Set;
import scala.collection.Set$;

/**
 * Is responsible for creating and providing the three-address-code (TAC) representation of a class
 * file
 */
class TacProducer extends DecompiledTextProducer {

  private final Function1<Method, AITACode<TACMethodParameter, ValueInformation>>
      methodTACodeFunction;

  TacProducer(String filepath) {
    Project<URL> uriProject = Project.apply(new File(filepath));
    Class<?> domain;
    switch(BytecodeConfig.getInstance().getTacKey()){
      case ONE:
        domain = PrimitiveTACAIDomain.class;
        //PrimitiveTACAIDomain primitiveTACAIDomain = new PrimitiveTACAIDomain(uriProject, );
        break;
      case TWO:
        domain = DefaultDomainWithCFGAndDefUse.class;
        // <URL> urlDefaultDomainWithCFGAndDefUse = new DefaultDomainWithCFGAndDefUse<>(uriProject, );
        break;
     default:
        domain = DefaultPerformInvocationsDomainWithCFGAndDefUse.class;//<URL> urlDefaultPerformInvocationsDomainWithCFGAndDefUse = new DefaultPerformInvocationsDomainWithCFGAndDefUse<>(uriProject, );
        break;
    }
    uriProject.updateProjectInformationKeyInitializationData(AIDomainFactoryKey$.MODULE$,(x) -> {
      if (x.isDefined()) {
        Set<?> objectSet = Set$.MODULE$.empty().$plus(domain);

        return ;
      } else {
        return x.get().$plus(domain);
      }
    });
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
