package com.supyp.bghouse.utils;

import com.supyp.bghouse.domain.entity.Account;

public class ThreadLocalUtil {
    //把构造函数私有，外面不能new，只能通过下面两个方法操作
    private ThreadLocalUtil(){
    }
    private static final ThreadLocal<Account> LOCAL = new ThreadLocal<Account>();
    public static void set(Account account){
        LOCAL.set(account);;
    }
    public static Account get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
