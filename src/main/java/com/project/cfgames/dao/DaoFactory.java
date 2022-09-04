package com.project.cfgames.dao;

import com.project.cfgames.dao.impl.ClienteDaoImpl;

public class DaoFactory {

    public static ClienteDao createClienteDao() {
        return new ClienteDaoImpl();
    }

    
}
