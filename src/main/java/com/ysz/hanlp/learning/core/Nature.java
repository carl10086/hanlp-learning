package com.ysz.hanlp.learning.core;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class Nature {

  private final String name;

  public Nature(String name) {
    this.name = name;
  }
}
