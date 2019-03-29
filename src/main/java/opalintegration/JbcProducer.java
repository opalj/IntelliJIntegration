package opalintegration;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.LineMarkerRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import opalintegration.Visitor.Instruction.InstructionVisitorImpl;
import org.opalj.br.*;
import org.opalj.br.instructions.Instruction;

/** Is responsible for creating and providing the bytecode representation of a class file */
class JbcProducer extends DecompiledTextProducer {

  private static final Logger LOGGER = Logger.getLogger(JbcProducer.class.getName());

  @Override
  String methodBody(Method method) {
    StringBuilder methodBodyText = new StringBuilder();
    InstructionVisitorImpl instructionVisitorImpl = new InstructionVisitorImpl();

    if (method.body().isDefined()) {
      Code methodBody = method.body().get();
      Instruction[] instructions = methodBody.instructions();

      String methodInformation =
          String.format(
              "// [size: %d bytes, max stack: %d bytes, max locals: %d]\n",
              methodBody.codeSize(), methodBody.maxStack(), methodBody.maxLocals());
      methodBodyText.append(methodInformation);
      methodBodyText.append(Tables.methodTypeSignature(method));

      String pcLineInstr = String.format("\t%-6s %-6s %s\n", "PC", "Line", "Instruction");
      methodBodyText.append(pcLineInstr);
      for (int k = 0; k < instructions.length; k++) {
        if (instructions[k] != null) {
          try {
            String instruction;
            instruction = instructionVisitorImpl.accept(instructions[k], k);

            instruction = opalStringBugFixer(instruction);

            String formattedInstrLine =
                String.format(
                    "\t%-6d %-6s %s\n",
                    k,
                    methodBody.lineNumber(k).isDefined() ? methodBody.lineNumber(k).get() : 0,
                    instruction);
            methodBodyText.append(formattedInstrLine);
          } catch (NoSuchElementException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
          }
        }
      } // for(instructions)

      if (Tables.hasTables(method)) {
        methodBodyText.append("\n  Tables {");
        methodBodyText.append(Tables.exceptionTable(method.body()));
        methodBodyText.append(Tables.localVariableTable(methodBody));
        methodBodyText.append(Tables.localVariableTypeTable(methodBody));
        methodBodyText.append(Tables.stackMapTable(methodBody));
        methodBodyText.append("  } // Tables\n");
      }

//      // render exceptions
//      if(Tables.hasExceptionTable(method.body())) {
//        LineMarkerRenderer exceptionLineMarkerRenderer = new ExceptionLineMarkerRenderer(Color.BLUE);
//
//        DataContext dataContext = DataManager.getInstance().getDataContext();
//        Project project = DataKeys.PROJECT.getData(dataContext);
//
//        if(project != null) {
//          FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
//          Editor selectedEditor = fileEditorManager.getSelectedTextEditor();
//
//          System.out.println(selectedEditor.getDocument().getLineCount());
//          System.out.println("lineheight: " + selectedEditor.getLineHeight());
//          selectedEditor.getMarkupModel().addRangeHighlighter(0, 200, HighlighterLayer.FIRST, null,
//                  HighlighterTargetArea.LINES_IN_RANGE);
//          RangeHighlighter rangeHighlighter = selectedEditor.getMarkupModel().getAllHighlighters()[0];
//          rangeHighlighter.setLineMarkerRenderer(exceptionLineMarkerRenderer);
//
//        }
//        else {
//          System.out.println("PROJECT NULL??");
//        }
//      }
    } // if(body.defined)

    return methodBodyText.toString();
  }

  class ExceptionLineMarkerRenderer implements LineMarkerRenderer {
    private static final int DEEPNESS = 0;
    private static final int THICKNESS = 5;
    private final Color myColor;

    public ExceptionLineMarkerRenderer(Color color) {
      myColor = color;
    }

    @Override
    public void paint(Editor editor, Graphics g, Rectangle r) {
      int height = r.height + editor.getLineHeight() + 30;
      g.setColor(myColor);
      g.fillRect(r.x, r.y, THICKNESS, height);
      g.fillRect(r.x + THICKNESS, r.y, DEEPNESS, THICKNESS);
      g.fillRect(r.x + THICKNESS, r.y + height - THICKNESS, DEEPNESS, THICKNESS);
    }
  }
}
