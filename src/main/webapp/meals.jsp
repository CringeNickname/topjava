<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="editMeal.jsp">Add meals</a><br>
<%
    List<MealTo> meals = (List<MealTo>) request.getAttribute("meals");

%>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <%for (MealTo meal: meals) {%>
    <%String color;
      if (meal.isExcess()) color = "#ff0000";
      else color = "#228b22";%>
        <tr style="color: <%= color%>">
            <td><%= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(meal.getDateTime())%></td>
            <td><%= meal.getDescription()%></td>
            <td><%= meal.getCalories()%></td>
            <td><a href="editMeal?id=<%=meals.indexOf(meal)%>">Update</a></td>
            <td><a href="?id=<%= meals.indexOf(meal)%>">Delete</a></td>
        </tr>
    <% } %>
</table>
</body>
</html>