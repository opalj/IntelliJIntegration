/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package JavaByteCodeLanguage.LanguageAndFileType;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The FileTypeFactory is used to register a file type via the com.intellij.fileTypeFactory platform
 * extension point in plugin.xml
 */
public class JavaByteCodeFileTypeFactory extends FileTypeFactory {

  @Override
  public void createFileTypes(@NotNull FileTypeConsumer consumer) {
    consumer.consume(JavaByteCodeFileType.INSTANCE);
  }
}
