package com.ysz.hanlp.learning.hanlp;

import com.google.common.collect.Lists;
import com.ysz.hanlp.learning.core.DoubleArrayTrie;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DoubleArrayTrieTst {

  @Test
  public void tstBaseSearch() throws Exception {
    TreeMap<String, String> treeMap = new TreeMap<>();
    treeMap.put("自然", "nature");
    treeMap.put("自然人", "human");
    treeMap.put("自然语言", "language");
    treeMap.put("自语", "talk to oneself");
    treeMap.put("入门", "introduction");

    DoubleArrayTrie<String> trie = new DoubleArrayTrie<>(treeMap);

    for (String key : Lists.newArrayList("自然", "自然语言", "自语", "入门", "不存在")) {
      System.out.printf("key:%s, value:%s\n", key, trie.get(key));
    }
  }


  @Test
  public void tstBaseSet() {
    TreeMap<String, String> treeMap = new TreeMap<>();
    treeMap.put("自然", "nature");
    DoubleArrayTrie<String> trie = new DoubleArrayTrie<>(treeMap);
    for (String key : Lists.newArrayList("自然", "自然人", "自语", "入门", "不存在")) {
      System.out.printf("key:%s, value:%s\n", key, trie.get(key));
    }
  }


  @Test
  public void tstMyDouble() {
    TreeMap<String, String> treeMap = new TreeMap<>();
    treeMap.put("ab", "ab");
    DoubleArrayTrie<String> trie = new DoubleArrayTrie<>(treeMap);

    int[] base = trie.getBase();
    int[] check = trie.getCheck();

    /*先计算 a 的 state*/
    int stateOfA = base[0] + 'a' + 1;
    log.info("stateOfA:{}", stateOfA);

    log.info("check path from root -> a , base[0]:{}, check[stateOfA]:{}, path is {}",
             base[0],
             check[stateOfA],
             base[0] == check[stateOfA]
    );

    int stateOfB = base[0] + 'b' + 1;
    log.info("stateOfB:{}", stateOfB);

    log.info("check path from root -> b , base[0]:{}, check[stateOfB]:{}, path is {}",
             base[0],
             check[stateOfB],
             base[0] == check[stateOfB]
    );


    /*检查 a 是否是 end*/

    int stateOfAToEnd = base[stateOfA] + '\0';
    log.info("check path from a -> '\\0' , base[stateOfA]:{}, check[stateOfAToEnd]:{}, path is {}",
             base[stateOfA],
             check[stateOfAToEnd],
             base[stateOfA] == check[stateOfAToEnd]
    );

    /*检查 a 是否能到达 ab*/
    int statOfAToB = base[stateOfA] + 'b' + 1;
    log.info("check path from a -> b , base[stateOfA]:{}, check[statOfAToB]:{}, path is {}",
             base[stateOfA],
             check[statOfAToB],
             base[stateOfA] == check[statOfAToB]
    );

    int stateOfAb = statOfAToB;

    /*检查 ab 是否能到达 end \0*/
    int stateOfAbToEnd = base[stateOfAb];
    log.info("check path from ab -> '\\0' , base[stateOfAb]:{}, check[stateOfAbToEnd]:{}, path is {}",
             base[stateOfAb],
             check[stateOfAbToEnd],
             base[stateOfAb] == check[stateOfAbToEnd]
    );

  }


}
