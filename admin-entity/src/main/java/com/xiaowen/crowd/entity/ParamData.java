package com.xiaowen.crowd.entity;

import java.util.List;

/**
 * @Author wen.he
 * @Date 2020/7/10 10:45
 */
public class ParamData {

  protected List<Integer> array;

  public ParamData(List<Integer> array) {
    this.array = array;
  }

  public ParamData() {
  }

  public List<Integer> getArray() {
    return array;
  }

  public void setArray(List<Integer> array) {
    this.array = array;
  }

  @Override
  public String toString() {
    return "ParamData{" +
        "array=" + array +
        '}';
  }
}
