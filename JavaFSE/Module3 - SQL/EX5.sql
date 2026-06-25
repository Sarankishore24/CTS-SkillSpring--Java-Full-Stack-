S1

CREATE TRIGGER UpdateCustomerLastModified
ON Customers
AFTER UPDATE
AS
BEGIN
    UPDATE Customers
    SET LastModified = GETDATE()
    FROM Customers c
    JOIN inserted i ON c.CustomerID = i.CustomerID;
END;

S2


CREATE TRIGGER LogTransaction
ON Transactions
AFTER INSERT
AS
BEGIN
    INSERT INTO AuditLog (TransactionID, Action, LogDate)
    SELECT TransactionID, 'INSERT', GETDATE()
    FROM inserted;
END;


S3



CREATE TRIGGER CheckTransactionRules
ON Transactions
AFTER INSERT
AS
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN Accounts a ON i.AccountID = a.AccountID
        WHERE i.TransactionType = 'Withdrawal' AND i.Amount > a.Balance
    )
    BEGIN
        RAISERROR ('Withdrawal amount exceeds account balance.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END

    IF EXISTS (
        SELECT 1 
        FROM inserted
        WHERE TransactionType = 'Deposit' AND Amount <= 0
    )
    BEGIN
        RAISERROR ('Deposit amount must be positive.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END
END;