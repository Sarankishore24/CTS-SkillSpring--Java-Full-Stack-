S1

DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT c.CustomerID, c.CustomerName, t.TransactionID, t.Amount, t.TransactionDate
        FROM Customers c
        JOIN Accounts a ON c.CustomerID = a.CustomerID
        JOIN Transactions t ON a.AccountID = t.AccountID
        WHERE t.TransactionDate >= TRUNC(SYSDATE, 'MM')
        AND t.TransactionDate < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)
        ORDER BY c.CustomerID, t.TransactionDate;
        
    v_cust_id       Customers.CustomerID%TYPE;
    v_cust_name     Customers.CustomerName%TYPE;
    v_trans_id      Transactions.TransactionID%TYPE;
    v_amount        Transactions.Amount%TYPE;
    v_trans_date    Transactions.TransactionDate%TYPE;
BEGIN
    OPEN GenerateMonthlyStatements;
    LOOP
        FETCH GenerateMonthlyStatements INTO v_cust_id, v_cust_name, v_trans_id, v_amount, v_trans_date;
        EXIT WHEN GenerateMonthlyStatements%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Customer: ' || v_cust_name || ' (ID: ' || v_cust_id || ') - Trans ID: ' || v_trans_id || ', Amount: ' || v_amount || ', Date: ' || TO_CHAR(v_trans_date, 'YYYY-MM-DD'));
    END LOOP;
    CLOSE GenerateMonthlyStatements;
END;


S2

DECLARE
    CURSOR ApplyAnnualFee IS
        SELECT AccountID, Balance 
        FROM Accounts;
        
    v_account_id   Accounts.AccountID%TYPE;
    v_balance      Accounts.Balance%TYPE;
    v_fee          CONSTANT NUMBER := 50.00;
BEGIN
    OPEN ApplyAnnualFee;
    LOOP
        FETCH ApplyAnnualFee INTO v_account_id, v_balance;
        EXIT WHEN ApplyAnnualFee%NOTFOUND;
        
        UPDATE Accounts
        SET Balance = Balance - v_fee
        WHERE AccountID = v_account_id;
    END LOOP;
    CLOSE ApplyAnnualFee;
    COMMIT;
END;


S3


DECLARE
    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID, LoanType, CurrentRate 
        FROM Loans;
        
    v_loan_id      Loans.LoanID%TYPE;
    v_loan_type    Loans.LoanType%TYPE;
    v_current_rate Loans.CurrentRate%TYPE;
    v_new_rate     Loans.CurrentRate%TYPE;
BEGIN
    OPEN UpdateLoanInterestRates;
    LOOP
        FETCH UpdateLoanInterestRates INTO v_loan_id, v_loan_type, v_current_rate;
        EXIT WHEN UpdateLoanInterestRates%NOTFOUND;
        
        IF v_loan_type = 'Home' THEN
            v_new_rate := v_current_rate + 0.5;
        ELSIF v_loan_type = 'Personal' THEN
            v_new_rate := v_current_rate + 1.0;
        ELSE
            v_new_rate := v_current_rate + 0.25;
        END IF;
        
        UPDATE Loans
        SET CurrentRate = v_new_rate
        WHERE LoanID = v_loan_id;
    END LOOP;
    CLOSE UpdateLoanInterestRates;
    COMMIT;
END;