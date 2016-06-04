package com.tigersndragons.docbookmarks.model;

import java.util.ArrayList;
import java.util.List;

public class Dolphin extends Mammal {
	
	public boolean hasFur(){
		return false;
	}
	
	public String describe(){
		
		String abc = new String ("abc");
		String abc1 = "abc";
		String abc2 ="";
		StringBuilder builder = new StringBuilder();
		builder = builder.append("a");
		builder =builder.append("b");
		builder = builder.append("c");
		abc2 = builder.toString();
		if (abc2.equals(abc)){
			
		}
		
		return "abc";
		//return abc;
	}
	
	public void counting(int aNumber){
		Integer zero = new Integer(0);
		
		int z =0;
		if (z == zero.intValue()){
			
		}
	}
	
	public List<Dog>  getaList(){
		ArrayList<Dog> list = new ArrayList<Dog>();
		list.add(new Dog());
		for (Dog d : list){
			d.hasFur();
		}
		return list;
		
	}
}
