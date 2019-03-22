package opalintegration.Visitor.Instruction;

import opalintegration.Visitor.ElementAcceptor;
import opalintegration.Visitor.StackMapVisitor;
import org.opalj.br.*;
import org.opalj.collection.immutable.RefArray;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StackMapVisitorImpl extends ElementAcceptor<StackMapFrame,String> implements StackMapVisitor {
    @Override
    public String visit(StackMapFrame frame) {
        Class<? extends StackMapFrame> frameClass = frame.getClass();
        return String.format("\t\t%d %s %d %d \n", pc[0],frameClass.getSimpleName(),frame.frameType(),frame.offset(0)-1);
    }

    @Override
    public String visit(SameLocals1StackItemFrame frame) {
        String verfictionTypeInfoString = frame.verificationTypeInfoStackItem().isObjectVariableInfo()? frame.verificationTypeInfoStackItem().asObjectVariableInfo().clazz().toJava():"";
        return String.format("\t\t%d %s %d %d %s\n",pc[0],frame.getClass().getSimpleName(),frame.frameType(),frame.offset(0)-1, verfictionTypeInfoString);
    }

    @Override
    public String visit(SameFrame frame) {
        return String.format("\t\t%d %s %d %d\n",pc[0],frame.getClass().getSimpleName(),frame.frameType(),frame.offset(0)-1);
    }

    @Override
    public String visit(SameLocals1StackItemFrameExtended frame) {
        String verfictionTypeInfoString = frame.verificationTypeInfoStackItem().isObjectVariableInfo()? frame.verificationTypeInfoStackItem().asObjectVariableInfo().clazz().toJava():"";
        return String.format("\t\t%d %s %d %d %s\n",pc[0],frame.getClass().getSimpleName(),((StackMapFrame)frame).frameType(),frame.offsetDelta(),verfictionTypeInfoString);
    }

    @Override
    public String visit(ChopFrame frame) {
        return String.format("\t\t%d %s %d %d \n",pc[0],frame.getClass().getSimpleName(),((StackMapFrame)frame).frameType(),frame.offsetDelta());
    }

    @Override
    public String visit(SameFrameExtended frame) {
        return String.format("\t\t%d %s %d %d \n",pc[0],frame.getClass().getSimpleName(),frame.frameType(),frame.offsetDelta());
    }

    @Override
    public String visit(AppendFrame frame) {
        return String.format("\t\t%d %s %d %d %s\n",pc[0],frame.getClass().getSimpleName(),frame.frameType(),frame.offsetDelta(),verficicationTypeInfoToString(frame.verificationTypeInfoLocals()));
    }

    @Override
    public String visit(FullFrame frame) {
        return String.format("\t\t%d %s %d %d Locals:%s Stack:%s\n",pc[0],frame.getClass().getSimpleName(),frame.frameType(),frame.offsetDelta(),verficicationTypeInfoToString(frame.verificationTypeInfoLocals()), verficicationTypeInfoToString(frame.verificationTypeInfoStack()));
    }
    private String verficicationTypeInfoToString(RefArray<VerificationTypeInfo> verificationTypeInfoRefArray){
        VerificationTypeInfo[] verificationTypeInfos = new VerificationTypeInfo[verificationTypeInfoRefArray.size()];
        verificationTypeInfoRefArray.copyToArray(verificationTypeInfos);
        return Arrays.stream(verificationTypeInfos).map(s->s.isObjectVariableInfo()? s.asObjectVariableInfo().clazz().toJava():s.toString().replaceFirst("VariableInfo","")).collect(Collectors.joining(" , "));
    }
}
