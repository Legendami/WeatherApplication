cmake_minimum_required(VERSION 3.10.2)

add_library(
        native-lib
        SHARED
        cpp/libnative-lib.cpp
)

include_directories(src/main/cpp/include/)