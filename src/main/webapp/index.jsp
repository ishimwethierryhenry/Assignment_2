<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Register a student</h1>
<form action="/SecondProject/new" method="post">
	<label for="id">Enter Id:</label>
	<input type="text" name="id" required/>
	<label" for="id">Enter Name:</label>
	<input type="text" name="name" required/>
	<button type="submit">Submit</button>	
</form>

<form action="/SecondProject/new" method="get">
<label for="id">Enter id to Search</label>
<input type="text" name=id />
<button type="submit">Search</button>
</form>
<h1> ${name} </h1>

</body>
</html>