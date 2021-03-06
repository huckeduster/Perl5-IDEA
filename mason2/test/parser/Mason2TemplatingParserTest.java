/*
 * Copyright 2015-2019 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package parser;

import com.intellij.psi.LanguageFileViewProviders;
import com.perl5.lang.mason2.Mason2TemplatingLanguage;
import com.perl5.lang.mason2.Mason2TemplatingParserDefinition;
import com.perl5.lang.mason2.filetypes.Mason2FileTypeFactory;
import com.perl5.lang.mason2.psi.Mason2TemplatingFileViewProviderFactory;


public class Mason2TemplatingParserTest extends PerlParserTestBase {
  public Mason2TemplatingParserTest() {
    super("", Mason2FileTypeFactory.TOP_LEVEL_COMPONENT_EXTENSION, new Mason2TemplatingParserDefinition());
  }

  @Override
  protected String getTestDataPath() {
    return "testData/parser/template";
  }


  public void testTestComponent() {
    doTest(true);
  }

  public void testIssue1077() {
    doTest(true);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    LanguageFileViewProviders.INSTANCE
      .addExplicitExtension(Mason2TemplatingLanguage.INSTANCE, new Mason2TemplatingFileViewProviderFactory());
  }
}
