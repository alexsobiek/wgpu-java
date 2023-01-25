package com.alexsobiek.wgpu.example.winit;

import com.alexsobiek.wgpu.NativeLibrary;
import com.alexsobiek.wgpu.winit.EventLoop;

import java.io.File;

public class WinitExample {

    public static void main(String[] args) {
        new WinitExample();
    }

    public WinitExample() {
        NativeLibrary.load("rust_wgpu", new File("target"));

        try (EventLoop eventLoop = EventLoop.create()) {
            System.out.println("Created event loop");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
