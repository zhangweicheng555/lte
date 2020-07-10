package com.boot.kaizen.business.buss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.kaizen.business.buss.dao.SimProjectMapper;
import com.boot.kaizen.business.buss.model.SimProject;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.util.ExcelUtil;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年10月21日 上午10:32:39
 */
@Service
public class SimProjectServiceImpl extends ServiceImpl<SimProjectMapper, SimProject> implements ISimProjectService {

	
	@Autowired
	private SysProjectService sysProjectService;
	
	@Override
	public JsonMsgUtil upload(MultipartFile file, LoginUser loginUser) {

		JsonMsgUtil msg = new JsonMsgUtil(false);
		try {
			// 导入
			HSSFWorkbook wbs = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet1 = wbs.getSheetAt(0);
			HSSFRow row = null;
			if (sheet1.getRow(0).getLastCellNum() != 6) {
				throw new IllegalArgumentException("导入excel的列数要求为6列");
			}
			List<SimProject> list = new ArrayList<SimProject>();
			SimProject autoCheckShuGc = null;
			for (int j = 1; j <= sheet1.getLastRowNum(); j++) {
				row = sheet1.getRow(j);
				if (row == null) {
					continue;
				}
				// 导入excel总行数记录
				int cellIndex = 0;
				autoCheckShuGc = new SimProject();
				// 项目名称
				String project_name = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNotBlank(project_name)) {
					autoCheckShuGc.setProject_name(project_name);
				} else {
					throw new IllegalArgumentException("项目名称不能为空");
				}
				
				// 网络ID
				String net_id = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNotBlank(net_id)) {
					autoCheckShuGc.validNetId(net_id);
					autoCheckShuGc.setNet_id(net_id);
				} else {
					throw new IllegalArgumentException("网络ID不能为空");
				}
				
				//省份
				String province_name = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNotBlank(province_name)) {
					autoCheckShuGc.setProvince_name(province_name);
				} else {
					throw new IllegalArgumentException("省不能为空");
				}
				//城市
				String city_name = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNotBlank(city_name)) {
					autoCheckShuGc.setCity_name(city_name);
				} else {
					throw new IllegalArgumentException("城市不能为空");
				}
				//运营商
				String operators = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNotBlank(operators)) {
					autoCheckShuGc.setOperators(operators);
				} else {
					throw new IllegalArgumentException("运营商不能为空");
				}
				
				//SIM工参地址
				String ihandle_url = ExcelUtil.cell_string(row.getCell(cellIndex++));
				if (StringUtils.isNotBlank(ihandle_url)) {
					autoCheckShuGc.setIhandle_url(ihandle_url);
				} else {
					throw new IllegalArgumentException("SIM工参URL不能为空");
				}
				
				//设置主键
				autoCheckShuGc.setId(UUID.randomUUID().toString().replace("-", ""));
				// 添加进list里面
				list.add(autoCheckShuGc);
			}

			for (SimProject model : list) {
				//添加
				insertOrUpdate(model);
				//判断log项目是不是存在了 不存在添加
				//判断lOG项目是否已经生成了项目  根据sim的项目名字直接判断即可
				String project_name = model.getProject_name();
				Map<String, Object> paramMap=new HashMap<>();
				paramMap.put("projName", project_name);
				List<SysProject> sysProjects = sysProjectService.findByCondition(paramMap);
				if (sysProjects==null || sysProjects.size()==0) {//添加项目
					SysProject sysProject=new SysProject();
					sysProject.setProjIntro("SIM项目自动生成");
					sysProject.setProjName(project_name);
					sysProject.setProjSimName(project_name);
					sysProject.setProjCode(model.getCity_name());
					sysProject.setProProvice(model.getProvince_name());
					sysProject.setHostAp(model.getIhandle_url());
					sysProject.setProjOperator(model.getOperators());
					sysProject.setSort(UUID.randomUUID().toString().replace("-", ""));
					sysProject.setCreateTime(new Date());
					sysProject.setUpdateTime(new Date());
					sysProjectService.insertSelf(sysProject);
				}else {//修改
					SysProject sysProject=sysProjects.get(0);
					sysProject.setProjIntro("SIM项目自动生成");
					sysProject.setProjName(project_name);
					sysProject.setProjSimName(project_name);
					sysProject.setProjCode(model.getCity_name());
					sysProject.setProProvice(model.getProvince_name());
					sysProject.setHostAp(model.getIhandle_url());
					sysProject.setProjOperator(model.getOperators());
					sysProject.setSort(UUID.randomUUID().toString().replace("-", ""));
					sysProject.setUpdateTime(new Date());
					sysProjectService.update(sysProject);
				}
			}
			msg = new JsonMsgUtil(true, "上传成功", null);
		} catch (Exception e) {
			msg = new JsonMsgUtil(false, e.getMessage(), null);
		}
		return msg;
	}


}
