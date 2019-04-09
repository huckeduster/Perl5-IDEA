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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Represents non-mutation operation value on some base value. E.g. item, slice, keys
 */
abstract class PerlOperationValue extends PerlValue {
  @NotNull
  private final PerlValue myBaseValue;

  public PerlOperationValue(@NotNull PerlValue baseValue) {
    myBaseValue = baseValue;
  }

  public PerlOperationValue(@NotNull StubInputStream dataStream) throws IOException {
    super(dataStream);
    myBaseValue = PerlValuesManager.readValue(dataStream);
  }

  @NotNull
  protected final PerlValue getBaseValue() {
    return myBaseValue;
  }

  @Override
  protected void serializeData(@NotNull StubOutputStream dataStream) throws IOException {
    myBaseValue.serializeData(dataStream);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PerlOperationValue value = (PerlOperationValue)o;

    return myBaseValue.equals(value.myBaseValue);
  }

  @Override
  public int computeHashCode() {
    int result = super.computeHashCode();
    result = 31 * result + myBaseValue.hashCode();
    return result;
  }
}