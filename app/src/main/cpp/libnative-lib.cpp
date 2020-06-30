#include <jni.h>
#include <string>

std::string getData(int x) {
    std::string app_secret = "Null";

    if (x == 1) app_secret = "e2ec78d289f46a3eccae1b4d7bc74a5f"; //The first key that you want to protect against decompilation

    return app_secret;
}


extern "C"
JNIEXPORT jstring

JNICALL
Java_com_damianopatane_weatherapplication_helpers_Keys_apiKey(
        JNIEnv *env,
        jobject /* this */,
        jint x) {
    std::string app_secret = "Null";
    app_secret = getData(x);
    return env->NewStringUTF(app_secret.c_str());
}