package com.eternalpower.util;

import java.io.Serializable;
import java.util.Iterator;
// This is a companion class for the EnderPongReactor
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.eternalpower.EternalPower;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;



public class EnderPongPacket implements net.minecraftforge.common.capabilities.ICapabilitySerializable<NBTTagCompound> {
	private int enderPearls, SlowFactor,EndermanDamage; // Number of loaded Ender Pearls
	private LargeContainer mass; // Loaded mass in this packet
	private double energyBarrier, energyBarrierMax, distance, position, velocity, impulseRF; // energy barrier shield level for this packet
	private boolean ready, sent, returning, done, waiting, impulsedone;
	private Random rand;
	
	
	public EnderPongPacket(int pearls, LargeContainer container, double barrier, double distance){
		this.enderPearls = pearls;
		this.mass = container; // Actual mass that is being transmitted
		this.energyBarrierMax = barrier; // Defensive shell for the packet, sacrificial protection (Uses some energy, but not a lot)
		this.distance = distance;
		
		this.position = 0; // initial X position = 0
		this.velocity = 0; // initial velocity = 0
		this.impulseRF = 0;
		
		this.ready = false;
		this.sent = false;
		
		this.returning = false; // return trip?
		this.done = false; // is the packet done?
		this.waiting = false;
		this.impulsedone = false;
		
		this.SlowFactor = 10; // slow factor for this packet (Exponential)
		this.EndermanDamage = 7; 
		
		this.rand = new Random(System.currentTimeMillis()+283);
	}
	
	// Recommend the RF for precharging
	public double recommendPreRF(double BlockRF){
		double dtRF = energyBarrierMax - energyBarrier;
		
		// Ensure that the Packet is not pulling more than the block can chew
		if(dtRF >= BlockRF) dtRF = BlockRF;
		
		// Ensure that the internal RF/t is not going over the limitations of the machine
		if(dtRF >= EternalPower.getInstance().getConfig().EnderPongRFRate) 
			dtRF = EternalPower.getInstance().getConfig().EnderPongRFRate;
			
		return dtRF;
	}
	
	// Precharge the barrier
	public boolean precharge(double RF){
		if(energyBarrier+RF > energyBarrierMax){
			ready = true;
			return false; // this packet cannot be charged anymore
		}
		if(sent) return false; // You cannot charge a package that is already enroute
		
		energyBarrier += RF; // charging
		
		return true;
	}
	
	public boolean send(){
		// Start the process
		if(returning || waiting) return false;
		
		ready = false; // This has been sent. It is not ready anymore.
		sent = true; // Allow the sending process
		return true;
	}
	
	// Process the packet in flight, this assumes 20tps
	public double processPacket(double Power){
		
		// Reset packet. It is done from the travel!
		if(done){
			 // Get the impulse power return from the packet
			double impulselevel = getImpulsePowerLevel(); 
			if(impulselevel >= 0 && !isImpulseDrained()){
				Power += drainImpulsePower(impulselevel);
			}else{
				reset();
			}
		}
		
		if(!sent){
			// Recharge packet if needed, ask the packet for how much it needs right now
			double RecommendRF = recommendPreRF(Power);
			
			if(Power >= 0 && precharge(RecommendRF)){
				Power -= RecommendRF; // discharge the main block
			}
		}else{
			// Calculate the acceleration and velocity
			this.velocity -= (enderPearls /
							(20*Math.pow(SlowFactor*distance,2))) * Math.pow(position-distance/2, 3);
			
			// Add the velocity to the position
			this.position += (!returning) ? velocity : -velocity; 
			
			if(!returning && position >= distance){
				pong(); // perform the Enderman pushback
				// Returning is set here.
			}
		}
		
		return Power;
	}
	
	// Packet has been received by the enderman!
	// Determine if/what has been lost in this packet 
	//		and the force returned from The End
	public void pong(){
		if(waiting){
			// random select tick
			if(rand.nextInt(60) == 0){ // Random chance that it hits 0
				calcPong();
				returning = true;
				waiting = false;
			}
		}
	}
	
	// Do the return materials calculation
	public void calcPong(){
		// Generate a random # of enderman attacks before they get fed up with the packet and send it back
		// 	These attacks are based on mass, pearls and shield. They are less likely to attack shielded
		
		// Calculate the # of enderman from the trip
		long Enderman = getEstimatedEnderman();
		double shieldstr = EternalPower.getInstance().getConfig().EnderPongShieldStrength;
		double sdamage = EternalPower.getInstance().getConfig().EnderPongDamage;
		
		// Check Enderman spawned
		if(Enderman > 0){
			double damage = (rand.nextInt(3)+sdamage-1)*Enderman;
			
			// Check the damage on the shield
			if(energyBarrier * shieldstr > damage){
				energyBarrier -= damage/shieldstr;
			}else{
				damage -= energyBarrier*shieldstr;
				energyBarrier = 0; // drain the shield
				
				double damageI = 0; // will iterate to 7*1000 per enderman
				double damageTo = damage*1000;
				// Use the last of the damage to actually effect the mass
				// This will damage 7*1000 mass per enderman
				Iterator<ItemStack> stacks = mass.inventoryItemStacks.iterator();
				
				while(stacks.hasNext()){
					ItemStack stack = stacks.next();
					
					double tdensity = getDensity(Item.getIdFromItem(stack.getItem()));
					double tmass = tdensity * stack.stackSize;
					
					if(tmass < damageTo-damageI){
						// Break this stack, add to damageI
						damageI += tmass;
						mass.inventoryItemStacks.remove(stack); // remove this stack
					}else{
						// Last set to break... determine the # of items removed:
						double preservedMass = tmass-(damageTo-damageI);
						double stacksx = Math.round(preservedMass/tdensity);
						stack.stackSize = (int) stacksx;
						damageI = damageTo;
						
					}
				}
				
				// If damageI is still < damageTo, just remove all of the ender pearls. 
				// 	The endermen will reclaim their brethren's lost ashes!
				if(damageI < damageTo){
					enderPearls = 0;
				}
			}
		}
	}
	
