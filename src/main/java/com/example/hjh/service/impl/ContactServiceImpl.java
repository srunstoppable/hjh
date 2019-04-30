package com.example.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.hjh.entity.Contact;
import com.example.hjh.mapper.ContactMapper;
import com.example.hjh.service.ContactService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2019-04-13
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

    @Override
    public boolean add(Contact contact) {
        return insert(contact);
    }

    @Override
    public boolean detele(String id) {
        EntityWrapper<Contact>ew = new EntityWrapper<>();
        ew.eq("exam_id",id);
        return delete(ew);

    }
}
