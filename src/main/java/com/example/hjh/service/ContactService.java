package com.example.hjh.service;

import com.example.hjh.entity.Contact;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
public interface ContactService extends IService<Contact> {

    public boolean add(Contact contact);

    public boolean detele(String id);

}
