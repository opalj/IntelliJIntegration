package opalintegration;

import org.opalj.ai.ValuesDomain;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.collection.immutable.ConstArray;
import org.opalj.collection.immutable.RefArray;
import org.opalj.da.ClassFileReader;
import org.opalj.tac.*;
import org.opalj.value.KnownTypedValue;
import scala.Function1;
import scala.Some;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Opal {
    private Project<URL> uriProject;
    private JavaProject javaProject;
    Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>> methodTACodeFunction;

    private static void Test(String filepath) {
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
    @Deprecated
    public void ThreeWayDisAssemblerOutput(String filepath) throws IOException {
        uriProject = Project.apply(new File(filepath));
        JavaProject javaProject = new JavaProject(uriProject);
        ConstArray<org.opalj.br.ClassFile> classFileConstArray = javaProject.project().allProjectClassFiles();
        Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>> methodTACodeFunction1 = javaProject.project().get(DefaultTACAIKey$.MODULE$);
        String dir = "./out/";
        for (int i = 0; i < classFileConstArray.length(); i++) {
            org.opalj.br.ClassFile classFile = classFileConstArray.apply(i);
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

    public String ThreeWayDisAssemblerString(String filepath) {
        String tacCodeString = "";
        uriProject = Project.apply(new File(filepath));
        javaProject = new JavaProject(uriProject);
        ConstArray<org.opalj.br.ClassFile> classFileConstArray = javaProject.project().allProjectClassFiles();
        methodTACodeFunction = javaProject.project().get(DefaultTACAIKey$.MODULE$);
        for (int i = 0; i < classFileConstArray.length(); i++) {
            org.opalj.br.ClassFile classFile = classFileConstArray.apply(i);
            RefArray<Method> methods = classFile.methods();
//            TODO     classFile.fields();
//            TODO     classFile.attributes();
            for (int j = 0; j < methods.length(); j++) {
                Method method = methods.apply(j);
                if (method.body().isDefined()) {
                    System.out.println(method.toJava());
                    TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode = methodTACodeFunction.apply(method);
                    tacCodeString = tacCodeString + ToTxt.apply(TacCode).mkString("\n");
                }
            }
        }
        return tacCodeString;
    }
    public String toHTMLForm(String classPath) throws IOException{
        Path path = Paths.get(classPath);
        File file = path.toFile();

//TODO        scala.collection.immutable.List<Object> classFileList;

                FileInputStream fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);

            // ...
            // in this representation (?), the returned list always contains
            // a single class file object
            org.opalj.da.ClassFile cf = (org.opalj.da.ClassFile) ClassFileReader.ClassFile(dis).head();
            String toHtmlAsString = "<style>" + cf.TheCSS() + "</style>" + cf.classFileToXHTML(new Some(classPath)).toString();

        return toHtmlAsString;
    }

}
