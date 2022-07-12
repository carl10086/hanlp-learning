package com.ysz.hanlp.learning.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dictionary {

  private final Natures allNatures;

  public Dictionary(Natures allNatures) {
    this.allNatures = allNatures;
  }

  public Attribute parseAttribute(String natureWithFrequency) {
    try {
      String param[] = natureWithFrequency.split(" ");
      if (param.length % 2 != 0) {
        return new Attribute(this.allNatures.add(natureWithFrequency.trim()), 1);
      }
      int natureCount = param.length / 2;
      Nature[] natures = new Nature[natureCount];
      int totalFrequency = 0;
      int[] frequency = new int[natureCount];
      for (int i = 0; i < natureCount; ++i) {
        natures[i] = allNatures.add(param[2 * i]);
        frequency[i] = Integer.parseInt(param[1 + 2 * i]);
        totalFrequency += frequency[i];
      }
      return new Attribute(natures, frequency, totalFrequency);
    } catch (Exception e) {
      log.warn("parseAttribute failed:{}", natureWithFrequency, e);
      return null;
    }


  }

  @Getter
  public static class Attribute {

    private final Nature[] nature;

    private final int[] frequency;

    private final int totalFreq;

    public Attribute(Nature nature, int frequency) {
      this(new Nature[]{nature}, new int[]{frequency}, frequency);
    }

    public Attribute(Nature[] nature, int[] frequency, int totalFreq) {
      this.nature = nature;
      this.frequency = frequency;
      this.totalFreq = totalFreq;
    }
  }


}
