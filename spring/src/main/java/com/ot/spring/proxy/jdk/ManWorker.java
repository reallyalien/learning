package com.ot.spring.proxy.jdk;

public class ManWorker implements Worker {
	@Override
	public void work(String str) {
		System.out.println("ManWorker -->" + str);
	}
}
