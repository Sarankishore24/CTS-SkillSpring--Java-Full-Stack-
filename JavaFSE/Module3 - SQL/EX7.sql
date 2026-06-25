-- Scenario1

CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireEmployee(
        p_EmployeeID IN NUMBER,
        p_Name       IN VARCHAR2,
        p_Salary     IN NUMBER,
        p_HireDate   IN DATE
    );

    PROCEDURE UpdateEmployeeDetails(
        p_EmployeeID IN NUMBER,
        p_Name       IN VARCHAR2,
        p_Salary     IN NUMBER
    );

    FUNCTION CalculateAnnualSalary(
        p_EmployeeID IN NUMBER
    ) RETURN NUMBER;
END EmployeeManagement;
/

-- Package body
CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_EmployeeID IN NUMBER,
        p_Name       IN VARCHAR2,
        p_Salary     IN NUMBER,
        p_HireDate   IN DATE
    ) IS
    BEGIN
        -- Assuming an Employees table exists with these columns
        INSERT INTO Employees (EmployeeID, Name, Salary, HireDate, LastModified)
        VALUES (p_EmployeeID, p_Name, p_Salary, p_HireDate, SYSDATE);
        COMMIT;
    END HireEmployee;

    PROCEDURE UpdateEmployeeDetails(
        p_EmployeeID IN NUMBER,
        p_Name       IN VARCHAR2,
        p_Salary     IN NUMBER
    ) IS
    BEGIN
        UPDATE Employees
        SET Name = p_Name,
            Salary = p_Salary,
            LastModified = SYSDATE
        WHERE EmployeeID = p_EmployeeID;
        COMMIT;
    END UpdateEmployeeDetails;

    FUNCTION CalculateAnnualSalary(
        p_EmployeeID IN NUMBER
    ) RETURN NUMBER IS
        v_MonthlySalary NUMBER := 0;
    BEGIN
        SELECT Salary INTO v_MonthlySalary
        FROM Employees
        WHERE EmployeeID = p_EmployeeID;
        
        RETURN v_MonthlySalary * 12;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END CalculateAnnualSalary;

END EmployeeManagement;
/

-- Scenario2

CREATE OR REPLACE PACKAGE AccountOperations AS
    -- Procedure to open a new account
    PROCEDURE OpenAccount(
        p_AccountID   IN Accounts.AccountID%TYPE,
        p_CustomerID  IN Accounts.CustomerID%TYPE,
        p_AccountType IN Accounts.AccountType%TYPE,
        p_Balance     IN Accounts.Balance%TYPE
    );

    -- Procedure to close an account (deleting or setting balance to 0)
    PROCEDURE CloseAccount(
        p_AccountID IN Accounts.AccountID%TYPE
    );

    -- Function to get total balance across all accounts for a customer
    FUNCTION GetTotalBalance(
        p_CustomerID IN Accounts.CustomerID%TYPE
    ) RETURN NUMBER;
END AccountOperations;
/

-- Package Body

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(
        p_AccountID   IN Accounts.AccountID%TYPE,
        p_CustomerID  IN Accounts.CustomerID%TYPE,
        p_AccountType IN Accounts.AccountType%TYPE,
        p_Balance     IN Accounts.Balance%TYPE
    ) IS
    BEGIN
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_AccountID, p_CustomerID, p_AccountType, p_Balance, SYSDATE);
        COMMIT;
    END OpenAccount;

    PROCEDURE CloseAccount(
        p_AccountID IN Accounts.AccountID%TYPE
    ) IS
    BEGIN
        -- Option A: Physically remove the record
        DELETE FROM Accounts WHERE AccountID = p_AccountID;
        
        -- Option B (Alternative): If soft-deleting, you would update a status column instead
        COMMIT;
    END CloseAccount;

    FUNCTION GetTotalBalance(
        p_CustomerID IN Accounts.CustomerID%TYPE
    ) RETURN NUMBER IS
        v_TotalBalance NUMBER := 0;
    BEGIN
        SELECT SUM(Balance) INTO v_TotalBalance
        FROM Accounts
        WHERE CustomerID = p_CustomerID;
        
        -- Handle case where user has no accounts (SUM returns NULL)
        RETURN NVL(v_TotalBalance, 0);
    END GetTotalBalance;

END AccountOperations;
/