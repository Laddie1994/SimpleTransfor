package com.yuchuan.plugin;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public class TestMethodVisitor extends LocalVariablesSorter implements Opcodes{

    private String methodName;

    protected TestMethodVisitor(String name, int access, String desc, MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, access, desc, methodVisitor);
        this.methodName = name.replace("/", ".");
    }

    @Override
    public void visitCode() {
        super.visitCode();
    }

//    LDC "TAG"
//    NEW java/lang/StringBuilder
//    DUP
//    INVOKESPECIAL java/lang/StringBuilder.<init> ()V
//    LDC "message"
//    INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    ALOAD 0
//    GETFIELD com/motoband/Test.methodName : Ljava/lang/String;
//    INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    INVOKEVIRTUAL java/lang/StringBuilder.toString ()Ljava/lang/String;
//    INVOKESTATIC android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
//    POP

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitLdcInsn("TAG");
            int sbIndex = newLocal(Type.getObjectType("java/lang/StringBuilder"));
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitLdcInsn("methodName: ");
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
            mv.visitVarInsn(ASTORE, sbIndex);
            mv.visitVarInsn(ALOAD, sbIndex);
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
        }
        super.visitInsn(opcode);
    }


}
