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
package jp.takawitter.s3j.generator;

import static org.slim3.gen.ClassConstants.Object;

import org.slim3.gen.datastore.CorePrimitiveType;
import org.slim3.gen.datastore.CoreReferenceType;
import org.slim3.gen.datastore.DataType;
import org.slim3.gen.datastore.KeyType;
import org.slim3.gen.datastore.PrimitiveBooleanType;
import org.slim3.gen.datastore.PrimitiveDoubleType;
import org.slim3.gen.datastore.PrimitiveFloatType;
import org.slim3.gen.datastore.PrimitiveIntType;
import org.slim3.gen.datastore.PrimitiveLongType;
import org.slim3.gen.datastore.PrimitiveShortType;
import org.slim3.gen.datastore.SimpleDataTypeVisitor;
import org.slim3.gen.datastore.StringType;
import org.slim3.gen.desc.AttributeMetaDesc;
import org.slim3.gen.desc.ModelMetaDesc;
import org.slim3.gen.generator.ModelMetaGenerator;
import org.slim3.gen.printer.Printer;

/**
 * @author Takao Nakaguchi
 */
public class S3JModelMetaGenerator extends ModelMetaGenerator {
	/**
	 * @param modelMetaDesc
	 */
	public S3JModelMetaGenerator(ModelMetaDesc modelMetaDesc) {
		super(modelMetaDesc);
	}

	@Override
	protected void printConstructor(Printer printer) {
		super.printConstructor(printer);
		printer.unindent();
		printer.println();
		printer.indent();
        new ModelToJsonMethodGenerator(printer).generate();
        new JsonToModelMethodGenerator(printer).generate();
	}

