package opalintegration;

import Compile.Compiler;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import org.jetbrains.annotations.NotNull;
import org.opalj.ai.ValuesDomain;
import org.opalj.br.Method;
import org.opalj.br.analyses.JavaProject;
import org.opalj.br.analyses.Project;
import org.opalj.collection.immutable.ConstArray;
import org.opalj.collection.immutable.RefArray;
import org.opalj.da.ClassFileReader;
import org.opalj.tac.*;
import org.opalj.value.KnownTypedValue;
import saveFile.SaveFile;
import saveFile.exceptions.ErrorWritingFileException;
import saveFile.exceptions.InputNullException;
import saveFile.exceptions.IsNotAFileException;
import saveFile.exceptions.NotEnoughRightsException;
import scala.Function1;
import scala.Some;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Opal {
    // uriProject wird benötigt um die OpalFrameworks mit dem Project zu verbinden.
    private static Project<URL> uriProject;
    // noch nicht wichtig könnted
    private static JavaProject javaProject;
    static Function1<Method, TACode<TACMethodParameter, DUVar<KnownTypedValue>>> methodTACodeFunction;
    // INTELIJ VARS
    private static com.intellij.openapi.project.Project project;
    private static String classPath;

    private static void Test(String filepath) {
        Project<URL> uriProject = Project.apply(new File(filepath));
        JavaProject javaProject = new JavaProject(uriProject);
        javaProject
                .project()
                .parForeachMethodWithBody(
                        null,
                        16,
                        (mi) -> {
                            TACode<TACMethodParameter, DUVar<ValuesDomain.Value>> taCode =
                                    TACAI.apply(javaProject.project(), mi.method(), null);
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

    public static String JavaClasstoTACForm(VirtualFile virtualClassFile) {
        classPath = virtualClassFile.getPath();
        String JavaTACClassString = threeWayDisassemblerString(classPath);
        return JavaTACClassString;
    }
    public static String threeWayDisassemblerString(String filepath) {
        StringBuilder tacCodeString = new StringBuilder();
        uriProject = Project.apply(new File(filepath));
        javaProject = new JavaProject(uriProject);
        ConstArray<org.opalj.br.ClassFile> classFileConstArray =
                javaProject.project().allProjectClassFiles();
        methodTACodeFunction = javaProject.project().get(DefaultTACAIKey$.MODULE$);
        for (int i = 0; i < classFileConstArray.length(); i++) {
            org.opalj.br.ClassFile classFile = classFileConstArray.apply(i);
            RefArray<Method> methods = classFile.methods();
            //            TODO     classFile.fields(); soll nicht
            //            TODO     classFile.attributes();
            for (int j = 0; j < methods.length(); j++) {
                Method method = methods.apply(j);
                if (method.body().isDefined()) {
                    System.out.println(method.toJava());
                    TACode<TACMethodParameter, DUVar<KnownTypedValue>> TacCode =
                            methodTACodeFunction.apply(method);
                    tacCodeString.append(ToTxt.apply(TacCode).mkString("\n"));
                }
            } // for(j)
        } // for(i)

        // TODO
        try {
            return new String(tacCodeString.toString().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        //        return tacCodeString;
    }

    public static String JavaClasstoHtmlForm(VirtualFile virtualClassFile) {
        classPath = virtualClassFile.getPath();
        String JavaHTMLClass = JavaClasstoHTMLForm(classPath);
        return JavaHTMLClass;
    }
    public static String JavaClasstoHTMLForm(String classPath) {
        Path path = Paths.get(classPath);
        File file = path.toFile();
        // TODO scala.collection.immutable.List<Object> classFileList;
        String toHtmlAsString;
        try (FileInputStream fis = new FileInputStream(file);
             DataInputStream dis = new DataInputStream(fis)) {
            // get the class file and construct the HTML string
            org.opalj.da.ClassFile cf = (org.opalj.da.ClassFile) ClassFileReader.ClassFile(dis).head();

            // TODO: make constant or so


            // ordentliches HTML Code
            toHtmlAsString =
                    "<html>\n<head>\n<style>"
                            + cf.TheCSS()
                            + "</style>\n</head>\n<body>\n<a href=\"#&lt;init&gt;()V\">toMain</a>\n"
                            + "<script>\n" +
                                "function scrollTo(elementId) {\n" +
                                "    var element = document.getElementById(elementId);\n" +
                                "    element.scrollIntoView(); \n" +
                                "    element.open  = true;\n" +
                                "    var orig = element.style.backgroundColor;\n" +
                                "    element.style.backgroundColor = \"#FDFF47\";\n" +
                                "    window.setTimeout(function(){\n" +
                                "       element.style.backgroundColor = orig;\n" +
                                "    }, 2000);\n" +
                                "}\n" +
                            "</script>"
                            + cf.classFileToXHTML(new Some(classPath)).toString()
                            + "\n</body>\n</html>";

            toHtmlAsString = toHtmlAsString.replace("id=\"&lt;init&gt;()V\"", "id=\"init()V\"");
        } catch (IOException e) {
            e.printStackTrace();
            // fehlerausgabe im HTMLFormat für den Editor
            toHtmlAsString = "<html><body><h1>Something went wrong in Opal.toHTMLForm()</h1></body></html>";
        }
        return toHtmlAsString;
    }

    //TODO rewrite Output VirutalFile
    public static VirtualFile prepareHtml(@NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
        // All files selected in the "Project"-View
        if (Compiler.make(project)) {
            String classHtmlForm = Opal.JavaClasstoHtmlForm(virtualFile);
            // Save the decompiled code to a file
            String basePath = project.getBasePath();
            File baseDir = new File(basePath);
            File temp = (new File(classPath)).getParentFile();
            ArrayList<String> dirNames = new ArrayList<>();
            while (!temp.getAbsolutePath().equals(baseDir.getAbsolutePath())) {
                dirNames.add(temp.getName());
                temp = temp.getParentFile();
            }
            File disassembledDir =
                    new File(basePath + File.separator + GlobalData.DISASSEMBLED_FILES_DIR);
            if (!disassembledDir.exists()) {
                disassembledDir.mkdir();
            }

            temp = new File(disassembledDir.getAbsolutePath());
            for (int i = 0; i < dirNames.size(); i++) {
                temp =
                        new File(
                                temp.getAbsolutePath() + File.separator + dirNames.get(dirNames.size() - (i + 1)));
                if (!temp.exists()) {
                    temp.mkdir();
                }
            }
            String noEnding = virtualFile.getNameWithoutExtension();
            File disassembledFile =
                    new File(
                            temp.getAbsolutePath()
                                    + File.separator
                                    + noEnding
                                    + "."
                                    + GlobalData.DISASSEMBLED_FILE_ENDING_HTML);

            if (!disassembledFile.exists()) {
                try {
                    disassembledFile.createNewFile();
                } catch (IOException e) {
                    // empty
                }
            }
            try {
                SaveFile.saveFile(classHtmlForm, disassembledFile.getAbsolutePath());
            } catch (InputNullException e0) {
                // empty
            } catch (NotEnoughRightsException e1) {
                // empty
            } catch (IsNotAFileException e2) {
                // empty
            } catch (ErrorWritingFileException e3) {
                // empty
            }
            //TODO better refresh siehe unten eventuell in die Save Datei ein Project Refresher
            return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(disassembledFile);
        }
        return null;
    } // prepareHtml()

    //TODO rewrite OUTPUT VirtualFile and clean up like above
    public static VirtualFile prepareTAC(@NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile classFile) {
        if (Compiler.make(project)) {
            String basePath = project.getBasePath();
            classPath = classFile.getPath();
            File baseDir = new File(basePath);
            File temp = (new File(classPath)).getParentFile();
            ArrayList<String> dirNames = new ArrayList<String>();
            while (!temp.getAbsolutePath().equals(baseDir.getAbsolutePath())) {
                dirNames.add(temp.getName());
                temp = temp.getParentFile();
            }
            File disassembledDir = new File(basePath + File.separator + GlobalData.DISASSEMBLED_FILES_DIR);
            if (!disassembledDir.exists()) {
                disassembledDir.mkdir();
            }

            temp = new File(disassembledDir.getAbsolutePath());
            for (int i = 0; i < dirNames.size(); i++) {
                temp =
                        new File(
                                temp.getAbsolutePath() + File.separator + dirNames.get(dirNames.size() - (i + 1)));
                if (!temp.exists()) {
                    temp.mkdir();
                }
            }
            String noEnding = classFile.getNameWithoutExtension();
            File disassembledFile =
                    new File(
                            temp.getAbsolutePath()
                                    + File.separator
                                    + noEnding
                                    + "."
                                    + GlobalData.DISASSEMBLED_FILE_ENDING_TAC);

            if (!disassembledFile.exists()) {
                try {
                    disassembledFile.createNewFile();
                } catch (IOException e) {
                    // empty
                }
            }
            String tac = Opal.JavaClasstoTACForm(classFile);
            try {
                SaveFile.saveFile(tac, disassembledFile.getAbsolutePath());
            } catch (InputNullException e0) {
                // empty
            } catch (NotEnoughRightsException e1) {
                // empty
            } catch (IsNotAFileException e2) {
                // empty
            } catch (ErrorWritingFileException e3) {
                // empty
            }
            // TODO noch nicht umgesetzt da keine Zeit also die VFS
            return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(disassembledFile);
        }
        return null;
    }

    public static void setProject(com.intellij.openapi.project.Project inteliProject) {
        project = inteliProject;
    }

}