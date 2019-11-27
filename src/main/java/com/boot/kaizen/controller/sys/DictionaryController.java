package com.boot.kaizen.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.kaizen.model.SysDic;
import com.boot.kaizen.service.SysDictionaryService;

/**
 * 字典管理
 * 
 * @author weichengz
 * @date 2018年10月3日 上午8:48:42
 */
@RestController
@RequestMapping("/dic")
public class DictionaryController {

	@Autowired
	private SysDictionaryService dictionaryService;

	@RequestMapping(value = "/findByType")
	public List<SysDic> findByType(@RequestParam(value = "type", required = true) String type) {
		return dictionaryService.findByType(type);
	}


}
