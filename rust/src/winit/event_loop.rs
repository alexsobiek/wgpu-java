use jni::JNIEnv;
use jni::objects::{JClass, JObject};
use winit::event_loop::EventLoop;

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_com_alexsobiek_wgpu_winit_EventLoop_init0<'a>(
    env: JNIEnv<'a>,
    _class: JClass,
    object: JObject<'a>
)  {
    unsafe {
        env.set_rust_field(object, "handle", Loop::new()).expect("Failed to set field");
    }
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_com_alexsobiek_wgpu_winit_EventLoop_free0<'a>(
    env: JNIEnv<'a>,
    _class: JClass,
    object: JObject<'a>
)  {
    unsafe {
        let _: Loop = env.take_rust_field(object, "handle").expect("Failed to get field");
    }
}

struct Loop {
    event_loop: EventLoop<()>,
}

impl Loop {
    pub fn new() -> Self {
        Self {
            event_loop: EventLoop::new(),
        }
    }

    pub fn get(&self) -> &EventLoop<()> {
        &self.event_loop
    }
}

unsafe impl Send for Loop {}