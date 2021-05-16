/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.opalintegration;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import org.opalj.ai.domain.l0.PrimitiveTACAIDomain;
import org.opalj.ai.domain.l1.DefaultDomainWithCFGAndDefUse;
import org.opalj.ai.domain.l2.DefaultPerformInvocationsDomainWithCFGAndDefUse;
import org.opalj.ai.fpcf.properties.AIDomainFactoryKey$;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.intellijintegration.config.BytecodeConfig;
import org.opalj.tac.*;
import org.opalj.value.ValueInformation;
import scala.Function1;
import scala.collection.JavaConverters;
import scala.collection.mutable.Set;

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
    switch (BytecodeConfig.getInstance().getTacKey()) {
      case ONE:
        domain = PrimitiveTACAIDomain.class;
        // PrimitiveTACAIDomain primitiveTACAIDomain = new PrimitiveTACAIDomain(uriProject, );
        break;
      case TWO:
        domain = DefaultDomainWithCFGAndDefUse.class;
        // <URL> urlDefaultDomainWithCFGAndDefUse = new DefaultDomainWithCFGAndDefUse<>(uriProject,
        // );
        break;
      case THREE:
        domain =
            DefaultPerformInvocationsDomainWithCFGAndDefUse
                .class; // <URL> urlDefaultPerformInvocationsDomainWithCFGAndDefUse = new
        // DefaultPerformInvocationsDomainWithCFGAndDefUse<>(uriProject, );
        break;
      default:
        domain = PrimitiveTACAIDomain.class;
        break;
    }
    HashSet<Class<?>> classes = new HashSet<>();
    classes.add(domain);
    Set<Class<?>> setAsScala = JavaConverters.<Class<?>>asScalaSet(classes);
    uriProject.updateProjectInformationKeyInitializationData(
        AIDomainFactoryKey$.MODULE$, (x) -> setAsScala.<Class<?>>toSet());
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
