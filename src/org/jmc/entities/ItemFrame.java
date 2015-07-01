package org.jmc.entities;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.jmc.BlockMaterial;
import org.jmc.FilledMapDat;
import org.jmc.OBJOutputFile;
import org.jmc.Options;
import org.jmc.NBT.TAG_Byte;
import org.jmc.NBT.TAG_Byte_Array;
import org.jmc.NBT.TAG_Compound;
import org.jmc.NBT.TAG_Float;
import org.jmc.NBT.TAG_Int;
import org.jmc.NBT.TAG_List;
import org.jmc.NBT.TAG_String;
import org.jmc.NBT.TAG_Short;
import org.jmc.geom.Transform;
import org.jmc.util.Log;
import org.jmc.util.Messages;


/**
 * Model for paintings.
 * TODO correctly render paintings larger than 1x1
 */
public class ItemFrame extends Entity
{
	@Override
	public void addEntity(OBJOutputFile obj, TAG_Compound entity)
	{
		
		int x=((TAG_Int)entity.getElement("TileX")).value;
		int y=((TAG_Int)entity.getElement("TileY")).value;
		int z=((TAG_Int)entity.getElement("TileZ")).value;
		
		TAG_Compound item = ((TAG_Compound)entity.getElement("Item"));

		String item_id = ((TAG_String)item.getElement("id")).value;
		
		Transform rotate = new Transform();
		Transform translate = new Transform();
		Transform rt;
		
		translate.translate(x, y, z);		
			
		rt = translate.multiply(rotate);
		
		BlockMaterial materials=new BlockMaterial();
		
		switch (item_id)
		{
			case "minecraft:filled_map":
				
				short map_id = ((TAG_Short)item.getElement("Damage")).value;
				Log.info("Found map with id: '" + map_id+ "'");
				String [] matname={"map_" + map_id + "_item_frame"};
				materials.put((byte) 0, matname );
				
				
				FilledMapDat map_data = new FilledMapDat(Options.worldDir);
				if (!map_data.open(String.valueOf(map_id))) {
					Log.info("'map_" + map_id+ ".dat' not found");
					return;
				}
				else {
					Log.info("MAP:" + map_data);
					
					try {
						map_data.writePngTexture();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Log.error("Cant write map", e, true);
					}
					
						
				}
				
				
				
				
				
				break;
			default:
				Log.info("Unsupported FrameItem: '" + item_id + "'");
				String [] matname1={"item_frame"};
				materials.put((byte) 0, matname1 );
				break;
		}
		model.setMaterials(materials);
		model.addEntity(obj, rt);			
	
		
			
	}

}
