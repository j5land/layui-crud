package com.j5land.layuicrud.model.response;

public class ResultPage {
     /**
     * 状态码
     * */
    private Integer code;

    /**
     * 提示消息
     * */
    private String msg;

    /**
     * 消息总量
     * */
    private Long count;

    /**
     * 数据对象
     * */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 构 建
     * */
    public static ResultPage page(long count,Object data){
        ResultPage resultTable = new ResultPage();
        resultTable.setData(data);
        resultTable.setCode(0);
        resultTable.setCount(count);
        return resultTable;
    }

    /**
     * 构 建
     * */
    public static ResultPage page(Object data, long count){
        return page(count, data);
    }

    public static ResultPage dataTable(Object data){
        ResultPage resultTable = new ResultPage();
        resultTable.setData(data);
        resultTable.setCode(0);
        return resultTable;
    }
}
