package com.ot.spring.proxy.jdk;

import java.lang.reflect.Proxy;

public class JdkProxyTest {

	private Worker worker;

	public JdkProxyTest(Worker worker) {
		this.worker = worker;
	}

	public JdkProxyTest() {
	}

	public Worker getInstance() {
		return (Worker) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),
				new Class[]{Worker.class},
				((proxy, method, args) -> {
					return method.invoke(worker, args);
				}));
	}


	public static void main(String[] args) {
		JdkProxyTest jdkProxyTest = new JdkProxyTest(new ManWorker());
		Worker instance = jdkProxyTest.getInstance();
		instance.work("sss");
	}
}