	// Get the mass of the blocks placed
	public double getMass(){
		double massx = 0;
		
		Iterator<ItemStack> stacks = mass.inventoryItemStacks.iterator();
		
		while(stacks.hasNext()){
			ItemStack stack = stacks.next();
			
			massx += getDensity(Item.getIdFromItem(stack.getItem())) * stack.stackSize;
		}
		
		return massx;
	}
	
	public double getDensity(int id){
		// return the density in kg/m3
		
		
		if(id == getIDFromName("redstone")) return 111.1; 	// ESTIMATED
		if(id == getIDFromName("diamond")) return 391.1;
		if(id == getIDFromName("iron_ingot")) return 874.4;
		if(id == getIDFromName("redstone_block")) return 1000.0;  // ESTIMATED
		if(id == getIDFromName("dirt")) return 1220.0;
		if(id == getIDFromName("gravel")) return 1680.0;
		if(id == getIDFromName("cobblestone")) return 2600.0;
		if(id == getIDFromName("gold_ingot")) return 2144.0;
		if(id == getIDFromName("diamond_block")) return 3520;
		if(id == getIDFromName("iron_block")) return 7870;
		if(id == getIDFromName("gold_block")) return 19300.0;
		
		return 300; // default return
	}
	
	public int getIDFromName(String name){
		return Item.getIdFromItem(Item.itemRegistry.getObject(new ResourceLocation(name)));
	}
	
	// return the estimated max number of enderman that will attack
	public long getEstimatedEnderman(){
		return Math.round(5*Math.pow(enderPearls-1, 2) + 
				100*Math.sqrt(getMass()/10000)-
				(1/33) * Math.pow(energyBarrier, 1.3)-1000);
	}
	
	// The packet is done.
	public void reset(){
		done = false;
		ready = false;
		waiting = false;
		returning = false;
		position = 0;
		velocity = 0;
		sent = false;
		impulsedone = false;
	}
	

	
	
	// Get the power on the receiving impulse
	public double getImpulsePowerLevel(){
		double RF = this.getMass()*EternalPower.getInstance().getConfig().EnderPongMultiplier;
		// Impulse power is based on the difference of the mass that was received.
		// Every kg = 1 RF
		
		// 128 pearls + 97.5k RF Shield + 777 gold blocks = Stable 1.5mil RF Generation
		
		if(impulseRF <= 0){
			impulseRF = RF;
		}
		
		
		return RF;
	}
	
	// Drain the RF Power from the Impulse power cache.
	public double drainImpulsePower(double RF){
		double dtRF = EternalPower.getInstance().getConfig().EnderPongRFRate;
		
		if(impulsedone) return 0.0;
		
		if(dtRF >= impulseRF){
			impulsedone = true;
			
		}
		
		impulseRF -= dtRF;
		return impulseRF;
	}
	
	
	// Fully remove this packet. Return the materials and RF
	public EnderPongPacketReturn remove(){
		return new EnderPongPacketReturn(energyBarrier, mass, enderPearls);
	}
	
	public boolean isReady(){
		return ready;
	}
	
	// Waiting for the enderman to find the packet and respond?
	public boolean isWaiting(){
		return waiting;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public boolean isSent(){
		return sent;
	}
	
	public boolean isImpulseDrained(){
		return impulsedone;
	}
	
	
	
	

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tags = new NBTTagCompound();
		
		tags.setInteger("EnderPearls", this.enderPearls);
		tags.setInteger("EndermanDamage", this.EndermanDamage);
		tags.setDouble("energyBarrier", this.energyBarrier);
		tags.setDouble("energyBarrierMax", this.energyBarrierMax);
		tags.setDouble("distance", this.distance);
		tags.setDouble("position", this.position);
		tags.setDouble("velocity", this.velocity);
		tags.setDouble("impulseRF", this.impulseRF);
		tags.setBoolean("ready",this.ready);
		tags.setBoolean("sent",this.sent);
		tags.setBoolean("returning",this.returning);
		tags.setBoolean("done",this.done);
		tags.setBoolean("waiting",this.waiting);
		
		return tags;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.enderPearls = nbt.getInteger("EnderPearls");
		this.EndermanDamage = nbt.getInteger("EndermanDamage");
		this.energyBarrier = nbt.getDouble("energyBarrier");
		this.energyBarrierMax = nbt.getDouble("energyBarrierMax");
		this.distance = nbt.getDouble("distance");
		this.position = nbt.getDouble("position");
		this.velocity = nbt.getDouble("velocity");
		this.impulseRF = nbt.getDouble("impulseRF");
		this.ready = nbt.getBoolean("ready");
		this.sent = nbt.getBoolean("sent");
		this.returning = nbt.getBoolean("returning");
		this.done = nbt.getBoolean("done");
		this.waiting = nbt.getBoolean("waiting");
	}
	
	// return class when a packet is deleted, so items and RF are returned.
	public class EnderPongPacketReturn{
		public EnderPongPacketReturn(double RF, LargeContainer contents, int pearls){
			this.RF = RF;
			this.contents = contents;
			this.EnderPearls = pearls;
		}
		public double RF;
		public LargeContainer contents;
		public int EnderPearls;
	}
}
