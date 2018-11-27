select count(*) as count
from Residents
where MaritalStatus=true
group by year
limit 5;

select count(*)
from Person,Resident
where GradeLevel in('Freshman','Sophomore','Junior','Senior')
and student=true and MaritalStatus=true and Person.StudentID=Resident.StudentID
group by year
limit 5;

select count(*) as count
from Residents
where MaritalStatus<>true
group by year
limit 5;

select count(*) as count
from Residents
where gender='female'
group by year
limit 5;

select BuildingID,RoomNum,description
from Maintenance request
where clearDate<>NULL
group by BuildingID,RoomNum

