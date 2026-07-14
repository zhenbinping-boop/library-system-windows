package com.library.controller;

import com.library.entity.BorrowBook;
import com.library.common.Result;
import com.library.entity.Users;
import com.library.service.BorrowBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin(origins = "*")
public class BorrowBookController {

    @Autowired
    private BorrowBookService borrowBookService;

    /**
     * 罚金查询页面（HTML）
     * 统一使用 Service 层的逾期计算逻辑
     */
    @GetMapping("/fineInfo")
    public void fineInfo(@RequestParam String rid, @RequestParam String bisbn,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<BorrowBook> list = borrowBookService.selectByReaderId(rid);
        BorrowBook target = null;
        for (BorrowBook bb : list) {
            if (bb.getBisbn().equals(bisbn)) {
                target = bb;
                break;
            }
        }
        PrintWriter out = response.getWriter();
        if (target == null) {
            out.write("<html><body style='text-align:center;padding:40px;font-family:sans-serif;'>"
                    + "<h2>未找到借阅记录</h2></body></html>");
            return;
        }

        // 使用 Service 层计算好的逾期信息
        String status = target.getOverdueStatus();
        BigDecimal fine = target.getOverdueFine() != null ? target.getOverdueFine() : BigDecimal.ZERO;
        long overdueDays = target.getOverdueDays() != null ? target.getOverdueDays() : 0;

        String statusHtml = status != null && status.contains("逾期")
                ? "<span style='color:#e6a23c'>" + status + "</span>"
                : "<span style='color:#67c23a'>" + status + "</span>";

        out.write("<html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1'>"
                + "<style>body{font-family:sans-serif;max-width:400px;margin:40px auto;padding:20px;background:#f5f7fa}"
                + ".card{background:#fff;border-radius:12px;padding:24px;box-shadow:0 2px 12px rgba(0,0,0,.08)}"
                + "h2{color:#409EFF;text-align:center}p{margin:12px 0;color:#606266}"
                + ".fine{font-size:28px;color:#f56c6c;text-align:center;margin:20px 0;font-weight:bold}</style></head><body>"
                + "<div class='card'><h2>📚 借阅罚金查询</h2>"
                + "<p><b>书名：</b>" + (target.getBname() != null ? target.getBname() : "未知") + "</p>"
                + "<p><b>作者：</b>" + (target.getAuthor() != null ? target.getAuthor() : "未知") + "</p>"
                + "<p><b>读者：</b>" + (target.getRname() != null ? target.getRname() : rid) + "</p>"
                + "<p><b>借阅日期：</b>" + target.getBdate() + "</p>"
                + "<p><b>状态：</b>" + statusHtml + "</p>"
                + "<div class='fine'>应付：" + fine + " 元</div>"
                + "</div></body></html>");
    }

    @GetMapping("/list")
    public Result<List<BorrowBook>> list(HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null) return Result.error(401, "未登录");
        if (u.getUtype() == 2) {
            return Result.success(borrowBookService.selectByReaderId(u.getUname()));
        }
        return Result.success(borrowBookService.selectAllActive());
    }

    @GetMapping("/all")
    public Result<List<BorrowBook>> all(HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null || u.getUtype() == 2) return Result.error(403, "无权限");
        return Result.success(borrowBookService.selectAll());
    }

    @GetMapping("/my")
    public Result<List<BorrowBook>> my(HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null) return Result.error(401, "未登录");
        return Result.success(borrowBookService.selectByReaderId(u.getUname()));
    }

    @PostMapping("/borrow")
    public Result<String> borrow(@RequestParam String rid, @RequestParam String bisbn, HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null) return Result.error(401, "未登录");
        if (u.getUtype() == 2) {
            rid = u.getUname();
        }
        String msg = borrowBookService.borrowBook(rid, bisbn);
        if (msg.equals("借阅成功")) return Result.success(msg);
        return Result.error(msg);
    }

    @PostMapping("/return")
    public Result<String> returnBook(@RequestParam String rid, @RequestParam String bisbn,
                                     @RequestParam(defaultValue = "1.0") BigDecimal finePerDay,
                                     HttpSession session) {
        Users u = (Users) session.getAttribute("user");
        if (u == null) return Result.error(401, "未登录");
        if (u.getUtype() == 2) {
            rid = u.getUname();
        }
        String msg = borrowBookService.returnBook(rid, bisbn, finePerDay);
        if (msg.contains("成功")) return Result.success(msg);
        return Result.error(msg);
    }
}