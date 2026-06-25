S1
BEGIN
    FOR rec IN (
        SELECT l.loan_id, l.interest_rate 
        FROM loans l 
        JOIN customers c ON l.customer_id = c.customer_id 
        WHERE c.age > 60
    ) LOOP
        UPDATE loans
        SET interest_rate = interest_rate - 1
        WHERE loan_id = rec.loan_id;
    END LOOP;
    COMMIT;
END;
/

S2

BEGIN
    FOR rec IN (
        SELECT customer_id 
        FROM customers 
        WHERE balance > 10000
    ) LOOP
        UPDATE customers
        SET IsVIP = 'TRUE'
        WHERE customer_id = rec.customer_id;
    END LOOP;
    COMMIT;
END;
/

S3

BEGIN
    FOR rec IN (
        SELECT customer_name, loan_id, due_date 
        FROM loans 
        WHERE due_date BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Customer ' || rec.customer_name || ', your loan (ID: ' || rec.loan_id || ') is due on ' || TO_CHAR(rec.due_date, 'YYYY-MM-DD') || '.');
    END LOOP;
END;
/