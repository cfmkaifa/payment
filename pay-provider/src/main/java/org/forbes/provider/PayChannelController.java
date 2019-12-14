package org.forbes.provider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.forbes.biz.IPayChannelService;
import org.forbes.comm.constant.CommonConstant;
import org.forbes.comm.constant.DataColumnConstant;
import org.forbes.comm.constant.SaveValid;
import org.forbes.comm.constant.UpdateValid;
import org.forbes.comm.enumm.BizResultEnum;
import org.forbes.comm.enumm.ChannelNameEnum;
import org.forbes.comm.enumm.MchStateEnum;
import org.forbes.comm.enumm.PayChannelEnum;
import org.forbes.comm.model.BasePageDto;
import org.forbes.comm.util.ConvertUtils;
import org.forbes.comm.vo.Result;
import org.forbes.dal.entity.PayChannel;
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

@RequestMapping("/pay-channel")
@Api(tags={"渠道管理"})
@Slf4j
@RestController
public class PayChannelController {

    @Autowired
    IPayChannelService payChannelService;


    /***
     * selectPage方法慨述:分页查询渠道信息
     * @param pageDto
     * @return Result<IPage<PayChannel>>
     * @创建人 huanghy
     * @创建时间 2019年12月10日 下午1:48:45
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation("分页查询渠道信息")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.PERMISSIONS_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.PERMISSIONS_MSG)
    })
    public Result<IPage<PayChannel>> selectPage(BasePageDto pageDto,PayChannel payChannel){
        log.debug("传入参数为："+JSON.toJSONString(pageDto));
        Result<IPage<PayChannel>> result = new Result<>();
        QueryWrapper<PayChannel> qw = new QueryWrapper<PayChannel>();
        if (ConvertUtils.isNotEmpty(payChannel)) {
            if(ConvertUtils.isNotEmpty(payChannel.getMchId()) ){
                qw.eq(DataColumnConstant.MCH_ID,payChannel.getMchId());
            }
            if(ConvertUtils.isNotEmpty(payChannel.getChannelId())){
                qw.like(DataColumnConstant.CHANNEL_ID,payChannel.getChannelId());
            }
        }
        IPage<PayChannel> page = new Page<PayChannel>(pageDto.getPageNo(),pageDto.getPageSize());
        IPage<PayChannel> pPayChannels = payChannelService.page(page,qw);
        result.setResult(pPayChannels);
        return result;
    }


   /***
    * checkCode方法慨述:检验渠道ID是否唯一
    * @param mchId
    * @return Result<Boolean>
    * @创建人 huanghy
    * @创建时间 2019年12月13日 上午10:55:59
    * @修改人 (修改了该文件，请填上修改人的名字)
    * @修改日期 (请填上修改该文件时的日期)
    */
    @ApiOperation("检验渠道ID是否唯一")
    @ApiImplicitParams(value={
    		@ApiImplicitParam(name = "mchId",value = "商户ID",required = true),
    		@ApiImplicitParam(name = "channelId",value = "渠道信息ID",required = true)
    })
    
