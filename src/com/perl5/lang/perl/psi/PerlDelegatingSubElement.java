/*
 * Copyright 2015-2017 Alexandr Evstigneev
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

import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import com.perl5.lang.perl.psi.utils.PerlSubArgument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PerlDelegatingSubElement extends PerlDelegatingLightNamedElement<PerlPolyNamedElement> implements PerlSubDefinitionElement {
  @Nullable
  private final String myPackageName;

  @NotNull
  private final List<PerlSubArgument> mySubArguments;

  @Nullable
  private final PerlSubAnnotations myAnnotations;

  public PerlDelegatingSubElement(PerlPolyNamedElement delegate,
                                  String name,
                                  @Nullable String packageName,
                                  @NotNull List<PerlSubArgument> subArguments,
                                  @Nullable PerlSubAnnotations annotations) {
    super(delegate, name);
    myPackageName = packageName;
    mySubArguments = subArguments;
    myAnnotations = annotations;
  }

  @Nullable
  @Override
  public String getPackageName() {
    return myPackageName;
  }

  @NotNull
  @Override
  public List<PerlSubArgument> getSubArgumentsList() {
    return mySubArguments;
  }

  @Override
  public String getSubName() {
    return getName();
  }

  @Nullable
  @Override
  public PerlSubAnnotations getAnnotations() {
    return myAnnotations;
  }

  @Nullable
  @Override
  public String getExplicitPackageName() {
    return myPackageName;
  }
}
