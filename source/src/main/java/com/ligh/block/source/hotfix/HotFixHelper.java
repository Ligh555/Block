package com.ligh.block.source.hotfix;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

public class HotFixHelper {
    public static void initHotFix(Context context) {
        File apk = new File(context.getCacheDir() + "/hotfix.dex");

        if (apk.exists()) {
            try {
                ClassLoader originalLoader = context.getClassLoader();
                //定义一个classLoader 加载定义的dex
                DexClassLoader classLoader = new DexClassLoader(apk.getPath(), context.getCacheDir().getPath(), null, null);

                // 获取自定义 classLoader 中的加载的elemets
                Class loaderClass = BaseDexClassLoader.class;
                Field pathListField = loaderClass.getDeclaredField("pathList");
                pathListField.setAccessible(true);
                Object pathListObject = pathListField.get(classLoader);

                Class pathListClass = pathListObject.getClass();
                Field dexElementsField = pathListClass.getDeclaredField("dexElements");
                dexElementsField.setAccessible(true);
                Object dexElementsObject = dexElementsField.get(pathListObject);

                // 获取原来的 classLoader 中的加载的elemets
                Object originalPathListObject = pathListField.get(originalLoader);
                Object originalDexElementsObject = dexElementsField.get(originalPathListObject);

                int oldLength = Array.getLength(originalDexElementsObject);
                int newLength = Array.getLength(dexElementsObject);
                //合并新旧element 元素
                Object concatDexElementsObject = Array.newInstance(dexElementsObject.getClass().getComponentType(), oldLength + newLength);
                for (int i = 0; i < newLength; i++) {
                    Array.set(concatDexElementsObject, i, Array.get(dexElementsObject, i));
                }
                for (int i = 0; i < oldLength; i++) {
                    Array.set(concatDexElementsObject, newLength + i, Array.get(originalDexElementsObject, i));
                }
                dexElementsField.set(originalPathListObject, concatDexElementsObject);

                // originalLoader.pathList.dexElements = classLoader.pathList.dexElements;
                // originalLoader.pathList.dexElements += classLoader.pathList.dexElements;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
