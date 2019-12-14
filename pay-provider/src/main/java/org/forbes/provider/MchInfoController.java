package org.forbes.provider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.forbes.biz.IMchInfoService;
import org.forbes.comm.constant.CommonConstant;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.constant.SaveValid;
import org.forbes.comm.constant.UpdateValid;
import org.forbes.comm.enumm.BizResultEnum;
import org.forbes.comm.enumm.MchStateEnum;
import org.forbes.comm.enumm.MchTypeEnum;
import org.forbes.comm.model.BasePageDto;
import org.forbes.comm.util.ConvertUtils;
import org.forbes.comm.vo.Result;
import org.forbes.dal.entity.MchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/mch-info")
@Api(tags={"商家管理"})
@Slf4j
@RestController
public class MchInfoController {

    @Autowired
    IMchInfoService mchInfoService;
    
    
    /***
     * selectALL方法慨述:查询所有商家信息
     * @return Result<List<MchInfo>>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:24:11
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/lit", method = RequestMethod.GET)
    @ApiOperation("查询所有商家信息")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.PERMISSIONS_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.PERMISSIONS_MSG)
    })
    public Result<List<MchInfo>> selectALL(){
        Result<List<MchInfo>> result = new Result<List<MchInfo>>();
        List<MchInfo> mchInfos = mchInfoService.list();
        result.setResult(mchInfos);
        return result;
    }


    /***
     * selectPage方法慨述:分页查询商家
     * @param pageDto
     * @return Result<IPage<MchInfo>>
     * @创建人 huanghy
     * @创建时间 2019年12月10日 下午1:48:45
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation("分页查询商家")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.PERMISSIONS_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.PERMISSIONS_MSG)
    })
    public Result<IPage<MchInfo>> selectPage(BasePageDto pageDto,MchInfo mchInfo){
        log.debug("传入参数为："+JSON.toJSONString(pageDto));
        Result<IPage<MchInfo>> result = new Result<>();
        QueryWrapper<MchInfo> qw = new QueryWrapper<MchInfo>();
        if (ConvertUtils.isNotEmpty(mchInfo)) {
            if(ConvertUtils.isNotEmpty(mchInfo.getMchId()) ){
                qw.eq(DataColumnConstant.MCH_ID,mchInfo.getMchId());
            }
            if(ConvertUtils.isNotEmpty(mchInfo.getName())){
                qw.like(DataColumnConstant.NAME,mchInfo.getName());
            }
            if(ConvertUtils.isNotEmpty(mchInfo.getType())){
                qw.eq(DataColumnConstant.TYPE,mchInfo.getType());
            }
            if(ConvertUtils.isNotEmpty(mchInfo.getState())){
                qw.eq(DataColumnConstant.STATE,mchInfo.getState());
            }
        }
        IPage<MchInfo> page = new Page<MchInfo>(pageDto.getPageNo(),pageDto.getPageSize());
        IPage<MchInfo> mchInfos = mchInfoService.page(page,qw);
        result.setResult(mchInfos);
        return result;
    }


   /***
    * checkCode方法慨述:检验商ID是否唯一
    * @param mchId
    * @return Result<Boolean>
    * @创建人 huanghy
    * @创建时间 2019年12月13日 上午10:55:59
    * @修改人 (修改了该文件，请填上修改人的名字)
    * @修改日期 (请填上修改该文件时的日期)
    */
    @ApiOperation("检验商ID是否唯一")
    @ApiImplicitParam(name = "mchId",value = "商家ID",required = true)
    @RequestMapping(value = "/check-code", method = RequestMethod.GET)
    public Result<Boolean> checkCode(@RequestParam(value = "mchId",required=true) String mchId) {
        Result<Boolean> result = new Result<Boolean>();
        int exitsCount = mchInfoService.count(new QueryWrapper<MchInfo>()
        		.eq(DataColumnConstant.MCH_ID,mchId));
        if (exitsCount > 0){//如果编码存在，则返回false
            result.setBizCode(BizResultEnum.MCH_ID_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_ID_EXISTS.getBizFormateMessage(), mchId));
            result.setResult(false);
            return result;
        }
        return result;
    }

    /***
     * receMchStates方法慨述:获取商户状态
     * @return Result<List<Map<String,String>>>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:07:08
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/mch-states", method = RequestMethod.GET)
    @ApiOperation("获取商户状态")
    public Result<List<Map<String,String>>> receMchStates(){
        Result<List<Map<String,String>>> result = new Result<List<Map<String,String>>>();
        result.setResult(MchStateEnum.receEnums());
        return result;
    }


    /***
     * receMchTypes方法慨述:获取商户类型
     * @return Result<List<Map<String,String>>>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:08:10
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/mch-types", method = RequestMethod.GET)
    @ApiOperation("获取商户类型")
    public Result<List<Map<String,String>>> receMchTypes(){
        Result<List<Map<String,String>>> result = new Result<List<Map<String,String>>>();
        result.setResult(MchTypeEnum.receEnums());
        return result;
    }


    /***
     * addMchInfo方法慨述:增加商户信息
     * @param mchInfo
     * @return Result<MchInfo>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:17:52
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation("增加商户信息")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.ADD_PERMISSION_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.ADD_PERMISSION_MSG)
    })
    public Result<MchInfo> addMchInfo(@RequestBody @Validated(value = SaveValid.class)MchInfo mchInfo){
        log.debug("传入MchInfo为:"+JSON.toJSONString(mchInfo));
        Result<MchInfo> result = new Result<MchInfo>();
        String mchId = mchInfo.getMchId();
        int exitsCount = mchInfoService.count(new QueryWrapper<MchInfo>()
        		.eq(DataColumnConstant.MCH_ID,mchId));
        if (exitsCount > 0){//如果编码存在，则返回false
            result.setBizCode(BizResultEnum.MCH_ID_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_ID_EXISTS.getBizFormateMessage(), mchId));
            return result;
        }
        String state = mchInfo.getState().toString();
        if(!MchStateEnum.existsCode(state)){
        	result.setBizCode(BizResultEnum.MCH_STATE_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_STATE_NO_EXISTS.getBizFormateMessage(), state));
            return result;
        }
        String type = mchInfo.getType();
        if(!MchTypeEnum.existsCode(type)){
        	result.setBizCode(BizResultEnum.MCH_TYPE_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_TYPE_NO_EXISTS.getBizFormateMessage(), type));
            return result;
        }
        mchInfoService.save(mchInfo);
        result.setResult(mchInfo);
        return result;
    }


    /***
     * updateMchInfo方法慨述:修改商户信息
     * @param mchInfo
     * @return Result<MchInfo>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:17:38
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @ApiOperation("修改商户信息")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.UPDATE_PERMISSION_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.UPDATE_PERMISSION_MSG)
    })
    public Result<MchInfo> updateMchInfo(@RequestBody @Validated(value = UpdateValid.class)MchInfo mchInfo){
        Result<MchInfo> result = new Result<>();
        MchInfo oldMchInfo = mchInfoService.getById(mchInfo.getId());
        if(ConvertUtils.isEmpty(oldMchInfo)){
        	 result.setBizCode(BizResultEnum.ENTITY_EMPTY.getBizCode());
	   		 result.setMessage(BizResultEnum.ENTITY_EMPTY.getBizMessage());
	   		 return result;
        }
        //判断当前权限编码与输入的是否一致
        if (!oldMchInfo.getMchId().equalsIgnoreCase(mchInfo.getMchId())) {
        	String mchId = mchInfo.getMchId();
            //查询是否和其他权限编码一致
            int exitsCount = mchInfoService.count(new QueryWrapper<MchInfo>()
            		.eq(DataColumnConstant.MCH_ID,mchId));
            if (exitsCount > 0){//如果编码存在，则返回false
                result.setBizCode(BizResultEnum.MCH_ID_EXISTS.getBizCode());
                result.setMessage(String.format(BizResultEnum.MCH_ID_EXISTS.getBizFormateMessage(), mchId));
                return result;
            }
        }
        String state = mchInfo.getState().toString();
        if(!MchStateEnum.existsCode(state)){
        	result.setBizCode(BizResultEnum.MCH_STATE_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_STATE_NO_EXISTS.getBizFormateMessage(), state));
            return result;
        }
        String type = mchInfo.getType();
        if(!MchTypeEnum.existsCode(type)){
        	result.setBizCode(BizResultEnum.MCH_TYPE_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_TYPE_NO_EXISTS.getBizFormateMessage(), type));
            return result;
        }
        mchInfoService.updateById(mchInfo);
        result.setResult(mchInfo);
        return result;
    }
    
    
    /***
     * delete方法慨述:删除商户信息
     * @param id
     * @return Result<MchInfo>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:19:14
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @ApiOperation("删除商户信息")
	@ApiImplicitParams(value = {
		@ApiImplicitParam(name = "id",value = "用户ID",required = true)
	})
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<MchInfo> delete(@RequestParam(name="id",required=true) String id) {
		Result<MchInfo> result = new Result<MchInfo>();
		MchInfo mchInfo = mchInfoService.getById(id);
		if(ConvertUtils.isEmpty(mchInfo)){
   		 	 result.setBizCode(BizResultEnum.ENTITY_EMPTY.getBizCode());
	   		 result.setMessage(BizResultEnum.ENTITY_EMPTY.getBizMessage());
	   		 return result;
		}
		mchInfoService.removeById(id);
		return result;
	}
	
	/***
	 * deleteBatch方法慨述:批量删除商户信息
	 * @param ids
	 * @return Result<Boolean>
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午11:19:38
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	@ApiOperation("批量删除商户信息")
	@ApiImplicitParams(value = {
		@ApiImplicitParam(name = "ids",value = "用户IDs",required = true)
	})
	@RequestMapping(value = "/delete-batch", method = RequestMethod.DELETE)
	public Result<Boolean> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Boolean> result = new Result<Boolean>();
		mchInfoService.removeByIds(Arrays.asList(ids.split(CommonConstant.SEPARATOR)));
		return result;
	}
}
