package com.boot.kaizen.business.template;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.boot.kaizen.util.CollectionUtil;
import com.boot.kaizen.util.CsvUtils;
import cn.hutool.core.util.URLUtil;

/**
 * 报告控制层
 * 
 * @author weichengz
 * @date 2019年12月6日 上午11:37:26
 */
@Controller
@RequestMapping("/app/tempModel")
public class CheckTempController {

	@RequestMapping(value = "/down", method = RequestMethod.GET)
	public void checkDocDown(HttpServletResponse response) throws IOException {
		
		ServletOutputStream csvResult = response.getOutputStream();
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+ URLUtil.encode("test", StringUtil.UTF8) +".csv");
        String[] head = new String[]{"用户姓名","年龄","性别","地址","手机号","业余爱好","出生日期","创建时间"};
        
        //List<Map<String, Object>> list = userService.listMaps(new QueryWrapper<>());
        List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
        	Map<String, Object> modelList=new HashMap<String, Object>();
        	modelList.put("user_name", "zhangsan"+i);
        	modelList.put("age", "11"+i);
        	modelList.put("sex", "1"+i);
        	modelList.put("address", "shandong"+i);
        	modelList.put("phone", "13142111373"+i);
        	modelList.put("hobby", "吃饭"+i);
        	modelList.put("birthday", "1982-02-02"+i);
        	modelList.put("birthday", new Date()+""+i);
        	list.add(modelList);
		}
        
        
        List<Object[]> objects = CollectionUtil.collectToArray(list);
        CsvUtils.simpleExport(true,"\n",head,objects,"test",csvResult);
	}

}
