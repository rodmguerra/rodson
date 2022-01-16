package io.github.rodmguerra.rodson.commons;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class Type<T> {
    private final java.lang.reflect.Type type;

    protected Type() {
        this.type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Type(java.lang.reflect.Type type) {
        this.type = type;
    }

    public static <T> Type<T> of(java.lang.reflect.Type type) {
        return new Type<T>(type) {};
    }

    public Class<T> getRawClass() {
        return ReflectionUtils.rawClass(type);
    }
    public List<Type> getGenericParams() {
        if(!(type instanceof ParameterizedType)) return new ArrayList();
        java.lang.reflect.Type[] rGenericParams = ((ParameterizedType) type).getActualTypeArguments();
        List<Type> genericParams = new ArrayList<>();
        for (java.lang.reflect.Type rGenericParam : rGenericParams) {
            genericParams.add(new Type(rGenericParam){});
        }
        return genericParams;
    }

    @Override
    public String toString() {
        List<Type> params = getGenericParams();
        return "Type{" + getRawClass() + (params.size() > 0? "<" + params + ">" : "") + "}";

    }

    public static void main(String[] args) {
        Type<List<String>> type = new Type<List<String>>(){};
        System.out.println(type);
    }
}

/*package com.rodmguerra.rodson.commons;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Type<T> {
    private final Class rawClass;
    private final List<Type> params;

    public Type(Class rawClass, Type... params) {
        this.rawClass = rawClass;
        this.params = Arrays.asList(params);
    }
    public static <T> Type of(Class clazz) {
        return new Type(clazz);
    }

    public static <T> Type of(java.lang.reflect.Type type) {
        if(type instanceof Class) return Type.of(type);
        else if(type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Class<T> clazz = (Class) pType.getRawType();
            List<Type> generics = new ArrayList<>();
            for (java.lang.reflect.Type generic : pType.getActualTypeArguments()) {
                generics.add(Type.of(generic));
            }
            return Type.of(clazz, generics);
        } else throw new UnsupportedOperationException(String.format("%s is not supported", type.getClass()));
    }

    public static <T> Type of(Class clazz, Type param) {
        return new Type(clazz, param);
    }

    public static <T> Type of(Class clazz, Class param) {
        return new Type<>(clazz, Type.of(param));
    }

    public static <T> Type of(Class clazz, Class param, Type... params) {
        List<Type> generics = new ArrayList<>(params.length + 1);
        generics.add(Type.of(param));
        for (Type aParam : params) {
            generics.add(aParam);
        }
        return new Type(clazz, generics.toArray(new Type[0]));
    }

    public static <T> Type of(Class clazz, Type param, Class... params) {
        List<Type> typeParams = new ArrayList<>(params.length + 1);
        typeParams.add(param);
        for (Class aParam : params) {
            typeParams.add(Type.of(aParam));
        }
        return new Type(clazz, typeParams.toArray(new Type[0]));
    }

    public static <T> Type of(Class clazz, Class param, Class... params) {
        List<Type> typeParams = new ArrayList<>(params.length + 1);
        typeParams.add(Type.of(param));
        for (Class aParam : params) {
            typeParams.add(Type.of(aParam));
        }
        return new Type(clazz, typeParams.toArray(new Type[0]));
    }


    public static <T> Type of(Class<T> clazz, Type param, Type... params) {
        List<Type> typeParams = new ArrayList<>(params.length + 1);
        typeParams.add(param);
        for (Type aParam : params) {
            typeParams.add(aParam);
        }
        return new Type(clazz, typeParams.toArray(new Type[0]));
    }

    public static <T> Type of(Class<T> clazz, List<Type> params) {
        return new Type(clazz, params.toArray(new Type[0]));
    }


    @Override
    public String toString() {
        return rawClass + (params.size() > 0? "<" + params + ">" : "");
    }

    public Class getRawClass() {
        return rawClass;
    }

    public Type[] getGenericParams() {
        return params.toArray(new Type[0]);
    }

}
*/
