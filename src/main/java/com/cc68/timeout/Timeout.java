package com.cc68.timeout;

import com.cc68.pojo.Message;
import com.cc68.pojo.UserAction;

public interface Timeout {
    Message handle(UserAction action);
}
