package com.foxconn.common.utils;

import com.foxconn.common.interfaces.LockTask;

public class LockUtil {

    public static<T> T lock(String lockKey, LockTask<T> lockTask){
        synchronized (lockKey.intern()){
            return lockTask.lockTask();
        }
    }

}
