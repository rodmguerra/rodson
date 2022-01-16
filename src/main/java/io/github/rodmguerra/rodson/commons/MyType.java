package io.github.rodmguerra.rodson.commons;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class MyType<T> {
    private final Type type;

    protected MyType() {
        this.type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private MyType(Type type) {
        this.type = type;
    }


    public Class<T> getRawClass() {
        //Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return ReflectionUtils.rawClass(type);
    }
    public List<MyType> getGenericParams() {
        if(!(type instanceof ParameterizedType)) return new ArrayList();
        Type[] rGenericParams = ((ParameterizedType) type).getActualTypeArguments();
        List<MyType> genericParams = new ArrayList<>();
        for (Type rGenericParam : rGenericParams) {
            genericParams.add(new MyType(rGenericParam){});
        }
        return genericParams;
    }

    @Override
    public String toString() {
        List<MyType> params = getGenericParams();
        return getRawClass() + (params.size() > 0? "<" + params + ">" : "");

    }

    public static void main(String[] args) {
        MyType<List<String>> type = new MyType<List<String>>(){};
        System.out.println(type);
    }
}
