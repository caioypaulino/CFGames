package com.project.cfgames.helper;

public class Helper {
    
    public boolean stringIsNull(String string) {
        if (string.isBlank()) {
            return false;
        }
        return true;
    }
}
