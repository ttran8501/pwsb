mysql -uroot
connect pwsb;

create table shutters 
(
orderid int, 
itemnbr int,
width float,
length float,
CONSTRAINT uc_ItemID UNIQUE(orderid,itemnbr));


#OR

ALTER TABLE shutters ADD CONSTRAINT uc_ItemID UNIQUE(orderid,itemnbr);

ALTER TABLE OrderDetails MODIFY column framesize varchar(15);
ALTER TABLE OrderDetails ADD column color varchar(40);


create table customer
(
id int not null,
name varchar(50),
PRIMARY KEY (id)
)ENGINE=INNODB;

create table orderdetails
(
cust_id INT,
mount varchar(3),
width varchar(10),
length varchar(10),
panel smallint,
rail varchar(16),
railcount smallint,
louverlen varchar(10),
louversize varchar(6),
louvercount varchar(25),
stilelen varchar(10),
bited smallint,
rabited smallint,
hinge varchar(5),
color varchar(40),
framecount smallint,
framesize varchar(15),
instruction varchar(80),

itemid INT NOT NULL AUTO_INCREMENT,
PRIMARY KEY(itemid),

INDEX (cust_id),
FOREIGN KEY (cust_id)
   REFERENCES customer(id)
   ON DELETE CASCADE
)ENGINE=INNODB;

GRANT ALL PRIVILEGES ON dbTest.* To 'user'@'hostname' IDENTIFIED BY 'password';

create table orderdetailnew
AS (select cust_id,
mount,
width,
length,
panel,
rail,
railcount,
louverlen,
louversize,
louvercount,
stilelen,
bited,
rabited,
hinge,
color,
framecount,
framesize,
instruction
from orderdetails);

alter table orderdetailnew rename to orderdetails;