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

package com.perl5.lang.perl.psi.stubs;

import com.intellij.psi.stubs.*;
import com.intellij.util.containers.ContainerUtil;
import com.perl5.lang.perl.PerlLanguage;
import com.perl5.lang.perl.parser.elementTypes.PsiElementProvider;
import com.perl5.lang.perl.psi.PerlPolyNamedElement;
import com.perl5.lang.perl.psi.utils.PerlLightStubUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class PerlPolyNamedElementType extends IStubElementType<PerlPolyNamedElementStub, PerlPolyNamedElement>
  implements PsiElementProvider {
  public PerlPolyNamedElementType(@NotNull String debugName) {
    super(debugName, PerlLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public PerlPolyNamedElementStub createStub(@NotNull PerlPolyNamedElement psi, StubElement parentStub) {
    return new PerlPolyNamedElementStub(parentStub, this, ContainerUtil.map(psi.getLightElements(), PerlLightStubUtil::createStub));
  }

  @NotNull
  @Override
  public String getExternalId() {
    return "perl.poly." + super.toString();
  }

  @Override
  public void serialize(@NotNull PerlPolyNamedElementStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    List<StubBase> stubs = stub.getLightNamedElementsStubs();
    dataStream.writeInt(stubs.size());
    for (StubBase lightStub : stubs) {
      Serializer serializer = Serializer.getForStub(lightStub);
      dataStream.writeInt(serializer.getId());
      //noinspection unchecked
      serializer.getElementType().serialize(lightStub, dataStream);
    }
  }

  @NotNull
  @Override
  public PerlPolyNamedElementStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
    List<StubBase> lightElementsStubs = new ArrayList<>();
    PerlPolyNamedElementStub stub = new PerlPolyNamedElementStub(parentStub, this, lightElementsStubs);
    int stubsNumber = dataStream.readInt();
    for (int i = 0; i < stubsNumber; i++) {
      Serializer serializer = Serializer.getById(dataStream.readInt());
      //noinspection unchecked
      serializer.getElementType().deserialize(dataStream, stub);
    }
    return stub;
  }

  @Override
  public void indexStub(@NotNull PerlPolyNamedElementStub stub, @NotNull IndexSink sink) {
    //noinspection unchecked
    stub.getLightNamedElementsStubs().forEach(lightStub -> Serializer.getForStub(lightStub).getElementType().indexStub(lightStub, sink));
  }

  private enum Serializer {
    SUB_DEFINITION(0, PerlStubElementTypes.SUB_DEFINITION),
    NAMESPACE_DEFINITION(1, PerlStubElementTypes.PERL_NAMESPACE);

    private final int myId;
    @NotNull
    private IStubElementType myElementType;

    Serializer(int id, @NotNull IStubElementType elementType) {
      myElementType = elementType;
      myId = id;
    }

    @NotNull
    public IStubElementType getElementType() {
      return myElementType;
    }

    public int getId() {
      return myId;
    }

    public static Serializer getForStub(@NotNull StubBase stubElement) {
      throw new IllegalArgumentException("Don't know which serializer to use for " + stubElement);
    }

    public static Serializer getById(int id) {
      for (Serializer serializer : values()) {
        if (serializer.getId() == id) {
          return serializer;
        }
      }
      throw new IllegalArgumentException("Has no serializer with id " + id);
    }
  }
}
