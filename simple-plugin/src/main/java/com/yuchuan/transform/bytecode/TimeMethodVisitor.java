package com.yuchuan.transform.bytecode;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;


public class TimeMethodVisitor extends LocalVariablesSorter implements Opcodes {

    private int startTimeIndex;
    private String className;
    private String methodName;
    private boolean isPrintMethod;

    public TimeMethodVisitor(String className, String name, int access, String descriptor, MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, access, descriptor, methodVisitor);
        this.className = className.substring(className.lastIndexOf("/") + 1);
        this.methodName = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor visitor = super.visitAnnotation(descriptor, visible);
        isPrintMethod = descriptor.equals("Lcom/yuchuan/library/PrintTime;");
        return visitor;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        startTimeIndex = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, startTimeIndex);
    }

    @Override
    public void visitInsn(int opcode) {
        if (((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) && isPrintMethod) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, startTimeIndex);
            mv.visitInsn(LSUB);
            int costedIndex = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, costedIndex);
            mv.visitLdcInsn(className);
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitLdcInsn("method: ");
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("; costed: ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(LLOAD, costedIndex);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
        }
        super.visitInsn(opcode);
    }
}
