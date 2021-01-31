package com.xiaoxin.notes.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaoxin.notes.common.Constant;
import com.xiaoxin.notes.component.JwtAuthenticationTokenFilter;
import com.xiaoxin.notes.entity.LogEntity;
import com.xiaoxin.notes.service.LogService;
import com.xiaoxin.notes.utils.PageUtils;
import com.xiaoxin.notes.utils.R;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @date 2021-01-12 20:18:07
 */
@Api(tags = "LogController", description = "日志管理")
@RestController
@RequestMapping("notes/log")
public class LogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);
    @Autowired
    private LogService logService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam int pagenum,@RequestParam int pageSize){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constant.PAGE,pagenum);
        params.put(Constant.LIMIT,pageSize);
        PageUtils page = logService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		LogEntity log = logService.getById(id);
        return R.ok(log);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LogEntity log){
		logService.save(log);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody LogEntity log){
		logService.updateById(log);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		logService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 导出
     */
    @GetMapping("exportLog")
    public R exportLog(HttpServletResponse response){
        LOGGER.info("准备导出文件...");
        // 创建Excel
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建sheet页
        HSSFSheet sheet = wb.createSheet();
        // 创建行
        HSSFRow nRow = sheet.createRow(0);
        // 创建列
        HSSFCell nCell = nRow.createCell(0);
        int rowNo = 0;
        int colNo = 0;
        // 设置excel第一行的标题
        String[] title = new String[]{"标题", "内容", "创建时间","姓名"};
        nRow = sheet.createRow(rowNo++);
        for (int i = 0; i < title.length; i++) {
            nCell = nRow.createCell(i);
            nCell.setCellValue(title[i]);
        }
        try {
            List<LogEntity> list = logService.list();
            //遍历并且创建行列
            for (LogEntity log : list) {
                //控制列号
                colNo = 0;
                //每遍历一次创建一行
                nRow = sheet.createRow(rowNo++);
                nCell = nRow.createCell(colNo++);
                nCell.setCellValue(log.getTitle());//标题
                nCell = nRow.createCell(colNo++);
                nCell.setCellValue(log.getContent());//内容
                nCell = nRow.createCell(colNo++);
                nCell.setCellValue(log.getCreateTime());//创建时间
                nCell = nRow.createCell(colNo++);
                nCell.setCellValue(log.getUsername());//姓名
            }

            // 返回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.reset();
            // 下列输出的xls名字可以自己修改（批量采购表样.xls）这个是例子
            response.setHeader("Content-Disposition","attchement;filename=" + new String("日志.xls".getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("application/msexcel");
            wb.write(outputStream);
            wb.close();

            // 输出到本地
            // FileOutputStream fout = new FileOutputStream("E:/usrer.xls");
            // wb.write(fout);
            // fout.close();

            LOGGER.info("导出Excel成功！");
        } catch (Exception e) {
            LOGGER.error("你异常了~~~快来看看我。。");
        }
        return R.ok();
    }
}
