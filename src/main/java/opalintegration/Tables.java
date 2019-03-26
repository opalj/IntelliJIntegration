package opalintegration;

import opalintegration.Visitor.StackMap.StackMapVisitorImpl;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.br.*;
import org.opalj.collection.IntIterator;
import org.opalj.collection.immutable.RefArray;
import scala.Option;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

final class Tables {

    private static final Logger LOGGER = Logger.getLogger(Tables.class.getName());

    private Tables() {}


    static boolean hasTables(@NotNull Method method) {
        if(!method.methodTypeSignature().isEmpty()) {
            return true;
        }

        if(method.body().isDefined()) {
            Code methodBody = method.body().get();

            return  methodBody.localVariableTable().isDefined()
                    || methodBody.stackMapTable().isDefined()
                    || !methodBody.localVariableTypeTable().isEmpty();
        }

        return false;
    }


    /** Converts a given list of annotations into a Java-like representation. */
    @NotNull
    static String annotationsToJava(@NotNull RefArray<Annotation> annotations, String before, String after) {
        if (!annotations.isEmpty()) {
            Annotation[] annotationsCopy = new Annotation[annotations.size()];
            annotations.copyToArray(annotationsCopy);
            // e.g. @java.lang.Deprecated
            String annotationInJavaStyle =
                    Arrays.stream(annotationsCopy).map(Annotation::toJava).collect(Collectors.joining("\n"));

            // scala annotations don't work for some
            if(annotationInJavaStyle.startsWith("@scala")) {
                return "";
            }

            return before + annotationInJavaStyle + after;
        } else {
            return "";
        }
    }

    /**
     * E.g. if the method throws two exceptions, say IOException and RuntimeException, then
     * the output will be: throws java.io.IOException, java.lang.RuntimeException
     *
     * @param exceptionTable The table which contains the exceptions that the method throws
     * @return a string which contains a throws clause
     */
    @NotNull
    static String thrownExceptions(@NotNull Option<ExceptionTable> exceptionTable) {
        if (!exceptionTable.isDefined()) {
            return "";
        }
        StringBuilder throwStringBuilder = new StringBuilder();
        throwStringBuilder.append(" throws ");
        ObjectType[] exceptions = new ObjectType[exceptionTable.get().exceptions().size()];
        exceptionTable.get().exceptions().copyToArray(exceptions);
        throwStringBuilder.append(
                Arrays.stream(exceptions).map(ObjectType::toJava).collect(Collectors.joining(", ")));
        return throwStringBuilder.toString();
    }


    @NotNull
    static String innerClassTable(@NotNull ClassFile classFile) {
        if(classFile.innerClasses().isEmpty()) {
            return "";
        }

        StringBuilder innerClassTable = new StringBuilder();

        innerClassTable.append("\tInnerClasses { ")
                .append("// [size: ")
                .append(classFile.innerClasses().get().size())
                .append(" item(s)]\n");


        classFile.innerClasses().get().foreach(innerClass -> {
            try {
                String innerClassEntry = String.format(
                        "\t\t%s { %s %s }\n",
                        innerClass.outerClassType().get().toJava(),
                        AccessFlags.classFlagsToJava(innerClass.innerClassAccessFlags()),
                        innerClass.innerName().get()
                );
                innerClassTable.append(innerClassEntry);
            } catch (NoSuchElementException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }

            return "";
        });

        innerClassTable.append("\t}\n");
        return innerClassTable.toString();
    }


    /**
     * @param body the method body of which we want the local variable table
     * @return the local variable table of a method as a string, if it exists, empty string otherwise
     */
    @NotNull
    static String localVariableTable(@NotNull Code body) {
        // if there is no local variable table, return an empty string
        if (!body.localVariableTable().isDefined()) {
            return "";
        }

        StringBuilder localVariableTable = new StringBuilder();

        RefArray<LocalVariable> refArrayOption = body.localVariableTable().get();
        localVariableTable
                .append("\n\tLocalVariableTable { // [size: ")
                .append(refArrayOption.length())
                .append(" item(s)]\n");
        for (int k = 0; k < refArrayOption.length(); k++) {
            LocalVariable localVariable = refArrayOption.apply(k);
            String localVariableEntry = String.format(
                    "\t\tpc=[%d > %d) / lv=%d => %s %s\n",
                    localVariable.startPC(),
                    localVariable.length(),
                    localVariable.index(),
                    localVariable.fieldType().toJava(),
                    localVariable.name()
            );
            localVariableTable.append(localVariableEntry);
        }
        localVariableTable.append("\t}\n");

        return localVariableTable.toString();
    }

