package com.example.xi_ii_uebung.database;

public class UebungTbl {
    public static final String PTABLE_NAME = "Persons";

    public static final String PersonId = "PersonId";
    public static final String Firstname = "Firstname";
    public static final String Lastname = "Lastname";
    public static final String Age = "Age";

    public static final String PSQL_CREATE =
            "CREATE TABLE " + PTABLE_NAME +
                    " (" +
                    PersonId + " INTEGER PRIMARY KEY,"+
                    Firstname + " TEXT NOT NULL," +
                    Lastname + " TEXT NOT NULL," +
                    Age + " INT NOT NULL" +
                    ")";

    public static final String PSTMT_INSERT =
            "INSERT INTO " + PTABLE_NAME +
                    " (" + PersonId + ", " + Firstname + ", " + Lastname + ", " + Age + ")" +
                    " VALUES (?,?,?,?)";

    public static final String PSTMT_UPDATE =
            "UPDATE " + PTABLE_NAME + " SET " +
                    Firstname + " = ?, " +
                    Lastname + " = ?, " +
                    Age + " = ? " +
            "WHERE PersonId = ?";


    public static final String PSTMT_SELECT =
            "SELECT * FROM " + PTABLE_NAME;

    public static final String PSTMT_COUNT =
            "SELECT COUNT(*) FROM " + PTABLE_NAME;

    public static final String PSTMT_SELECT_ID =
            "SELECT * FROM " + PTABLE_NAME + " WHERE PersonId = ?";


    public static final String CTABLE_NAME = "Contacts";

    public static final String ContactId = "ContactId";
    public static final String City = "City";
    public static final String Street = "Street";
    public static final String ZipCode = "ZipCode";

    public static final String CSQL_CREATE =
            "CREATE TABLE " + CTABLE_NAME +
                    "(" +
                    ContactId + " INTEGER PRIMARY KEY, "+
                    City + " TEXT NOT NULL, " +
                    Street + " TEXT NOT NULL, " +
                    ZipCode + " TEXT NOT NULL" +
                    ")";

    public static final String CSTMT_INSERT =
            "INSERT INTO " + CTABLE_NAME +
                    " (" + ContactId + ", " + City + ", " + Street + ", " + ZipCode + ")" +
                    " VALUES (?,?,?,?)";

    public static final String CSTMT_UPDATE =
            "UPDATE " + CTABLE_NAME + " SET " +
                    City + " = ?, " +
                    Street + " = ?, " +
                    ZipCode + " = ? " +
            "WHERE ContactId = ?";

    public static final String CSTMT_SELECT =
            "SELECT * FROM " + CTABLE_NAME;

    public static final String CSTMT_SELECT_ID =
            "SELECT * FROM " + CTABLE_NAME + " WHERE ContactId = ?";

}
