SELECT sum(MaritalStatus)
FROM Person
WHERE MaritalStatus = 1
	AND Graduation_Year >=2013
;

SELECT sum(MaritalStatus)
FROM Person
WHERE MaritalStatus = 1
	AND Graduation_Year >=2014
;

SELECT sum(MaritalStatus)
FROM Person
WHERE MaritalStatus = 1
	AND Graduation_Year >=2015
		OR GradeLevel = 'Senior'
;

SELECT sum(MaritalStatus)
FROM Person
WHERE MaritalStatus = 1
	AND Graduation_Year >=2016
		OR GradeLevel = 'Senior'
			OR GradeLevel = 'Junior'
;

SELECT sum(MaritalStatus)
FROM Person
WHERE MaritalStatus = 1
	AND Graduation_Year >=2017
		OR GradeLevel = 'Senior'
			OR GradeLevel = 'Junior'
				OR GradeLevel = 'Sophmore'
;

SELECT sum(MaritalStatus)
FROM Person
WHERE MaritalStatus = 1
	AND Graduation_Year >=2018
		OR GradeLevel = 'Senior'
			OR GradeLevel = 'Junior'
				OR GradeLevel = 'Sophmore'
					OR GradeLevel = 'Freshman'
;