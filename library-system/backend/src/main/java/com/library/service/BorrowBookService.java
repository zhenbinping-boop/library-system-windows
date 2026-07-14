package com.library.service;

import com.library.entity.BorrowBook;
import com.library.entity.Reader;
import com.library.entity.Book;
import com.library.mapper.BorrowBookMapper;
import com.library.mapper.ReaderMapper;
import com.library.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 借阅Service
 * 包含借阅和归还的核心业务逻辑
 */
@Service
public class BorrowBookService {

    @Autowired
    private BorrowBookMapper borrowBookMapper;
    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private BookMapper bookMapper;

    // 默认罚金单价（元/天）
    private static final BigDecimal DEFAULT_FINE_PER_DAY = new BigDecimal("1.0");
    // 默认借阅期限（天）
    private static final int DEFAULT_LIMIT_DAYS = 30;

    public List<BorrowBook> selectAllActive() {
        List<BorrowBook> list = borrowBookMapper.selectAllActive();
        fillOverdueInfo(list);
        return list;
    }

    public List<BorrowBook> selectAll() {
        List<BorrowBook> list = borrowBookMapper.selectAll();
        fillOverdueInfo(list);
        return list;
    }

    public List<BorrowBook> selectByReaderId(String rid) {
        List<BorrowBook> list = borrowBookMapper.selectByReaderId(rid);
        fillOverdueInfo(list);
        return list;
    }

    /**
     * 填充逾期信息到借阅记录（仅未归还记录）
     */
    private void fillOverdueInfo(List<BorrowBook> list) {
        if (list == null) return;
        Date now = new Date(System.currentTimeMillis());
        for (BorrowBook bb : list) {
            // 已归还的不计算逾期
            if (bb.getIsReturned() != null && bb.getIsReturned() == 1) {
                bb.setOverdueStatus("已归还");
                bb.setOverdueDays(0L);
                bb.setOverdueFine(BigDecimal.ZERO);
                continue;
            }
            // 借阅日期为空的不计算
            if (bb.getBdate() == null) {
                bb.setOverdueStatus("未知");
                continue;
            }
            // 获取该读者的借阅期限
            Reader reader = readerMapper.selectById(bb.getRid());
            int limitDays = (reader != null && reader.getLimitdays() != null)
                    ? reader.getLimitdays() : DEFAULT_LIMIT_DAYS;

            long diffMillis = now.getTime() - bb.getBdate().getTime();
            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

            if (diffDays > limitDays) {
                long overdueDays = diffDays - limitDays;
                BigDecimal fine = DEFAULT_FINE_PER_DAY.multiply(new BigDecimal(overdueDays));
                bb.setOverdueDays(overdueDays);
                bb.setOverdueFine(fine);
                bb.setOverdueStatus("已逾期 " + overdueDays + " 天");
            } else {
                bb.setOverdueDays(0L);
                bb.setOverdueFine(BigDecimal.ZERO);
                bb.setOverdueStatus("未逾期（还剩 " + (limitDays - diffDays) + " 天）");
            }
        }
    }

    /**
     * 计算单条记录的逾期信息（供 fineInfo 接口使用，逻辑与 fillOverdueInfo 一致）
     */
    public void calcOverdueInfo(BorrowBook bb) {
        if (bb == null || bb.getBdate() == null) return;
        Reader reader = readerMapper.selectById(bb.getRid());
        int limitDays = (reader != null && reader.getLimitdays() != null)
                ? reader.getLimitdays() : DEFAULT_LIMIT_DAYS;

        Date now = new Date(System.currentTimeMillis());
        long diffMillis = now.getTime() - bb.getBdate().getTime();
        long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

        if (diffDays > limitDays) {
            long overdueDays = diffDays - limitDays;
            BigDecimal fine = DEFAULT_FINE_PER_DAY.multiply(new BigDecimal(overdueDays));
            bb.setOverdueDays(overdueDays);
            bb.setOverdueFine(fine);
            bb.setOverdueStatus("已逾期 " + overdueDays + " 天");
        } else {
            bb.setOverdueDays(0L);
            bb.setOverdueFine(BigDecimal.ZERO);
            bb.setOverdueStatus("未逾期（还剩 " + (limitDays - diffDays) + " 天）");
        }
    }

    /**
     * 借阅图书
     */
    @Transactional
    public String borrowBook(String rid, String bisbn) {
        Reader reader = readerMapper.selectById(rid);
        if (reader == null) return "读者不存在";

        Book book = bookMapper.selectById(bisbn);
        if (book == null) return "图书不存在";
        if (book.getNum() == null || book.getNum() <= 0) return "该图书暂无库存";

        int activeCount = borrowBookMapper.countActiveByReader(rid);
        if (activeCount >= reader.getMaxbnum()) {
            return "借阅数量已达上限(最多" + reader.getMaxbnum() + "本)";
        }

        List<BorrowBook> active = borrowBookMapper.selectByReaderId(rid);
        for (BorrowBook b : active) {
            if (b.getBisbn().equals(bisbn)) {
                return "您已借阅该书，请勿重复借阅";
            }
        }

        if (bookMapper.decreaseNum(bisbn) == 0) {
            return "借阅失败，库存不足";
        }

        BorrowBook bb = new BorrowBook();
        bb.setRid(rid);
        bb.setBisbn(bisbn);
        bb.setBdate(new Date(System.currentTimeMillis()));
        bb.setIsReturned(0);
        borrowBookMapper.insert(bb);

        return "借阅成功";
    }

    /**
     * 归还图书
     */
    @Transactional
    public String returnBook(String rid, String bisbn, BigDecimal finePerDay) {
        List<BorrowBook> list = borrowBookMapper.selectByReaderId(rid);
        BorrowBook target = null;
        for (BorrowBook bb : list) {
            if (bb.getBisbn().equals(bisbn)) {
                target = bb;
                break;
            }
        }
        if (target == null) return "未找到该借阅记录";

        Date now = new Date(System.currentTimeMillis());
        Reader reader = readerMapper.selectById(rid);
        int limitDays = (reader != null && reader.getLimitdays() != null)
                ? reader.getLimitdays() : DEFAULT_LIMIT_DAYS;

        // 如果传入的 finePerDay 为 null 或 <=0，用默认值
        BigDecimal actualFinePerDay = (finePerDay != null && finePerDay.compareTo(BigDecimal.ZERO) > 0)
                ? finePerDay : DEFAULT_FINE_PER_DAY;

        long diffMillis = now.getTime() - target.getBdate().getTime();
        long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

        BigDecimal fine = BigDecimal.ZERO;
        if (diffDays > limitDays) {
            long overdueDays = diffDays - limitDays;
            fine = actualFinePerDay.multiply(new BigDecimal(overdueDays));
        }

        borrowBookMapper.returnBook(rid, bisbn, now, fine);
        bookMapper.increaseNum(bisbn);

        if (fine.compareTo(BigDecimal.ZERO) > 0) {
            return "归还成功，逾期" + (diffDays - limitDays) + "天，罚金：" + fine + "元";
        }
        return "归还成功";
    }
}