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

package com.perl5.lang.perl.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilCore;
import com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlScalarValue;
import com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValues.UNKNOWN_VALUE;
import static com.perl5.lang.perl.lexer.PerlElementTypesGenerated.OPERATOR_MUL;
import static com.perl5.lang.perl.util.PerlPackageUtil.PACKAGE_ANY;
import static com.perl5.lang.perl.util.PerlPackageUtil.PACKAGE_ANY_VALUE;


public interface PerlAnnotationWithValue extends PsiElement, PerlAnnotation {
  /**
   * Trying to search for netsted NamespaceElement
   * fixme this is raw, should be a type
   *
   * @return psi element or null
   * @deprecated use {@link com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValue}
   */
  @Deprecated
  @Nullable
  default PerlNamespaceElement getType() {
    return PsiTreeUtil.getChildOfType(this, PerlNamespaceElement.class);
  }

  /**
   * @deprecated use {@link com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValue}
   */
  @Deprecated
  @Nullable
  default String getReturnClass() {
    PerlNamespaceElement namespaceElement = getType();
    if (namespaceElement != null) {
      return namespaceElement.getCanonicalName();
    }

    PsiElement run = getFirstChild();
    while (run != null) {
      if (PsiUtilCore.getElementType(run) == OPERATOR_MUL) {
        return PACKAGE_ANY;
      }
      run = run.getNextSibling();
    }

    return null;
  }

  /**
   * @return a value described in this annotation
   */
  @NotNull
  default PerlValue getValue() {
    String returnClass = getReturnClass();
    return returnClass == null ? UNKNOWN_VALUE :
           returnClass.equals(PACKAGE_ANY) ? PACKAGE_ANY_VALUE : PerlScalarValue.create(returnClass);
  }
}
