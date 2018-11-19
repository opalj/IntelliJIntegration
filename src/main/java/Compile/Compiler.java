package Compile;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Compiler {

    /*
        Finds filebased or dirbased Projectpaths
     */

    static private String find(String filePath) {
        Path path =  Paths.get(filePath);
        String s;
        while(path != null) {
            s = find(path.toFile());
            if(s != null)
                return s;
            path = path.getParent();
        }
            return "";
    }
    /*
            recursive helpfunction for the find method
     */
    static private String find(@NotNull  File fl){
        for(File f : fl.listFiles()){
            if(f.isDirectory() && f.getName().equals(".idea"))
                return f.getParentFile().getAbsolutePath();
            else if(f.isFile() && f.getName().endsWith(".ipr"))
                return f.getAbsolutePath(); // nocht nicht getestet
            else if(f.isDirectory())
                return find(f);
        }
        return null;
    }
    /*
            compile a project with a given (open) Project-Object
     */
    public static boolean make(@NotNull final Project project){

        CompilerManager compManager = CompilerManager.getInstance(project);
        CompileScope projectCompileScope = compManager.createProjectCompileScope(project);
        if(!compManager.isUpToDate(projectCompileScope)){
            ApplicationManager.getApplication().invokeAndWait( () -> compManager.make(null));
            return true;
        }else{
            //TODO
            // OPALFRAMEWORKINGER ADD OR BOOLEANINGER
            return true;
        }
    }
    /*
            compile a project with a given filePath-String, searches naivly the first .idea-Dir or *.ipr-file
     */
    public static boolean make(@NotNull String filePath )  {
        filePath = find(filePath);
        if(filePath == null)
            return false;
        ProjectManager projectManager = ProjectManager.getInstance();
        Project project;
        // Look for OpenProjects
        for (Project openProject:projectManager.getOpenProjects()) {
            if(openProject!= null && openProject.getBasePath().equals(filePath)){
                project = openProject;
                return make(project);
            }
        }
        // try to open project
        try {
            project = projectManager.loadAndOpenProject(filePath);
            if(project == null)
                return false;
            boolean bMade = make(project);
            projectManager.closeProject(project);
            return bMade;
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
        return false;
    }


}
