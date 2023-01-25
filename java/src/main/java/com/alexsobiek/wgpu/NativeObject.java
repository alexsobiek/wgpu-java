package com.alexsobiek.wgpu;

import java.lang.reflect.Method;

public abstract class NativeObject<T extends NativeObject<T>> implements AutoCloseable {
    private long handle;

    public static native <T extends NativeObject<T>> T alloc0(String className);

    public static <T extends NativeObject<T>> T alloc(Class<T> clazz) {
        String className = clazz.getName().replace('.', '/');
        return alloc0(className);
    }

    private void invoke(NativeObject<?> o, String methodName) throws Exception {
        Method m = o.getClass().getDeclaredMethod(methodName, o.getClass());
        m.setAccessible(true);
        m.invoke(o, o);
    }

    private void init(NativeObject<?> o) throws Exception {
        invoke(o, "init0");
    }

    private void free() throws Exception {
        invoke(this, "free0");
    }

    @Override
    public void close() throws Exception {
        this.free();
    }
}
