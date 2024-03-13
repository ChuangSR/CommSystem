package com.cc68.mapper;

import com.cc68.pojo.MessageDatabase;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface MessageMapper {
    void createTable(String account);
    int insertOriginatorMessageDatabase(MessageDatabase messageDatabase);

    int insertReceiverMessageDatabase(MessageDatabase messageDatabase);

    ArrayList<MessageDatabase> messageLoad(@Param("account") String account,@Param("time") long time);
}
