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

public enum CurrencyType implements java.io.Serializable
{
    PLN(0),
    EUR(1),
    USD(2),
    GBP(3);

    public int value()
    {
        return _value;
    }

    public static CurrencyType valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return PLN;
        case 1:
            return EUR;
        case 2:
            return USD;
        case 3:
            return GBP;
        }
        return null;
    }

    private CurrencyType(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 3);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, CurrencyType v)
    {
        if(v == null)
        {
            ostr.writeEnum(com.gjasinski.ds.slice.CurrencyType.PLN.value(), 3);
        }
        else
        {
            ostr.writeEnum(v.value(), 3);
        }
    }

    public static CurrencyType ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(3);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<CurrencyType> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, CurrencyType v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<CurrencyType> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static CurrencyType validate(int v)
    {
        final CurrencyType e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}