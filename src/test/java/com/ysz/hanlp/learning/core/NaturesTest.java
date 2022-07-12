package com.ysz.hanlp.learning.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NaturesTest {

  public Natures natures;

  @Before
  public void setUp() throws Exception {
    this.natures = new Natures();
  }


  /**
   * 并发测试 add 的正确性
   */
  @Test
  public void concurrentAdd() throws Exception {
    int threadNum = 4;

    final int loop = 1000;
    ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
    for (int n = 0; n < threadNum; n++) {
      executorService.submit(() -> {
        for (int i = 0; i < loop; i++) {
          natures.add("a" + i);
        }
      });
    }

    executorService.shutdown();
    while (true) {
      Thread.sleep(1000L);
      if (executorService.isTerminated()) {
        break;
      }
    }

    Assert.assertEquals(natures.size(), loop);
    for (int i = 0; i < natures.size(); i++) {
      Assert.assertTrue(this.natures.checkAt(i));
    }
  }
}