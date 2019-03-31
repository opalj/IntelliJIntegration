package opalintegration.Visitor.Instruction;

import java.util.Arrays;
import java.util.stream.Collectors;
import opalintegration.Visitor.ElementAcceptor;
import org.opalj.br.instructions.*;
import org.opalj.collection.immutable.IntIntPair;

/**
 * Visits specific bytecode instructions which are defined in the list, and for each of them it
 * returns a string representation.
 *
 * <p>This visitor does not handle all bytecode instructions. Instead, it visits those instructions
 * for which the default "toString()" method does not behave as wanted.
 *
 * <p>
 */
public class InstructionVisitorImpl extends ElementAcceptor<Instruction, String>
    implements InstructionVisitor {

  public String visit(NEW mnemonicNew) {
    return mnemonicNew.mnemonic().toUpperCase() + "(" + mnemonicNew.objectType().toJava() + ")";
  }

  @Override
  public String visit(LoadInt l) {
    return l.mnemonic().toUpperCase() + "(" + l.value() + ")";
  }

  @Override
  public String visit(LOOKUPSWITCH s) {
    IntIntPair[] intIntPairs = new IntIntPair[s.npairs().size()];
    s.npairs().copyToArray(intIntPairs);
    return s.mnemonic().toUpperCase()
        + "(default:"
        + (s.defaultOffset() + pc[0])
        + "["
        + Arrays.stream(intIntPairs)
            .map(p -> "(case:" + p._1() + "," + (p._2() + pc[0]) + ")")
            .collect(Collectors.joining(""))
        + "])";
  }

  @Override
  public String visit(LoadInt_W l) {
    return l.mnemonic().toUpperCase() + "(" + l.value() + ")";
  }

  @Override
  public String visit(LoadDouble l) {
    return l.mnemonic().toUpperCase() + "(" + l.value() + ")";
  }

  @Override
  public String visit(LoadLong l) {
    return l.mnemonic().toUpperCase() + "(" + l.value() + ")";
  }

  @Override
  public String visit(LoadFloat l) {
    return l.mnemonic().toUpperCase() + "(" + l.value() + ")";
  }

  @Override
  public String visit(LoadFloat_W l) {
    return l.mnemonic().toUpperCase() + "(" + l.value() + ")";
  }

  @Override
  public String visit(LoadClass l) {
    return l.mnemonic().toUpperCase() + "(" + l.value().toJava() + ")";
  }

  @Override
  public String visit(LoadClass_W l) {
    return l.mnemonic().toUpperCase() + "(" + l.value().toJava() + ")";
  }

  @Override
  public String visit(LoadMethodHandle l) {
    return l.mnemonic().toUpperCase() + "(" + l.value().toJava() + ")";
  }

  @Override
  public String visit(LoadMethodHandle_W l) {
    return l.mnemonic().toUpperCase() + "(" + l.value().toJava() + ")";
  }

  @Override
  public String visit(LoadMethodType l) {
    return l.mnemonic().toUpperCase() + "(" + l.value().toJava() + ")";
  }

  @Override
  public String visit(LoadMethodType_W l) {
    return l.mnemonic().toUpperCase() + "(" + l.value().toJava() + ")";
  }

  public String visit(ANEWARRAY anewarray) {
    return anewarray.mnemonic().toUpperCase() + "(" + anewarray.componentType().toJava() + ")";
  }

  public String visit(MULTIANEWARRAY multianewarray) {
    return multianewarray.mnemonic().toUpperCase()
            + "("
            + multianewarray.arrayType().toJava()
            + ", "
            + multianewarray.dimensions()
            + ")";
  }

  public String visit(GETSTATIC getstatic) {
    return getstatic.mnemonic().toUpperCase()
        + "("
        + getstatic.declaringClass().toJava()
        + "."
        + getstatic.name()
        + " : "
        + getstatic.fieldType().toJava()
        + ")";
  }

  public String visit(GETFIELD getfield) {
    return "GET("
        + getfield.declaringClass().toJava()
        + "."
        + getfield.name()
        + " : "
        + getfield.fieldType().toJava()
        + ")";
  }

  public String visit(PUTFIELD putfield) {
    return "PUT("
        + putfield.declaringClass().toJava()
        + "."
        + putfield.name()
        + " : "
        + putfield.fieldType().toJava()
        + ")";
  }

  public String visit(PUTSTATIC putstatic) {
    return "PUTSTATIC("
        + putstatic.declaringClass().toJava()
        + "."
        + putstatic.name()
        + " : "
        + putstatic.fieldType().toJava()
        + ")";
  }

  @Override
  public String visit(LoadString_W lsw) {
    return lsw.mnemonic().toUpperCase() + "(\"" + lsw.value() + "\")";
  }

  @Override
  public String visit(LoadString ls) {
    return ls.mnemonic().toUpperCase() + "(\"" + ls.value() + "\")";
  }

  @Override
  public String visit(SimpleBranchInstruction sbi) {
    return sbi.mnemonic().toUpperCase() + "(" + (super.pc[0] + sbi.branchoffset()) + ")";
  }

  @Override
  public String visit(Instruction instruction) {
    return instruction.toString();
  }
}
