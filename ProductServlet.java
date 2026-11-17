package com.shop.servlet;

import com.shop.data.ProductRepository;
import com.shop.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String category = req.getParameter("category");
        boolean filtered = category != null && !category.trim().isEmpty();
        List<Product> products = filtered
                ? ProductRepository.findByCategory(category.trim())
                : ProductRepository.findAll();

        String base = req.getContextPath() + "/shop";
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<title>E-Shop Products</title>");
        out.println("<style>");
        out.println("body{font-family:Arial,sans-serif;margin:0;background:#f4f6f8;color:#222}");
        out.println("header{background:#1f3a5f;color:#fff;padding:18px 32px}");
        out.println(".wrap{max-width:900px;margin:24px auto;padding:0 16px}");
        out.println("nav a{display:inline-block;margin:0 8px 16px 0;padding:6px 14px;background:#fff;");
        out.println("border:1px solid #1f3a5f;border-radius:16px;color:#1f3a5f;text-decoration:none}");
        out.println("table{width:100%;border-collapse:collapse;background:#fff}");
        out.println("th,td{padding:10px 12px;border-bottom:1px solid #e3e7eb;text-align:left}");
        out.println("th{background:#e9eef4}");
        out.println("</style></head><body>");
        out.println("<header><h1>E-Shop</h1></header>");
        out.println("<div class=\"wrap\"><nav>");
        out.println("<a href=\"" + base + "\">All</a>");
        for (String c : ProductRepository.categories()) {
            out.println("<a href=\"" + base + "?category=" + esc(c) + "\">" + esc(c) + "</a>");
        }
        out.println("</nav>");
        out.println("<p>Showing " + products.size() + " product(s)"
                + (filtered ? " in <b>" + esc(category.trim()) + "</b>" : "") + "</p>");
        out.println("<table><tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th><th>Description</th></tr>");
        for (Product p : products) {
            out.println("<tr><td>" + p.getId() + "</td><td>" + esc(p.getName()) + "</td><td>"
                    + esc(p.getCategory()) + "</td><td>&#8377;" + String.format("%.2f", p.getPrice())
                    + "</td><td>" + esc(p.getDescription()) + "</td></tr>");
        }
        out.println("</table></div></body></html>");
    }

    private static String esc(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
