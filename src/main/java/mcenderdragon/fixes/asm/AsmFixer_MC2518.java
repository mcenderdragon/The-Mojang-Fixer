package mcenderdragon.fixes.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class AsmFixer_MC2518 implements IClassTransformer
{
	//this patches the ENtityLivingBase.class at line 1200

	public final static String method = "renderBrokenItemStack";
	public final static String methodO = "b";
	
	public final static String desc = "(Lnet/minecraft/item/ItemStack;)V";
	public final static String descO = "(Lafj;)V";
	
	public final static String livingbase = "net.minecraft.entity.EntityLivingBase";
//	public final static String livingbaseO = "net.minecraft.entity.EntityLivingBase";
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{	
		if(name.equals(livingbase) || transformedName.equals(livingbase))
		{
			return patchLivingBase(basicClass);
		}
		return basicClass;
	}

	
	public byte[] patchLivingBase(byte[] bytes)
	{
		System.out.println("########################################### Start Patching ##########################################");
		ClassNode node = new ClassNode();
		ClassReader read = new ClassReader(bytes);
		read.accept(node, 0);
		for(MethodNode method : node.methods)
		{	
			System.out.println(method.name + " " + method.desc);
			
			if(method.desc.equals(desc) || method.desc.equals(descO))
			{			
				System.out.println("+################### " + method.name);	
				
				if(method.name.equals(this.method) || method.name.equals(this.methodO))
				{										
					InsnList newStuff = new InsnList();
					newStuff.add(new VarInsnNode(Opcodes.ALOAD, 0)); //this
					newStuff.add(new VarInsnNode(Opcodes.ALOAD, 1)); //param ItemStack  
					//mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "random", "()D", false); -> Math.random()
					newStuff.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "mcenderdragon/fixes/asm/EntityLivingExtension", "preRenderBrokenItemStack", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;)V", false)); //call of method
					method.instructions.insertBefore(method.instructions.getFirst(), newStuff);
					break;
				}
			}
		}
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		node.accept(writer);
		System.out.println("Patched " + livingbase + "#" + method);
		return writer.toByteArray();
	}
}
