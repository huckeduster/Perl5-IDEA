/*
 * Copyright 2015-2018 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.codeInsight.typeInference.value;

import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.perl5.PerlBundle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValuesManager.BLESSED_ID;

public class PerlBlessedValue extends PerlValue {
  @NotNull
  private final PerlValue myValue;
  @NotNull
  private final PerlValue myBless;

  PerlBlessedValue(@NotNull PerlValue value, @NotNull PerlValue bless) {
    myValue = value;
    myBless = bless;
  }

  PerlBlessedValue(@NotNull StubInputStream dataStream) throws IOException {
    super(dataStream);
    myValue = PerlValuesManager.readValue(dataStream);
    myBless = PerlValuesManager.readValue(dataStream);
  }

  @NotNull
  public PerlValue getBless() {
    return myBless;
  }

  @NotNull
  public PerlValue getValue() {
    return myValue;
  }

  @Override
  protected int getSerializationId() {
    return BLESSED_ID;
  }

  @Override
  protected void serializeData(@NotNull StubOutputStream dataStream) throws IOException {
    myValue.serialize(dataStream);
    myBless.serialize(dataStream);
  }

  @NotNull
  @Override
  public String getPresentableText() {
    return PerlBundle.message("perl.value.presentable.blessed", myValue, myBless);
  }
}