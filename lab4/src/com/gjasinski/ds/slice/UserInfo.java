// **********************************************************************
//
// Copyright (c) 2003-2017 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.0
//
// <auto-generated>
//
// Generated from file `ClientBankInterface.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.gjasinski.ds.slice;

public class UserInfo implements java.lang.Cloneable,
                                 java.io.Serializable
{
    public String firstname;

    public String lastname;

    public String pesel;

    public long income;

    public UserInfo()
    {
        this.firstname = "";
        this.lastname = "";
        this.pesel = "";
    }

    public UserInfo(String firstname, String lastname, String pesel, long income)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pesel = pesel;
        this.income = income;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        UserInfo r = null;
        if(rhs instanceof UserInfo)
        {
            r = (UserInfo)rhs;
        }

        if(r != null)
        {
            if(this.firstname != r.firstname)
            {
                if(this.firstname == null || r.firstname == null || !this.firstname.equals(r.firstname))
                {
                    return false;
                }
            }
            if(this.lastname != r.lastname)
            {
                if(this.lastname == null || r.lastname == null || !this.lastname.equals(r.lastname))
                {
                    return false;
                }
            }
            if(this.pesel != r.pesel)
            {
                if(this.pesel == null || r.pesel == null || !this.pesel.equals(r.pesel))
                {
                    return false;
                }
            }
            if(this.income != r.income)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::com::gjasinski::ds::slice::UserInfo");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, firstname);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lastname);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, pesel);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, income);
        return h_;
    }

    public UserInfo clone()
    {
        UserInfo c = null;
        try
        {
            c = (UserInfo)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.firstname);
        ostr.writeString(this.lastname);
        ostr.writeString(this.pesel);
        ostr.writeLong(this.income);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.firstname = istr.readString();
        this.lastname = istr.readString();
        this.pesel = istr.readString();
        this.income = istr.readLong();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, UserInfo v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public UserInfo ice_read(com.zeroc.Ice.InputStream istr)
    {
        UserInfo v = new UserInfo();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<UserInfo> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, UserInfo v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<UserInfo> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(UserInfo.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final UserInfo _nullMarshalValue = new UserInfo();

    public static final long serialVersionUID = 964044710098677351L;
}
