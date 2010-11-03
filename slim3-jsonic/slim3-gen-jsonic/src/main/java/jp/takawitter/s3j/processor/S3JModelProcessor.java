/*
 * Copyright 2010 Takao Nakaguchi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.takawitter.s3j.processor;

import java.util.Set;

import jp.takawitter.s3j.generator.S3JModelMetaGenerator;

import org.slim3.gen.desc.ModelMetaDesc;
import org.slim3.gen.generator.ModelMetaGenerator;
import org.slim3.gen.processor.ModelProcessor;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;


/**
 * @author Takao Nakaguchi
 */
public class S3JModelProcessor extends ModelProcessor {
	public S3JModelProcessor(
			Set<AnnotationTypeDeclaration> annotationTypeDeclarations,
			AnnotationProcessorEnvironment env) {
		super(annotationTypeDeclarations, env);
	}

	@Override
	protected ModelMetaGenerator createModelMetaGenerator(
			ModelMetaDesc modelMetaDesc) {
		return new S3JModelMetaGenerator(modelMetaDesc);
	}
}
