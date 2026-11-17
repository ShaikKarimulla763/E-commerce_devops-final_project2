<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.shop.data.ProductRepository, java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>E-Shop Home</title>
  <style>
    body { font-family: Arial, sans-serif; background:#f4f6f8; margin:0; }
    header { background:#1f3a5f; color:#fff; padding:18px 32px; }
    .box { max-width:700px; margin:40px auto; background:#fff; padding:28px; border-radius:8px; }
    a.btn { display:inline-block; background:#1f3a5f; color:#fff; padding:10px 22px;
            border-radius:6px; text-decoration:none; }
  </style>
</head>
<body>
  <header><h1>E-Shop</h1></header>
  <div class="box">
    <h2>Welcome to the E-commerce DevOps Project</h2>
    <p>Products in catalog: <b><%= ProductRepository.findAll().size() %></b></p>
    <p>Server time: <%= new Date() %></p>
    <a class="btn" href="<%= request.getContextPath() %>/shop">Browse Products</a>
  </div>
</body>
</html>
