// IBinderPool.aidl
package com.ligh.blog.ipc;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);  //查找特定Binder的方法
}