    @RequestMapping(value = "/check-code", method = RequestMethod.GET)
    public Result<Boolean> checkCode(@RequestParam(value = "mchId",required=true) String mchId,
    		@RequestParam(value = "channelId",required=true) String channelId) {
        Result<Boolean> result = new Result<Boolean>();
        int exitsCount = payChannelService.count(new QueryWrapper<PayChannel>()
        		.eq(DataColumnConstant.CHANNEL_ID,channelId)
        		.eq(DataColumnConstant.MCH_ID, mchId));
        if (exitsCount > 0){//如果编码存在，则返回false
            result.setBizCode(BizResultEnum.CHANNEL_ID_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_EXISTS.getBizFormateMessage(), mchId,channelId));
            result.setResult(false);
            return result;
        }
        return result;
    }

    /***
     * recePayChannels方法慨述:获取支付渠道
     * @return Result<List<Map<String,String>>>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 下午12:00:28
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/pay-channels", method = RequestMethod.GET)
    @ApiOperation("获取支付渠道")
    public Result<List<Map<String,String>>> recePayChannels(){
        Result<List<Map<String,String>>> result = new Result<List<Map<String,String>>>();
        result.setResult(PayChannelEnum.receEnums());
        return result;
    }
    
    
    /***
     * receChannelNames方法慨述:获取支付渠道名称
     * @return Result<List<Map<String,String>>>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 下午12:01:29
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/channel-names", method = RequestMethod.GET)
    @ApiOperation("获取支付渠道名称")
    public Result<List<Map<String,String>>> receChannelNames(){
        Result<List<Map<String,String>>> result = new Result<List<Map<String,String>>>();
        result.setResult(ChannelNameEnum.receEnums());
        return result;
    }


    /***
     * addPayChannel方法慨述:增加支付渠道
     * @param PayChannel
     * @return Result<PayChannel>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:17:52
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation("增加支付渠道")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.ADD_PERMISSION_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.ADD_PERMISSION_MSG)
    })
    public Result<PayChannel> addPayChannel(@RequestBody @Validated(value = SaveValid.class)PayChannel payChannel){
        log.debug("传入PayChannel为:"+JSON.toJSONString(payChannel));
        Result<PayChannel> result = new Result<PayChannel>();
        String mchId = payChannel.getMchId();
        String channelId = payChannel.getChannelId();
        if(!PayChannelEnum.existsCode(channelId)){
        	result.setBizCode(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizFormateMessage(),channelId));
            return result;
        }
        String  channelName = payChannel.getChannelName();
        if(!ChannelNameEnum.existsCode(channelName)){
        	result.setBizCode(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizFormateMessage(),channelName));
            return result;
        }
        if(!PayChannelEnum.existsGroupCode(channelId, channelName)){
        	result.setBizCode(BizResultEnum.CHANNEL_ID_GROUP_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_GROUP_NO_EXISTS.getBizFormateMessage(),channelName,channelId));
            return result;
        }
        int exitsCount = payChannelService.count(new QueryWrapper<PayChannel>()
        		.eq(DataColumnConstant.MCH_ID,mchId)
        		.eq(DataColumnConstant.CHANNEL_ID,channelId));
        if (exitsCount > 0){//如果编码存在，则返回false
        	result.setBizCode(BizResultEnum.CHANNEL_ID_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_EXISTS.getBizFormateMessage(), mchId,channelId));
            return result;
        }
        String state = payChannel.getState().toString();
        if(!MchStateEnum.existsCode(state)){
        	result.setBizCode(BizResultEnum.MCH_STATE_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_STATE_NO_EXISTS.getBizFormateMessage(), state));
            return result;
        }
        payChannelService.save(payChannel);
        result.setResult(payChannel);
        return result;
    }


    /***
     * updatePayChannel方法慨述:修改支付渠道
     * @param PayChannel
     * @return Result<PayChannel>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:17:38
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @ApiOperation("修改支付渠道")
    @ApiResponses(value={
            @ApiResponse(code=500,message= Result.UPDATE_PERMISSION_NOT_ERROR_MSG),
            @ApiResponse(code=200,message = Result.UPDATE_PERMISSION_MSG)
    })
    public Result<PayChannel> updatePayChannel(@RequestBody @Validated(value = UpdateValid.class)PayChannel payChannel){
        Result<PayChannel> result = new Result<>();
        PayChannel oldPayChannel = payChannelService.getById(payChannel.getId());
        if(ConvertUtils.isEmpty(oldPayChannel)){
        	 result.setBizCode(BizResultEnum.ENTITY_EMPTY.getBizCode());
	   		 result.setMessage(BizResultEnum.ENTITY_EMPTY.getBizMessage());
	   		 return result;
        }
        String channelId = payChannel.getChannelId();
        if (!oldPayChannel.getChannelId()
        		.equalsIgnoreCase(payChannel.getChannelId())) {
        	String mchId = payChannel.getMchId();
        	 int exitsCount = payChannelService.count(new QueryWrapper<PayChannel>()
             		.eq(DataColumnConstant.MCH_ID,mchId)
             		.eq(DataColumnConstant.CHANNEL_ID,channelId));
             if (exitsCount > 0){//如果编码存在，则返回false
             	result.setBizCode(BizResultEnum.CHANNEL_ID_EXISTS.getBizCode());
                 result.setMessage(String.format(BizResultEnum.CHANNEL_ID_EXISTS.getBizFormateMessage(), mchId,channelId));
                 return result;
             }
        }
        if(!PayChannelEnum.existsCode(channelId)){
        	result.setBizCode(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizFormateMessage(),channelId));
            return result;
        }
        String  channelName = payChannel.getChannelName();
        if(!ChannelNameEnum.existsCode(channelName)){
        	result.setBizCode(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_NO_EXISTS.getBizFormateMessage(),channelName));
            return result;
        }
        if(!PayChannelEnum.existsGroupCode(channelId, channelName)){
        	result.setBizCode(BizResultEnum.CHANNEL_ID_GROUP_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.CHANNEL_ID_GROUP_NO_EXISTS.getBizFormateMessage(),channelName,channelId));
            return result;
        }
        String state = payChannel.getState().toString();
        if(!MchStateEnum.existsCode(state)){
        	result.setBizCode(BizResultEnum.MCH_STATE_NO_EXISTS.getBizCode());
            result.setMessage(String.format(BizResultEnum.MCH_STATE_NO_EXISTS.getBizFormateMessage(), state));
            return result;
        }
        payChannelService.updateById(payChannel);
        result.setResult(payChannel);
        return result;
    }
    
    
    /***
     * delete方法慨述:删除支付渠道
     * @param id
     * @return Result<PayChannel>
     * @创建人 huanghy
     * @创建时间 2019年12月13日 上午11:19:14
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @ApiOperation("删除支付渠道")
	@ApiImplicitParams(value = {
		@ApiImplicitParam(name = "id",value = "用户ID",required = true)
	})
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<PayChannel> delete(@RequestParam(name="id",required=true) String id) {
		Result<PayChannel> result = new Result<PayChannel>();
		PayChannel payChannel = payChannelService.getById(id);
		if(ConvertUtils.isEmpty(payChannel)){
   		 	 result.setBizCode(BizResultEnum.ENTITY_EMPTY.getBizCode());
	   		 result.setMessage(BizResultEnum.ENTITY_EMPTY.getBizMessage());
	   		 return result;
		}
		payChannelService.removeById(id);
		return result;
	}
	
	/***
	 * deleteBatch方法慨述:批量删除支付渠道
	 * @param ids
	 * @return Result<Boolean>
	 * @创建人 huanghy
	 * @创建时间 2019年12月13日 上午11:19:38
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	@ApiOperation("批量删除支付渠道")
	@ApiImplicitParams(value = {
		@ApiImplicitParam(name = "ids",value = "用户IDs",required = true)
	})
	@RequestMapping(value = "/delete-batch", method = RequestMethod.DELETE)
	public Result<Boolean> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Boolean> result = new Result<Boolean>();
		payChannelService.removeByIds(Arrays.asList(ids.split(CommonConstant.SEPARATOR)));
		return result;
	}
}
