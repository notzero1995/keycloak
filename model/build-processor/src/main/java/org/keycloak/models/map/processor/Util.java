/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
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
package org.keycloak.models.map.processor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor8;

/**
 *
 * @author hmlnarik
 */
public class Util {

    private static final HashSet<String> SET_TYPES = new HashSet<>(Arrays.asList(Set.class.getCanonicalName(), TreeSet.class.getCanonicalName(), HashSet.class.getCanonicalName(), LinkedHashSet.class.getCanonicalName()));

    public static List<TypeMirror> getGenericsDeclaration(TypeMirror fieldType) {
        List<TypeMirror> res = new LinkedList<>();

        fieldType.accept(new SimpleTypeVisitor8<Void, List<TypeMirror>>() {
            @Override
            public Void visitDeclared(DeclaredType t, List<TypeMirror> p) {
                List<? extends TypeMirror> typeArguments = t.getTypeArguments();
                res.addAll(typeArguments);
                return null;
            }
        }, res);

        return res;
    }

    public static String methodParameters(List<? extends VariableElement> parameters) {
        return parameters.stream()
          .map(p -> p.asType() + " " + p.getSimpleName())
          .collect(Collectors.joining(", "));
    }

    public static boolean isSetType(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        return SET_TYPES.contains(name.toString());
    }

}
