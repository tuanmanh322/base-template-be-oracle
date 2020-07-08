Install java home, maven home<br/>
Run command install library oracle: <code>mvn install:install-file -Dfile=ojdbc6-11.2.0.3.jar  -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3 -Dpackaging=jar -DgeneratePom=true</code><br/>
Login database oracle as DBA run statement:<br/>
<code>
    CREATE USER database_mock IDENTIFIED BY itsol_mock#123;<br/>
    GRANT CONNECT, RESOURCE, DBA TO database_mock;<br/>
    GRANT UNLIMITED TABLESPACE TO database_mock;
</code><br/>
Login database with user name: <code>database_mock</code>, password: <code>itsol_mock#123</code><br/>
Run statement create table:<br/>
<code>CREATE TABLE TBL_AUTHORITY(
          NAME VARCHAR2(15) NOT NULL PRIMARY KEY
      )
      CREATE TABLE TBL_USER(
          ID NUMBER(10) NOT NULL PRIMARY KEY,
          USERNAME VARCHAR2(50),
          PASSWORD VARCHAR2(70),
          EMAIL VARCHAR2(50),
          ACTIVATED NUMBER(1),
          LANG_KEY VARCHAR2(10),
          IMAGE_URL VARCHAR2(256),
          ACTIVATION_KEY VARCHAR2(20),
          RESET_KEY VARCHAR2(20),
          RESET_DATE DATE,
          CREATED_DATE DATE NOT NULL,
          UPDATED_DATE DATE
      )
      
      CREATE TABLE TBL_USER_AUTHORITY(
          USER_ID NUMBER(10),
          AUTHORITY_NAME VARCHAR2(15),
          CONSTRAINT TBL_USER_AUTHORITY_pk PRIMARY KEY (USER_ID, AUTHORITY_NAME),
          CONSTRAINT fk_TBL_AUTHORITY FOREIGN KEY (AUTHORITY_NAME) REFERENCES TBL_AUTHORITY(NAME),
          CONSTRAINT fk_TBL_USER FOREIGN KEY (USER_ID) REFERENCES TBL_USER(ID)
      )
      CREATE SEQUENCE USER_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1 NOCACHE;
      
      INSERT ALL INTO TBL_AUTHORITY
          INTO TBL_AUTHORITY (NAME) VALUES ('ROLE_ADMIN')
          INTO TBL_AUTHORITY (NAME) VALUES ('ROLE_USER')
          INTO TBL_AUTHORITY (NAME) VALUES ('ROLE_WEB_USER')
          INTO TBL_AUTHORITY (NAME) VALUES ('ANONYMOUS')
      SELECT * FROM DUAL</code>
