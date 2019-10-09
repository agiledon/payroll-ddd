CREATE TABLE timecards(
    id INT NOT NULL AUTO_INCREMENT,
    employeeId VARCHAR(50) NOT NULL,
    workDay DATE NOT NULL,
    workHours INT NOT NULL,
    createdTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedTime TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-09-02', 8);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-09-03', 8);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-09-04', 10);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-09-05', 9);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-09-06', 7);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000002', '2019-09-02', 8);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000002', '2019-09-03', 10);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000002', '2019-09-04', 5);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000002', '2019-09-05', 9);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000002', '2019-09-06', 7);