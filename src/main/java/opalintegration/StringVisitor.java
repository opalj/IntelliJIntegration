package opalintegration;

import java.util.Arrays;

import org.opalj.br.Type;
import org.opalj.br.instructions.*;

public class StringVisitor extends ListVisitor<Instruction, String> implements InstructionVisitor {
  public StringVisitor() {
    super(
        Arrays.asList(
            ANEWARRAY.class,
            PUTFIELD.class,
            PUTSTATIC.class,
            GETSTATIC.class,
            GETFIELD.class,
            LDC.class));
  }

  public String visit(ANEWARRAY anewarray) {
    return anewarray.mnemonic().toUpperCase() + " " + anewarray.componentType().toJava();
  }

  public String visit(GETSTATIC getstatic) {
    return getstatic.mnemonic().toUpperCase()
        + " "
        + getstatic.declaringClass().toJava()
        + "."
        + getstatic.name()
        + " : "
        + getstatic.fieldType().toJava();
  }

  public String visit(GETFIELD getfield) {
    return "GET "
        + getfield.declaringClass().toJava()
        + "."
        + getfield.name()
        + " : "
        + getfield.fieldType().toJava();
  }

  public String visit(PUTFIELD putfield) {
    return "PUT "
        + putfield.declaringClass().toJava()
        + "."
        + putfield.name()
        + " : "
        + putfield.fieldType().toJava();
  }

  public String visit(PUTSTATIC putstatic) {
    return "PUTSTATIC "
        + putstatic.declaringClass().toJava()
        + "."
        + putstatic.name()
        + " : "
        + putstatic.fieldType().toJava();
  }

  @Override
  public String visit(LDC ldc) {
    return ldc.mnemonic().toUpperCase()+"("+((Type)ldc.value()).toJava()+")";
  }

  @Override
  public String visit(Instruction instruction) {
    return instruction.toString(); // .replaceAll("\\(", "\t\t").replaceAll("\\)", "");
  }
}
