package com.alexsobiek.wgpu.winit;

import com.alexsobiek.wgpu.NativeObject;
import jdk.jfr.Event;

public class EventLoop extends NativeObject<EventLoop> {
    // private native void init0(EventLoop e);

    private native void free0(EventLoop e);

    private native void init0(EventLoop e);

    public static EventLoop create() {
        return NativeObject.alloc(EventLoop.class);
    }
}
