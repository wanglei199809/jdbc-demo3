package cn.net.trimmer.jdbc.test;

/**
 * 测试线程局部变量
 * 
 * @author wl
 *
 */
public class TestThreadLocal {

	// 测试结果:该类型的变量可以被多线程共享,但是一旦该变量的值设置后,则该值只在本线程内部独享.
	public static void main(String[] args) throws Exception {
		final ThreadLocal<String> tol = new ThreadLocal<>();
		Thread t1 = new Thread() {
			public void run() {
				//设置局部变量
				tol.set("t1---hello");
				System.out.println(tol.get());
				//清除当前线程的局部变量
				tol.remove();
				System.out.println("t1线程清除局部变量后,局部变量="+tol.get());
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				tol.set("t2---hello");
				System.out.println(tol.get());
			}
		};
		t1.start();
		t2.start();
	}

}
