package com.sierzega.pagingclean;

public class Status {
    private static Status INSTANCE;
    public int page = 0;
    public boolean isFetching = false;

    public static Status getInstance(){
        if(INSTANCE == null){
            synchronized (Status.class){
                if(INSTANCE == null){
                    INSTANCE = new Status();
                }
            }
        }
        return INSTANCE;
    }

}
