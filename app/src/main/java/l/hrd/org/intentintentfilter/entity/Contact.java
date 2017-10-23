package l.hrd.org.intentintentfilter.entity;

import l.hrd.org.intentintentfilter.entity.base.BaseEntity;

/**
 * Created by RATHANA on 10/24/2017.
 */

public class Contact extends BaseEntity{
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        super(name);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
