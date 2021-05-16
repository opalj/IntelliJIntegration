/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.taclanguage.languageandfiletype;

import com.intellij.lang.Language;

public class TAC extends Language {

  public static final TAC INSTANCE = new TAC();

  private TAC() {
    super("TAC");
  }
}
