#ifndef CALC_ICE
#define CALC_ICE
module com{
module gjasinski
{
  module ds {
    module slice {
        enum CurrencyType {
            PLN,
            EUR,
            USD,
            GBP
        };

        struct UserInfo {
            string firstname;
            string lastname;
            string pesel;
            long income;
        };

        struct LoanInfo {
            double loanBaseCurrency;
            double loanForeignCurrency;
            CurrencyType currencyType;
            double exchangeRate;
            double loanValue;
        };

        exception AccessDenied {};
        exception OperationNotPermitted {};

        interface User {
            double checkAccountStatus(string id) throws AccessDenied;
            LoanInfo getLoanInfo(CurrencyType currencyType, double loanValue) throws OperationNotPermitted;
        };

        interface Factory {
            string createAccount(UserInfo userInfo);
            User* login(string id) throws AccessDenied;
        };
    };
  };
};
};
#endif
