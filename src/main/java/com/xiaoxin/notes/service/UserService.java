package com.xiaoxin.notes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxin.notes.entity.PermissionEntity;
import com.xiaoxin.notes.entity.PersonNotesEntity;
import com.xiaoxin.notes.entity.UserEntity;

import java.util.List;

/**
 * 系统用户
 *
 * @author Ð¡ÐÄ×Ð
 * @email ${email}
 * @date 2020-12-19 22:19:45
 */
public interface UserService extends IService<UserEntity> {

    void submitReg(UserEntity user);

    String login(String username, String password);

    void selectUserCount(String username);

    UserEntity selOneUser(String id);

    UserEntity getUserByUsername(String username);

    List<PermissionEntity> getPermissionList(Integer userId);

    List<Integer> selRoleByUserId(String id);

    void saveRolesByUserId(String userId, String rids);

    List<PersonNotesEntity> getPersonHubList();

    void savePerNotes(PersonNotesEntity personNotes);

    void updatePwd(String password,String newPwd,String username);

    void delById(String id);
}

