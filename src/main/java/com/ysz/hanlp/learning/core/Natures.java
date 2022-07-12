package com.ysz.hanlp.learning.core;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Natures {

  private static final int DEFAULT_SIZE = 256;
  private final ReadWriteLock lock;

  private final Lock readLock;

  private final Lock writeLock;

  private ObjectArrayList<Nature> natures;


  private Object2IntArrayMap<String> idMap;


  public Natures() {
    this(DEFAULT_SIZE);
  }

  public Natures(int exceptSize) {
    this.lock = new ReentrantReadWriteLock();
    this.readLock = this.lock.readLock();
    this.writeLock = this.lock.writeLock();

    this.natures = new ObjectArrayList<>(exceptSize);
    this.idMap = new Object2IntArrayMap<>(exceptSize);
  }

  public Nature[] batchAdd(String... natureStrs) {
    if (null != natureStrs && natureStrs.length > 0) {
      Nature[] result = new Nature[natureStrs.length];

      writeLock.lock();
      try {
        for (int i = 0; i < natureStrs.length; i++) {
          result[i] = doAddNature(natureStrs[i]);
        }
        return result;
      } finally {
        writeLock.unlock();
      }

    }

    return new Nature[0];
  }

  public Nature add(String natureStr) {
    Preconditions.checkNotNull(natureStr != null && natureStr.length() > 0);
    readLock.lock();
    try {
      if (idMap.containsKey(natureStr)) {
        return natures.get(idMap.getInt(natureStr));
      }
    } finally {
      readLock.unlock();
    }

    writeLock.lock();
    try {
      /*ABA problem*/
      return doAddNature(natureStr);
    } finally {
      writeLock.unlock();
    }

  }

  private Nature doAddNature(String natureStr) {
    if (idMap.containsKey(natureStr)) {
      return natures.get(idMap.getInt(natureStr));
    }

    int size = natures.size();

    final Nature newNature = new Nature(natureStr);

    this.natures.add(newNature);
    this.idMap.put(natureStr, size);

    return newNature;
  }

  public int size() {
    readLock.lock();
    try {
      return this.natures.size();
    } finally {
      readLock.unlock();
    }
  }

  /**
   * only for check
   *
   * @param index 检查位置为 index 的 map
   */
  public boolean checkAt(int index) {
    readLock.lock();
    try {
      Nature nature = this.natures.get(index);
      return this.idMap.getInt(nature.getName()) == index;
    } finally {
      readLock.unlock();
    }
  }

}
