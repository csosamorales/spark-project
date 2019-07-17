<%--
  Created by IntelliJ IDEA.
  User: casosa
  Date: 2019-07-15
  Time: 17:32
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<table>
    <tr>
        <th>Nro item</th>
        <th>Titulo</th>
        <th>Subtitulo</th>
        <th>Precio</th>
        <th>Categoria</th>
        <th>Imagen</th>
        <th></th>
    </tr>

    <g:each in="${listaItems}" var="p">
        <tr>
            <td>${p.id}</td>
            <td>${p.title}</td>
            <td>${p.subtitle}</td>
            <td>${p.price}</td>
            <td>${p.categoryId.name}</td>
            <td><img src="${p.imageUrl}"></td>
            <td><button >Ver</button></td>
        </tr>

    </g:each>


</table>
</body>
</html>