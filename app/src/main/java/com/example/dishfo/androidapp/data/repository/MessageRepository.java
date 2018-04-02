package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.MessageDao;
import com.example.dishfo.androidapp.bean.sqlBean.Message;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by dishfo on 18-3-19.
 */
public class MessageRepository {
    @Inject
    MessageDao messageDao;

    public MessageRepository(){
        MyApplication.getComponent().inject(this);
    }

    public List<Message> getMessagesByEmail(String email){
        return messageDao.getMessagesByEmail(email);
    }

    public void saveMessage(Message message){
        messageDao.saveMessage(message);
    }

    public void saveMessages(List<Message> messages){
        messageDao.saveMessage(messages);
    }
}
