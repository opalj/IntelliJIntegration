package opalintegration;

import java.util.Arrays;
import org.opalj.br.Type;
import org.opalj.br.instructions.*;

/**
 * Visits specific bytecode instructions which are defined in the list, and for each of them it
 * returns a string representation.
 *
 * <p>This visitor does not handle all bytecode instructions. Instead, it visits those instructions
 * for which the default "toString()" method does not behave as wanted.
 *
 * <p>TODO: visit(TABLESWITCH)
 */
public class StringVisitor extends ListVisitor<Instruction, String> implements InstructionVisitor {
  public StringVisitor() {
    super(
        Arrays.asList(
            ANEWARRAY.class,
            PUTFIELD.class,
            PUTSTATIC.class,
            GETSTATIC.class,
            GETFIELD.class,
            LoadString.class, // rather LDC visitor
            LoadString_W.class,
            LDC.class,
            LDC_W.class));
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
    return ldc.mnemonic().toUpperCase() + "(" + ((Type) ldc.value()).toJava() + ")";
  }

  @Override
  public String visit(LDC_W ldc_w) {
    return ldc_w.mnemonic().toUpperCase() + "(" + ((Type) ldc_w.value()).toJava() + ")";
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
  public String visit(Instruction instruction) {
    return instruction.toString();
  }
}