    /**
     * @param body the method body of which we want the stack map table
     * @return the stack map table of a method as a string, if it exists, empty string otherwise
     */
    @NotNull
    static String stackMapTable(@NotNull Code body) {
        if (!body.stackMapTable().isDefined()) {
            return "";
        }

        StringBuilder stackMapTableString = new StringBuilder();

        StackMapTable stackMapTable = body.stackMapTable().get();
        RefArray<StackMapFrame> stackMapFrameRefArray = stackMapTable.stackMapFrames();
        StackMapVisitorImpl stackMapVisitor = new StackMapVisitorImpl();
        stackMapTableString
                .append("\n\tStackMapTable {")
                .append("\n")
                .append("\t\t//PC\tKind\tFrame Type\tOffset Delta\n");
        IntIterator pcIterator = stackMapTable.pcs().iterator();
        for (int i = 0; i < stackMapFrameRefArray.length(); i++) {
            int pc = pcIterator.next();
            stackMapTableString.append(stackMapVisitor.accept(stackMapFrameRefArray.apply(i),pc));
        }
        stackMapTableString.append("\t}\n");

        return stackMapTableString.toString();
    }

    @NotNull
    static String localVariableTypeTable(@NotNull Code body) {
        StringBuilder localVariableTypeTable = new StringBuilder();

        if(body.localVariableTypeTable().isEmpty()) {
            return "";
        }

        localVariableTypeTable
                .append("\n\tLocalVariableTypeTable { ")
                .append("// [size: ")
                .append(body.localVariableTypeTable().size()) // TODO: not always correct? see Cl.ManyMethods#genericMethod
                .append(" item(s)]\n");

        //write the text for the localVarTypeTable
        body.localVariableTypeTable().foreach(locVarTypeRefArray -> {
            for(int i=0; i < locVarTypeRefArray.length(); ++i) {
                LocalVariableType localVariableType = locVarTypeRefArray.apply(i);
                String localVariableTypeEntry = String.format(
                        "\t\tpc=[%d => %d) / lv=%d => %s: %s\n",
                        localVariableType.startPC(),
                        localVariableType.startPC() + localVariableType.length(),
                        localVariableType.index(),
                        localVariableType.name(),
                        localVariableType.signature().toJVMSignature().replaceAll(";>", ">"));
                localVariableTypeTable.append(localVariableTypeEntry);
            }

            // return value can be ignored
            return "";
        });

        localVariableTypeTable.append("\t}\n");

        return localVariableTypeTable.toString();
    }

    @NotNull
    static String methodTypeSignature(Method method) {
        StringBuilder methodTypeSig = new StringBuilder();

        MethodTypeSignature methodTypeSignature;
        try {
            methodTypeSignature = method.methodTypeSignature().get();
        } catch(Exception e) {
            return "";
        }

        methodTypeSig.append("\t// MethodTypeSignature: ").append(methodTypeSignature.toJVMSignature()).append("\n");
        return methodTypeSig.toString();
    }

    /**
     * E.g. if the exception table contains two exceptions, say IOException and RuntimeException, then
     * the output will be: generate in a Table
     *
     * @param body The table which contains the exceptions that the method throws
     * @return a string which contains a throws clause
     */
    @NotNull
    static String exceptionTable(@NotNull Option<Code> body) {
        if (!body.isDefined() || body.get().exceptionHandlers().isEmpty()) {
            return "";
        }
        StringBuilder exceptionHandlerBuilder = new StringBuilder();
        exceptionHandlerBuilder.append("\n\t ExceptionTable {\n");
        ExceptionHandler[] exceptionHandlers = new ExceptionHandler[body.get().exceptionHandlers().size()];
        body.get().exceptionHandlers().copyToArray(exceptionHandlers);
        exceptionHandlerBuilder.append(Arrays.stream(exceptionHandlers).map(e-> "\t\ttry ["+e.startPC()+", "+e.endPC()+") catch "+e.handlerPC()+" "+(e.catchType().isDefined()?e.catchType().get().toJava():"ANY")).collect(Collectors.joining("\n")));
        exceptionHandlerBuilder.append("\n\t}\n");
        return exceptionHandlerBuilder.toString();
    }
}
