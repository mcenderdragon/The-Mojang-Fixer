package mcenderdragon.fixes.asm;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;

public class EntityLivingExtension
{
	public static void preRenderBrokenItemStack(EntityLivingBase entity, ItemStack breaking)
	{
		if(!entity.getEntityWorld().isRemote)
		{
			if(entity instanceof EntityPlayerMP)
			{
				EntityPlayerMP mp = (EntityPlayerMP) entity;
				
				mp.connection.sendPacket(new SPacketSoundEffect(SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, mp.posX, mp.posY, mp.posZ, 0.8F, 0.8F + entity.world.rand.nextFloat() * 0.4F));
				
			}
			
		}
	}
}
