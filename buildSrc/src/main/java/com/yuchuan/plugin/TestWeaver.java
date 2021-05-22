package com.yuchuan.plugin;

import com.yuchuan.plugin.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class TestWeaver extends BaseWeaver {

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        System.out.println(fullQualifiedClassName);
        return super.isWeavableClass(fullQualifiedClassName) &&
                fullQualifiedClassName.startsWith("com.yuchuan.transform.MainActivity");
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new TestClassVisitor(classWriter);
    }
}
