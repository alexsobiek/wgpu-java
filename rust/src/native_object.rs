use jni::JNIEnv;
use jni::objects::{JClass, JObject, JString, JValue};
use jni::sys::jlong;

pub fn get_handle(env: &JNIEnv, object: JObject) -> jni::errors::Result<jlong> {
    env.get_field(object, "handle", "J")?.j()
}

pub fn handle_err<T>(env: JNIEnv, ret: jni::errors::Result<T>, msg: &str) -> Option<T> {
    match ret {
        Ok(val) => Some(val),
        Err(_) => {
            eprintln!("JNI error: {}", msg);
            env.exception_describe().expect("Failed to print exception");
            None
        }
    }
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_com_alexsobiek_wgpu_NativeObject_alloc0<'a>(
    env: JNIEnv<'a>,
    _class: JClass,
    class_name: JString,
) -> JObject<'a> {
    match handle_err(env, env.get_string(class_name), "Failed to get class name") {
        Some(cn) => {
            let class_name: String = cn.into();
            match handle_err(env, env.alloc_object(class_name), "Failed to allocate object") {
                Some(obj) => {
                    handle_err(env,
                               env.call_method(obj,
                                               "init",
                                               "(Lcom/alexsobiek/wgpu/NativeObject;)V",
                                               &[JValue::from(obj)]),
                               "Failed to call init");
                    return obj;
                }
                None => JObject::null()
            }
        }
        None => JObject::null()
    }
}