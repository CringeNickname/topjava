<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
  <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2><br>
<form method="post" action="editMeal">
  <h4>DateTime:</h4><label>
    <input type="datetime-local" name="date">
  </label>
  <h4>Description:</h4><label>
    <input type="text" name="description">
  </label>
  <h4>Calories:</h4><label>
    <input type="number" name="calories">
  </label>
  <input type="number" hidden name="id" value="<%=request.getAttribute("id")%>">
  <br><br>
  <input type="submit" value="Save" name="Save">&nbsp;<button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
