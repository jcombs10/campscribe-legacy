<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CampScribe Advancement Tracker</title>
</head>
<body>
  <h2>Class List</h2>
  <p>
    <a href="/">Main</a>
  </p>
  <p>
  <table>
    <c:forEach var="clazz" items="${clazzes}">
      <tr>
        <td><c:out value="${clazz.description}"/></td>
        <td><c:out value="${clazz.mb.badgeName}"/></td>
        <td style="text-align: center;"><a href="deleteClazz.cs?id=${clazz.id}"><img src="images/16x16/delete.png" alt="Delete"></a></td>
      </tr>
    </c:forEach>
  </table>
  </p>
  <p>
    <form action="addClazz.cs" method="post">
        <table>
            <tr>
                <td>Description:</td>
                <td><input type="text" name="description" /></td>
            </tr>
            <tr>
                <td>Event</td>
                <td>
                  <select name="event">
                    <c:forEach var="ev" items="${events}">
                      <option value="${ev.id}"><c:out value="${ev.description}"/></option>
                    </c:forEach>
                  </select>
                </td>
            </tr>
            <tr>
                <td>Merit Badge</td>
                <td>
                  <select name="meritBadge">
                    <c:forEach var="mb" items="${meritBadges}">
                      <option value="${mb.id}"><c:out value="${mb.badgeName}"/></option>
                    </c:forEach>
                  </select>
                </td>
            </tr>
        </table>
        <input type="submit" value="Create" />
    </form>
  </p>
</body>
</html>
