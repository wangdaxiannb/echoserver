package com.wangp.decoder;

import java.util.concurrent.*;

public class MyTest {


    public static void main(String[] args)  {
        ExecutorService service = Executors.newCachedThreadPool();
       try {



                Future future = service.submit(new Callable<Object>() {


                public Object call() throws Exception {
                    System.out.println("call()....sleep....");
                    Thread.sleep(5000);
                    return "success";
                }
            });
            System.out.println("main主线程执行。。。。。");
            System.out.println("future......." + future.get());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            service.shutdown();
        }
    }
}
