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

public interface User extends com.zeroc.Ice.Object
{
    double checkAccountStatus(String id, com.zeroc.Ice.Current current)
        throws AccessDenied;

    LoanInfo getLoanInfo(CurrencyType currencyType, double loanValue, com.zeroc.Ice.Current current)
        throws OperationNotPermitted;

    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::com::gjasinski::ds::slice::UserImpl"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::com::gjasinski::ds::slice::UserImpl";
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_checkAccountStatus(User obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_id;
        iceP_id = istr.readString();
        inS.endReadParams();
        double ret = obj.checkAccountStatus(iceP_id, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeDouble(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getLoanInfo(User obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        CurrencyType iceP_currencyType;
        double iceP_loanValue;
        iceP_currencyType = CurrencyType.ice_read(istr);
        iceP_loanValue = istr.readDouble();
        inS.endReadParams();
        LoanInfo ret = obj.getLoanInfo(iceP_currencyType, iceP_loanValue, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        LoanInfo.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    final static String[] _iceOps =
    {
        "checkAccountStatus",
        "getLoanInfo",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping"
    };

    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_checkAccountStatus(this, in, current);
            }
            case 1:
            {
                return _iceD_getLoanInfo(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
