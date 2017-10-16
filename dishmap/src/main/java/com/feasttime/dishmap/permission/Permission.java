package com.feasttime.dishmap.permission;

import android.Manifest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoqing on 2017/9/25
 */

public class Permission {

    // id
    private int id;

    // 权限值
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Permission> getPermission() {

        List<Permission> list = new ArrayList();

        addPermission(list,  2, Manifest.permission.ACCESS_FINE_LOCATION);
        addPermission(list,  4, Manifest.permission.READ_PHONE_STATE);
        addPermission(list,  3, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        addPermission(list,  1, Manifest.permission.CAMERA);

        return list;
    }

    private static void  addPermission(List<Permission> list,  int id, String name){

        Permission permission = new Permission();

        permission.setId(id);
        permission.setName(name);

        list.add(permission);
    }

    public static String[] allPermissions = {

            Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


}
