package com.boot.kaizen.controller.lte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.model.lte.LteConfig;
import com.boot.kaizen.service.lte.ILteConfigService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.boot.kaizen.util.UserUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author weichengz
 * @date 2018年10月28日 上午3:13:32
 */
@RestController
@RequestMapping("/config")
public class LteConfigController {

	@Autowired
	private ILteConfigService lteConfigService;

	@ResponseBody
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public TableResultUtil find(RequestParamEntity param) {

		LoginUser loginUser = UserUtil.getLoginUser();
		param.getMap().put("projId", loginUser.getProjId());

		PageInfo<LteConfig> pageInfo = PageHelper.startPage(param.getPage(), param.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						lteConfigService.find(param.getMap());
					}
				});
		return new TableResultUtil(0L, "操作成功", pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 
	 * @Description: 编辑
	 * @author weichengz
	 * @date 2018年10月28日 下午4:36:46
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(LteConfig lteConfig) {
		LoginUser loginUser = UserUtil.getLoginUser();
		return lteConfigService.edit(lteConfig, loginUser);
	}

	/**
	 * 
	 * @Description: 根据id查询
	 * @author weichengz
	 * @date 2018年10月28日 下午5:00:01
	 */
	@ResponseBody
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	public JsonMsgUtil findById(@RequestParam("id") Long id) {
		return lteConfigService.findById(id);
	}

	/**
	 * 
	 * @Description: 删除
	 * @author weichengz
	 * @date 2018年10月28日 下午5:52:35
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonMsgUtil delete(@RequestParam("ids") String ids) {
		return lteConfigService.delete(ids);
	}
}
