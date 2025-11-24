package com.qy.message.app.application.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultBean<T> implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 总页数
   */
  private Long PageCount = 0L;
  /**
   * 总记录数
   */
  private Long totalCount = 0L;
  /**
   * 返回数据
   */
  private List<T> data = new ArrayList<>();

  private String accessToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public Long getPageCount() {
    return PageCount;
  }

  public void setPageCount(Long pageCount) {
    PageCount = pageCount;
  }

  public Long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
}
