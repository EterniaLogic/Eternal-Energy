package com.eternalpower.common.gui;

import com.eternalpower.common.blocks.TileEntityEnderPongReactor;
import com.eternalpower.common.blocks.SlotEnderReactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEnderReactor extends Container
{
	TileEntityEnderPongReactor tileEntity;
	int slotCount;
	public ContainerEnderReactor(InventoryPlayer inventoryPlayer, TileEntityEnderPongReactor tile)
	{
		this.tileEntity=tile;
		
		addSlotToContainer(new SlotEnderReactor(tileEntity, 0, 62, 17));
		addSlotToContainer(new SlotEnderReactor(tileEntity, 1, 62+18, 17));
		addSlotToContainer(new SlotEnderReactor(tileEntity, 2, 62+32, 17));
		
		
		bindPlayerInventory(inventoryPlayer);
	}
	
	// Vanilla inventory slots
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
	}

	@Override
    public boolean canInteractWith(EntityPlayer player) {
            return tileEntity.isUseableByPlayer(player);
    }


	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
            ItemStack stack = null;
            Slot slotObject = (Slot) inventorySlots.get(slot);
            
            if(tileEntity == null) return null;

            //null checks and checks if the item can be stacked (maxStackSize > 1)
            if (slotObject != null && slotObject.getHasStack()) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    //merges the item into player inventory since its in the tileEntity
                    if (slot < tileEntity.getSizeInventory()) {
                            if (!this.mergeItemStack(stackInSlot, tileEntity.getSizeInventory(), 36+tileEntity.getSizeInventory(), true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0, tileEntity.getSizeInventory(), false)) {
                            return null;
                    }

                    if (stackInSlot.stackSize == 0) {
                            slotObject.putStack(null);
                    } else {
                            slotObject.onSlotChanged();
                    }

                    if (stackInSlot.stackSize == stack.stackSize) {
                            return null;
                    }
                    slotObject.onPickupFromSlot(player, stackInSlot);
            }
            return stack;
    }
}
