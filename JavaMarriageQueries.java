string query = "SELECT sum(MaritalStatus)" +
"FROM Person" +
"WHERE MaritalStatus = 1" +
"AND Graduation_Year >=2013" +
";";

string query ="SELECT sum(MaritalStatus)" +
"FROM Person" +
"WHERE MaritalStatus = 1" +
"AND Graduation_Year >=2014" +
";";

string query = "SELECT sum(MaritalStatus)" +
"FROM Person" +
"WHERE MaritalStatus = 1" +
"AND Graduation_Year >=2015" +
"OR GradeLevel = 'Senior'" +
";";

string query = "SELECT sum(MaritalStatus)" +
"FROM Person" +
"WHERE MaritalStatus = 1" +
"AND Graduation_Year >=2016" +
"OR GradeLevel = 'Senior'" +
"OR GradeLevel = 'Junior'" +
";";

string query = "SELECT sum(MaritalStatus)" +
"FROM Person" +
"WHERE MaritalStatus = 1" +
"AND Graduation_Year >=2017" +
"OR GradeLevel = 'Senior'" +
"OR GradeLevel = 'Junior'" +
"OR GradeLevel = 'Sophmore'" +
";";

string query = "SELECT sum(MaritalStatus)" +
"FROM Person"
"WHERE MaritalStatus = 1" +
"AND Graduation_Year >=2018" +
"OR GradeLevel = 'Senior'" +
"OR GradeLevel = 'Junior'" +
"OR GradeLevel = 'Sophmore'" +
"OR GradeLevel = 'Freshman'" +
";";
