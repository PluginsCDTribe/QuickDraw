package com.quickdraw.util;

import org.bukkit.block.BlockFace;

public class Face {
private Face() {}
public static BlockFace getOpposite(BlockFace face) {
	switch(face) {
	case NORTH:
		return BlockFace.SOUTH;
	case SOUTH:
		return BlockFace.NORTH;
	case WEST:
		return BlockFace.EAST;
	case EAST:
		return BlockFace.WEST;
	default:
		return null;
	}
}
public static BlockFace getBetween(BlockFace face) {
	switch(face) {
	case NORTH:
		return BlockFace.EAST;
	case SOUTH:
		return BlockFace.WEST;
	case WEST:
		return BlockFace.SOUTH;
	case EAST:
		return BlockFace.NORTH;
	default:
		return null;
	}
}
public static int getData(BlockFace face) {
	return face.ordinal();
}
}
