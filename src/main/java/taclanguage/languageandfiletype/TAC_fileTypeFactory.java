/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package taclanguage.languageandfiletype;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class TAC_fileTypeFactory extends FileTypeFactory {

  @Override
  public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
    fileTypeConsumer.consume(TAC_filetype.INSTANCE);
  }
}
