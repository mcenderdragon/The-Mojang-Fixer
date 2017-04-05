package mcenderdragon.fixes.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("This Coremod Fixed MC-2518")
public class PluginFix_MC2518 implements IFMLLoadingPlugin
{

	@Override
	public String[] getASMTransformerClass()
	{
		System.out.println("############################################## Geting AMS Class ############################################");
		
		return new String[]{AsmFixer_MC2518.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

	
}
