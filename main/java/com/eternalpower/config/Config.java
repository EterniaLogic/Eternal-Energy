package com.eternalpower.config;


import java.io.File;

import net.minecraftforge.common.config.Configuration;



public class Config extends Configuration {
	public Config(){
		super(new File("config/EternalPower.cfg"),"0.1");
		
		doLoad();
	}
	
	
	public static double EnderPongMultiplier = 1; // Power output multiplier for the Ender Pong Reactor
	public static double EnderPongRFRate = 10000000; // Internal recharge rate
	public static double EnderPongRFStorage = 100000000; // Internal energy buffer (Fully upgraded)
	public static double EnderPongRF_ExtRate = 1300000; // External energy exchange rate (Fully upgraded)
	public static double EnderPongPacketLoss = 0.001; // Percentage possible loss of the entire Ender pong packet
	public static double EnderPongBitLoss = 0.05; // Percentage possible loss of a part of the Ender pong packet
	public static double EnderPongShieldStrength = 1.2; // Exponential strength of the shield
	public static double EnderPongDistance = 100000; // Distance to The End, this is in meters (Velocity is calculated along with momentum, so 100km is not really that far
	public static double EnderPongDamage = 7; // Common Enderman damage before the packet capsule can leave The End
	
	
	public void doLoad(){
		load();

		// Base-mod Configuration
		EnderPongMultiplier = this.getFloat("EnderPongMultiplier","EnderPongReactor",1.f,0.01f,100000.f,"Power multiplier for the Ender Pong Reactor");
		EnderPongRFRate = this.getFloat("EnderPongRFRate","EnderPongReactor",10000000.f,0.01f,100000000.f,"Internal packet precharge rate");
		EnderPongRFStorage = this.getFloat("EnderPongRFStorage","EnderPongReactor",100000000.f,10.f,10000000000.f,"Internal energy buffer (fully upgraded)");
		EnderPongRF_ExtRate = this.getFloat("EnderPongRF_ExtRate","EnderPongReactor",1300000.f,10.f,1000000000.f,"External energy exchange rate (fully upgraded)");
		EnderPongPacketLoss = this.getFloat("EnderPongPacketLoss","EnderPongReactor",0.001f,0.01f,100000.f,"Percentage possible loss of the entire Ender pong packet (If the shield is 0)");
		EnderPongBitLoss = this.getFloat("EnderPongBitLoss","EnderPongReactor",0.05f,0.01f,100000.f,"Percentage possible loss of a part of the Ender pong packet (If the shield is 0)");
		EnderPongShieldStrength = this.getFloat("EnderPongShieldStrength","EnderPongReactor",1.2f,0.01f,1000.f,"Exponential strength of the shield (shield -= (TotalEndermanDmg)^(-SheildStr)");
		EnderPongDistance = this.getFloat("EnderPongDistance","EnderPongReactor",100000.f,0.01f,100000000.f,"Distance to The End, this is in meters (Acceleration is calculated in a ballistic arc, so 100km is not really that far)");
		EnderPongDamage = this.getFloat("EnderPongDamage","EnderPongReactor",7.f,0.001f,500.f,"Common Enderman damage before the packet capsule can leave The End (7 = minecraft default medium)");
		
		
		save(); // changes to config (auto-adds to config)
	}
}
