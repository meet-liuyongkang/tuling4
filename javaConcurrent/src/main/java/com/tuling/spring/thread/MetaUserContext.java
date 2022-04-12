package com.tuling.spring.thread;


public class MetaUserContext {

    private final static ThreadLocal<MetaUser> local = new ThreadLocal<>();

    public static void setUserName(String userName) {
        metaUser().setUserName(userName);
    }


    public static String getUserName() {
        if(local.get() != null){
            return local.get().getUserName();
        }
        return null;
    }

    public static void remove() {
        local.remove();
    }

    private static MetaUser metaUser() {
        if (local.get() == null) {
            local.set(new MetaUser());
        }
        return local.get();
    }

    public static MetaUser get() {
        return local.get();
    }

    public static class MetaUser {
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
