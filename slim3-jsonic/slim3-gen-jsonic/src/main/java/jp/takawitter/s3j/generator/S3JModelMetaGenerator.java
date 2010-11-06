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

import java.util.HashMap;
import java.util.Map;

import org.slim3.gen.datastore.AbstractDataType;
import org.slim3.gen.datastore.CollectionType;
import org.slim3.gen.datastore.CorePrimitiveType;
import org.slim3.gen.datastore.CoreReferenceType;
import org.slim3.gen.datastore.DataType;
import org.slim3.gen.datastore.KeyType;
import org.slim3.gen.datastore.ListType;
import org.slim3.gen.datastore.SetType;
import org.slim3.gen.datastore.SimpleDataTypeVisitor;
import org.slim3.gen.datastore.SortedSetType;
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
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":\").append(m.%2$s());",
                    p.getName(),
                    p.getReadMethodName());
            return null;
        }

        @Override
        public Void visitCoreReferenceType(CoreReferenceType type,
                AttributeMetaDesc p) throws RuntimeException {
        	printer.println(
                    "b.append(\"\\\"%1$s\\\":\").append(m.%2$s());",
                    p.getName(),
                    p.getReadMethodName());
            return null;
        }

        @Override
        public Void visitStringType(StringType type, AttributeMetaDesc p)
        throws RuntimeException {
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

        @Override
        public Void visitCollectionType(CollectionType type, AttributeMetaDesc p)
        throws RuntimeException {
            printer.println("if(m.%s() != null){", p.getReadMethodName());
            printer.indent();
            type.getElementType().accept(new SimpleDataTypeVisitor<Void, AttributeMetaDesc, RuntimeException>(){
            	@Override
            	public Void visitCoreReferenceType(CoreReferenceType type,
            			AttributeMetaDesc p) throws RuntimeException {
                	printer.println(
                            "b.append(\"\\\"%1$s\\\":\");",
                			p.getName()
                            );
                	printer.println("b.append(\"[\");");
                	printer.println("boolean first = true;");
            		printer.println("for(Object v : m.%s()){", p.getReadMethodName());
            		printer.indent();
                	printer.println("if(first) first = false;");
                	printer.println("else b.append(\",\");");
                	printer.println("b.append(v);");
            		printer.unindent();
            		printer.println("}");
                	printer.println("b.append(\"]\");");
            		return null;
            	}
			}, p);
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
        public Void visitCorePrimitiveType(CorePrimitiveType type,
        		AttributeMetaDesc p) throws RuntimeException {
            visitNumberType(type, p, type.getWrapperClassName()
            		+ "." + classToParseMethod.get(type.getWrapperClassName()));
        	return null;
        }

        @Override
        public Void visitCoreReferenceType(CoreReferenceType type,
        		AttributeMetaDesc p) throws RuntimeException {
        	visitNumberType(type, p, type.getTypeName() + ".valueOf");
        	return null;
        }

        @Override
        public Void visitStringType(StringType type, AttributeMetaDesc p)
        throws RuntimeException {
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

        @Override
        public Void visitListType(ListType type, AttributeMetaDesc p)
        throws RuntimeException {
            printer.println("if(map.get(\"%s\") != null){", p.getName());
            printer.indent();
            printer.println(
            		"java.util.List<%s> list = new java.util.ArrayList<%1$s>();"
            		, type.getElementType().getClassName()
            		);
            printer.println("for(Object v : (java.util.List<?>)map.get(\"%s\")){"
            		, p.getName());
            printer.indent();
        	printer.println("list.add(%s.%s(v.toString()));"
        			, type.getElementType().getClassName()
        			, classToParseMethod.get(type.getElementType().getClassName())
        			);
            printer.unindent();
        	printer.println("}");
        	printer.println(
        			"m.%1$s(list);",
        			p.getWriteMethodName()
        			);
        	printer.unindent();
        	printer.println("}");
        	return null;
        }

        @Override
        public Void visitSortedSetType(SortedSetType type, AttributeMetaDesc p)
		throws RuntimeException {
        	return visitSetType(type, p, "java.util.TreeSet");
        }

        @Override
        public Void visitSetType(SetType type, AttributeMetaDesc p)
        throws RuntimeException {
        	return visitSetType(type, p, "java.util.HashSet");
        }

        private Void visitSetType(CollectionType type, AttributeMetaDesc p
        		, String setClass)
        throws RuntimeException {
            printer.println("if(map.get(\"%s\") != null){", p.getName());
            printer.indent();
            printer.println(
            		"%s<%s> set = new %1$s<%2$s>();"
            		, setClass
            		, type.getElementType().getClassName()
            		);
            printer.println("for(Object v : (java.util.List<?>)map.get(\"%s\")){"
            		, p.getName());
            printer.indent();
        	printer.println("set.add(%s.%s(v.toString()));"
        			, type.getElementType().getClassName()
        			, classToParseMethod.get(type.getElementType().getClassName())
        			);
            printer.unindent();
        	printer.println("}");
        	printer.println(
        			"m.%1$s(set);",
        			p.getWriteMethodName()
        			);
        	printer.unindent();
        	printer.println("}");
        	return null;
        }

        private void visitNumberType(AbstractDataType type,
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

        private final Printer printer;
    }

    private static Map<String, String> classToParseMethod = new HashMap<String, String>();
    private static Map<String, String> classToValueMethod = new HashMap<String, String>();
    static{
    	classToParseMethod.put("java.lang.Boolean", "parseBoolean");
    	classToParseMethod.put("java.lang.Short", "parseShort");
    	classToParseMethod.put("java.lang.Integer", "parseInt");
    	classToParseMethod.put("java.lang.Long", "parseLong");
    	classToParseMethod.put("java.lang.Float", "parseFloat");
    	classToParseMethod.put("java.lang.Double", "parseDouble");
    	classToValueMethod.put("java.lang.Short", "shortValue");
    	classToValueMethod.put("java.lang.Integer", "intValue");
    	classToValueMethod.put("java.lang.Long", "longValue");
    	classToValueMethod.put("java.lang.Float", "floatValue");
    	classToValueMethod.put("java.lang.Double", "doubleValue");
    }
}
