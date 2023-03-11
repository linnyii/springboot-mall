package com.linbirdie.springbootmall.constant;

//這裡所定義的constant name 一定要跟資料庫裡面的值撰寫一樣
//假如Enum裡面沒有定義，則會回傳錯誤代碼
//如此我們可以確保從資料庫所回傳的值都是有被定義，避免從資料庫撈到髒值
public enum ProductCategory {
    FOOD,
    BOOK,
    CAR
}
