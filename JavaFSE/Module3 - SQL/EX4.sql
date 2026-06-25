S1

CREATE PROCEDURE ProcessMonthlyInterest
AS
BEGIN
    UPDATE Accounts
    SET Balance = Balance * 1.01
    WHERE AccountType = 'Savings';
END;

S2


CREATE PROCEDURE UpdateEmployeeBonus
    @DepartmentID INT,
    @BonusPercentage DECIMAL(5,2)
AS
BEGIN
    UPDATE Employees
    SET Salary = Salary * (1 + (@BonusPercentage / 100))
    WHERE DepartmentID = @DepartmentID;
END;

S3

CREATE PROCEDURE TransferFunds
    @SourceAccountID INT,
    @DestinationAccountID INT,
    @Amount DECIMAL(18,2)
AS
BEGIN
    DECLARE @CurrentBalance DECIMAL(18,2);

    SELECT @CurrentBalance = Balance 
    FROM Accounts 
    WHERE AccountID = @SourceAccountID;

    IF @CurrentBalance >= @Amount
    BEGIN
        UPDATE Accounts
        SET Balance = Balance - @Amount
        WHERE AccountID = @SourceAccountID;

        UPDATE Accounts
        SET Balance = Balance + @Amount
        WHERE AccountID = @DestinationAccountID;
    END
END;