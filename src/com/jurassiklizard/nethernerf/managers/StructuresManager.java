package com.jurassiklizard.nethernerf.managers;

import org.bukkit.Location;
import org.bukkit.World;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StructuresManager {

    public static Location getStructure(World w, String structure) throws ClassNotFoundException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Location l = w.getSpawnLocation();

        Class<?> structureClass = NMSManager.getNMSClass("StructureGenerator");
        Class<?> blockPositionClass = NMSManager.getNMSClass("BlockPosition");

        Method getHandle = l.getWorld().getClass().getMethod("getHandle");
        Object nmsWorld = getHandle.invoke(l.getWorld());

        Class<?>[] aClass = new Class[] { structureClass, blockPositionClass, int.class, boolean.class };
        Object[] aParameters = new Object[] {structureClass.getField(structure).get(null), getBlockPosition(l), 100, false};

        Object blockPosition = nmsWorld.getClass().getMethod("a", aClass).invoke(nmsWorld, aParameters);

        return BlockPosToLocation(blockPosition, l.getWorld());
    }

    private static Object getBlockPosition(Location loc) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException {
        Class<?> nmsBlockPositionClass = NMSManager.getNMSClass("BlockPosition");
        Object nmsBlockPositionInstance = nmsBlockPositionClass
                .getConstructor(new Class[] { Double.TYPE, Double.TYPE, Double.TYPE })
                .newInstance(loc.getX(), loc.getY(), loc.getZ());
        return nmsBlockPositionInstance;
    }

    private static Location BlockPosToLocation(Object blockPos, World world) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int x = Integer.parseInt(blockPos.getClass().getMethod("getX").invoke(blockPos).toString());
        int y = Integer.parseInt(blockPos.getClass().getMethod("getY").invoke(blockPos).toString());
        int z = Integer.parseInt(blockPos.getClass().getMethod("getZ").invoke(blockPos).toString());
        return new Location(world, x, y, z);
    }
}
