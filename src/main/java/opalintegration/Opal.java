package opalintegration;

import org.opalj.ai.ValuesDomain;
import org.opalj.br.ClassFile;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.collection.immutable.ConstArray;
import org.opalj.collection.immutable.RefArray;
import org.opalj.tac.*;
import org.opalj.value.KnownTypedValue;
import scala.Function1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class Opal {
    private static void Test(String filepath){
        Project<URL> uriProject = Project.apply(new File(filepath));
        JavaProject javaProject = new JavaProject(uriProject);
        javaProject.project().parForeachMethodWithBody(null, 16,
                (mi) -> {
                    TACode<TACMethodParameter, DUVar<ValuesDomain.Value>> taCode = TACAI.apply(javaProject.project(), mi.method(), null);
                    String taCodeAsString = ToTxt.apply(taCode).mkString("\n");
                    String Name = mi.classFile().thisType().toJava() + mi.method().name();
                    System.out.println("test");
                    try {
                        FileOutputStream outputStream = new FileOutputStream(Name);
                        byte[] strToBytes = taCodeAsString.getBytes();
                        outputStream.write(strToBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                    return 1;
                });
    }
    public void ThreeWayDisAssemblerOutput(String filepath) throws IOException {
        Project<URL> uriProject = Project.apply(new File(filepath));
        JavaProject javaProject = new JavaProject(uriProject);
        ConstArray<ClassFile> classFileConstArray = javaProject.project().allProjectClassFiles();
        Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>> methodTACodeFunction1 = javaProject.project().get(DefaultTACAIKey$.MODULE$);
        String dir = "./out/";
        for (int i = 0; i < classFileConstArray.length(); i++) {
            ClassFile classFile = classFileConstArray.apply(i);
            String Name = dir + classFile.fqn() + ".class.tac";

            FileOutputStream outputStream = new FileOutputStream(Name);

            RefArray<Method> methods = classFile.methods();
            for (int j = 0; j < methods.length(); j++) {
                Method method = methods.apply(j);
                if (method.body().isDefined()) {
                    System.out.println(method.toJava());
                    TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode = methodTACodeFunction1.apply(method);
                    String tacCodeString = ToTxt.apply(TacCode).mkString("\n");
                    //System.out.println(ToTxt.apply(TacCode).mkString("\n"));
                    method.toJava(ToTxt.apply(TacCode).mkString("\n"));
                    byte[] strToBytes = tacCodeString.getBytes();
                    outputStream.write(strToBytes);
                }
            }
        }
    }
        public String ThreeWayDisAssembler(String filepath) {
            String tacCodeString = "";
            Project<URL> uriProject = Project.apply(new File(filepath));
            JavaProject javaProject = new JavaProject(uriProject);
            ConstArray<ClassFile> classFileConstArray = javaProject.project().allProjectClassFiles();
            Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>> methodTACodeFunction = javaProject.project().get(DefaultTACAIKey$.MODULE$);
            for(int i = 0 ; i < classFileConstArray.length(); i++) {
                ClassFile classFile = classFileConstArray.apply(i);
                RefArray<Method> methods = classFile.methods();
                for(int j = 0 ; j < methods.length(); j++) {
                    Method method = methods.apply(j);
                    if(method.body().isDefined()) {
                        System.out.println(method.toJava());
                        TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode = methodTACodeFunction.apply(method);
                        tacCodeString = tacCodeString + ToTxt.apply(TacCode).mkString("\n");
                    }
                }
            }
            return tacCodeString;
    }
}
