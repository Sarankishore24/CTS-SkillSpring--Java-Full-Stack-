S1

CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
) AS
    v_balance NUMBER;
    insufficient_funds EXCEPTION;
BEGIN
    SELECT balance INTO v_balance 
    FROM accounts 
    WHERE account_id = p_from_account;

    IF v_balance < p_amount THEN
        RAISE insufficient_funds;
    END IF;

    UPDATE accounts 
    SET balance = balance - p_amount 
    WHERE account_id = p_from_account;

    UPDATE accounts 
    SET balance = balance + p_amount 
    WHERE account_id = p_to_account;

    COMMIT;
EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in the source account.');
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: One or both account IDs do not exist.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transaction rolled back due to error: ' || SQLERRM);
END;
/


S2


CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_emp_id     IN NUMBER,
    p_percentage IN NUMBER
) AS
    v_dummy NUMBER;
    emp_not_found EXCEPTION;
BEGIN
    SELECT 1 INTO v_dummy 
    FROM employees 
    WHERE employee_id = p_emp_id;

    UPDATE employees
    SET salary = salary + (salary * (p_percentage / 100))
    WHERE employee_id = p_emp_id;

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_emp_id || ' does not exist.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An unexpected error occurred: ' || SQLERRM);
END;
/ 

S3

CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_customer_id   IN NUMBER,
    p_customer_name IN VARCHAR2,
    p_email         IN VARCHAR2
) AS
BEGIN
    INSERT INTO Customers (customer_id, customer_name, email)
    VALUES (p_customer_id, p_customer_name, p_email);

    COMMIT;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Insertion prevented. A customer with ID ' || p_customer_id || ' already exists.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An unexpected error occurred during insertion: ' || SQLERRM);
END;
/