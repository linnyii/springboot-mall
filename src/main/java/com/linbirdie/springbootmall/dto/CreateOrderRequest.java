package com.linbirdie.springbootmall.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateOrderRequest {

    //曹狀json 寫法複習（4-4單元）
    //實際工作中很頻繁使用
    //@NotEmpty 通常使用在驗證List 或是Map 等變數不可為空
    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