    /**
     * @author Takao Nakaguchi
     */
    private class ModelToJsonMethodGenerator extends
            SimpleDataTypeVisitor<Void, AttributeMetaDesc, RuntimeException> {
        /**
         * Creates a new {@link ModelToJsonMethodGenerator}.
         * @param printer the printer
         */
        public ModelToJsonMethodGenerator(Printer printer) {
            this.printer = printer;
        }

        /**
         * Generates the modelToJson method.
         */
        public void generate() {
            //printer.println("@Override");
            printer.println(
                "public String modelToJson(%s model) {",
                Object);
            printer.indent();
            if (modelMetaDesc.isAbstrct()) {
                printer.println(
                    "throw new %1$s(\"The class(%2$s) is abstract.\");",
                    UnsupportedOperationException.class.getName(),
                    modelMetaDesc.getModelClassName());
            } else {
                printer.println("%1$s m = (%1$s) model;", modelMetaDesc.getModelClassName());
                printer.println("StringBuilder b = new StringBuilder(\"{\");");
                boolean firstProp = true;
                for (AttributeMetaDesc attr : modelMetaDesc.getAttributeMetaDescList()) {
                    DataType dataType = attr.getDataType();
                    if(firstProp){
                    	firstProp = false;
                    } else{
                    	printer.println("b.append(\",\");");
                    }
                    dataType.accept(this, attr);
                }
                printer.println("b.append(\"}\");");
                printer.println("return b.toString();");
            }
            printer.unindent();
            printer.println("}");
            printer.println();
        }

        @Override
        public Void visitKeyType(KeyType type, AttributeMetaDesc p)
        		throws RuntimeException {
            printer.println("//KeyType");
            printer.println("if(m.%s() != null){", p.getReadMethodName());
            printer.indent();
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":\\\"\").append(" +
                    "com.google.appengine.api.datastore.KeyFactory.keyToString(m.%2$s())" +
                    ").append(\"\\\"\");",
                    p.getAttributeName(),
                    p.getReadMethodName());
        	printer.unindent();
        	printer.println("} else{");
            printer.indent();
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":null\");",
        			p.getAttributeName()
        			);
        	printer.unindent();
        	printer.println("}");
            return null;
        }

        @Override
        public Void visitCorePrimitiveType(CorePrimitiveType type,
                AttributeMetaDesc p) throws RuntimeException {
            printer.println("//CorePrimitiveType");
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":\").append(m.%2$s());",
                    p.getName(),
                    p.getReadMethodName());
            return null;
        }

        @Override
        public Void visitCoreReferenceType(CoreReferenceType type,
                AttributeMetaDesc p) throws RuntimeException {
            printer.println("//CoreReferenceType");
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":\").append(m.%2$s());",
                    p.getName(),
                    p.getReadMethodName());
            return null;
        }

        @Override
        public Void visitStringType(StringType type, AttributeMetaDesc p)
        throws RuntimeException {
            printer.println("// StringType");
            printer.println("if(m.%s() != null){", p.getReadMethodName());
            printer.indent();
            String getMethodCall = "m.%2$s()";
            if(p.isCipher()){
            	getMethodCall = "encrypt(" + getMethodCall + ")";
            }
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":\\\"\").append(" + getMethodCall + ").append(\"\\\"\");",
                    p.getName(),
                    p.getReadMethodName());
        	printer.unindent();
        	printer.println("} else{");
            printer.indent();
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":null\");",
        			p.getName()
        			);
        	printer.unindent();
        	printer.println("}");
            return null;
        }

        private final Printer printer;
    }

    /**
     * @author Takao Nakaguchi
     */
    private class JsonToModelMethodGenerator extends
            SimpleDataTypeVisitor<Void, AttributeMetaDesc, RuntimeException> {
        /**
         * Creates a new {@link JsonToModelMethodGenerator}.
         * @param printer the printer
         */
        public JsonToModelMethodGenerator(Printer printer) {
            this.printer = printer;
        }

        /**
         * Generates the modelToJson method.
         */
        public void generate() {
            //printer.println("@Override");
            printer.println(
                "public %s jsonToModel(String json) {",
                modelMetaDesc.getModelClassName());
            printer.indent();
            if (modelMetaDesc.isAbstrct()) {
                printer.println(
                    "throw new %1$s(\"The class(%2$s) is abstract.\");",
                    UnsupportedOperationException.class.getName(),
                    modelMetaDesc.getModelClassName());
            } else {
                printer.println("%1$s m = new %1$s();", modelMetaDesc.getModelClassName());
                printer.println("java.util.Map<?, ?> map = net.arnx.jsonic.JSON.decode(json);");
                for (AttributeMetaDesc attr : modelMetaDesc.getAttributeMetaDescList()) {
                    DataType dataType = attr.getDataType();
                    dataType.accept(this, attr);
                }
                printer.println("return m;");
            }
            printer.unindent();
            printer.println("}");
            printer.println();
        }

        @Override
        public Void visitKeyType(KeyType type, AttributeMetaDesc p)
        		throws RuntimeException {
            printer.println("// KeyType");
            printer.println("if(map.get(\"%s\") != null){", p.getAttributeName());
            printer.indent();
        	printer.println(
                    "m.%1$s(com.google.appengine.api.datastore.KeyFactory.stringToKey(" +
                    "map.get(\"%2$s\").toString()" +
                    "));",
                    p.getWriteMethodName(),
                    p.getAttributeName());
        	printer.unindent();
        	printer.println("} else{");
            printer.indent();
        	printer.println(
                    "m.%1$s(null);",
        			p.getWriteMethodName()
        			);
        	printer.unindent();
        	printer.println("}");
            return null;
        }

        @Override
        public Void visitPrimitiveBooleanType(PrimitiveBooleanType type,
        		AttributeMetaDesc p) throws RuntimeException {
            printer.println("// PrimitiveBooleanType");
            visitPrimitiveType(type, p, "Boolean.parseBoolean");
        	return null;
        }

        @Override
        public Void visitPrimitiveShortType(PrimitiveShortType type,
        		AttributeMetaDesc p) throws RuntimeException {
            printer.println("// PrimitiveShortType");
            visitPrimitiveType(type, p, "Short.parseShort");
        	return null;
        }

        @Override
        public Void visitPrimitiveIntType(PrimitiveIntType type,
        		AttributeMetaDesc p) throws RuntimeException {
            printer.println("// PrimitiveIntType");
            visitPrimitiveType(type, p, "Integer.parseInt");
        	return null;
        }

        @Override
        public Void visitPrimitiveLongType(PrimitiveLongType type,
        		AttributeMetaDesc p) throws RuntimeException {
            printer.println("// PrimitiveLongType");
            visitPrimitiveType(type, p, "Long.parseLong");
        	return null;
        }

        @Override
        public Void visitPrimitiveFloatType(PrimitiveFloatType type,
        		AttributeMetaDesc p) throws RuntimeException {
            printer.println("// PrimitiveFloatType");
            visitPrimitiveType(type, p, "Float.parseFloat");
        	return null;
        }

        @Override
        public Void visitPrimitiveDoubleType(PrimitiveDoubleType type,
        		AttributeMetaDesc p) throws RuntimeException {
            printer.println("// PrimitiveDoubleType");
            visitPrimitiveType(type, p, "Double.parseDouble");
        	return null;
        }

        private void visitPrimitiveType(CorePrimitiveType type,
        		AttributeMetaDesc p, String parseMethod){
            printer.println("if(map.get(\"%s\") != null){", p.getName());
            printer.indent();
        	printer.println(
                    "m.%1$s(%2$s(map.get(\"%3$s\").toString()));",
                    p.getWriteMethodName(),
                    parseMethod,
                    p.getName());
        	printer.unindent();
        	printer.println("}");
        }

        @Override
        public Void visitCoreReferenceType(CoreReferenceType type,
                AttributeMetaDesc p) throws RuntimeException {
            printer.println("//CoreReferenceType");
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":\").append(m.%2$s());",
                    p.getName(),
                    p.getReadMethodName());
            return null;
        }

        @Override
        public Void visitStringType(StringType type, AttributeMetaDesc p)
        throws RuntimeException {
            printer.println("// StringType");
            printer.println("if(map.get(\"%s\") != null){", p.getName());
            printer.indent();
            String getMethodCall = "map.get(\"%2$s\").toString()";
            if(p.isCipher()){
            	getMethodCall = "decrypt(" + getMethodCall + ")";
            }
        	printer.println(
                    "m.%1$s(" + getMethodCall + ");",
                    p.getWriteMethodName(),
                    p.getName());
        	printer.unindent();
        	printer.println("} else{");
            printer.indent();
        	printer.println(
                    "m.%1$s(null);",
        			p.getWriteMethodName()
        			);
        	printer.unindent();
        	printer.println("}");
            return null;
        }

        private final Printer printer;
    }
}
