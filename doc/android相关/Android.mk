# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)
$(call import-add-path, D:\work\code\android-sdk\NDK\crystax-ndk-10.2.1)

include $(CLEAR_VARS)

LOCAL_MODULE    := hello-jni
LOCAL_SRC_FILES := hello-jni.c xlsys/algorithm/DES.cpp xlsys/io/IOUtil.cpp xlsys/io/JNIUtil.cpp xlsys/io/xlsys_io_util_IOUtil.cpp xlsys/util/EDCoder.cpp xlsys/util/xlsys_util_EDCoder.cpp
LOCAL_LDLIBS    := -lz
LOCAL_STATIC_LIBRARIES := boost_iostreams_static boost_zlib_static boost_date_time_static boost_system_static boost_regex_static boost_random_static

include $(BUILD_SHARED_LIBRARY)

$(call import-module,sources/boost/1.57.0)