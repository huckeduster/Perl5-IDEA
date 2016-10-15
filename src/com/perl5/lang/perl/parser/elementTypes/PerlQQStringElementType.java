/*
 * Copyright 2016 Alexandr Evstigneev
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

package com.perl5.lang.perl.parser.elementTypes;

import com.intellij.lang.ASTNode;
import com.intellij.lexer.FlexAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.perl5.lang.perl.lexer.PerlLexer;
import com.perl5.lang.perl.lexer.adapters.PerlSubLexerAdapter;
import com.perl5.lang.perl.psi.impl.PerlParsableStringWrapperlImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 10.09.2015.
 */
public class PerlQQStringElementType extends PerlParsableStringElementType implements PsiElementProvider
{
	public PerlQQStringElementType(String name)
	{
		super(name);
	}

	@Override
	protected FlexAdapter getLexerAdapter(Project project)
	{
		return new PerlSubLexerAdapter(PerlLexer.LEX_STRING_CONTENT_QQ);
	}

	@NotNull
	@Override
	public PsiElement getPsiElement(@NotNull ASTNode node)
	{
		return new PerlParsableStringWrapperlImpl(node);
	}
}
