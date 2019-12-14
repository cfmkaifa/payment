package org.forbes.comm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * BasePageDto概要说明：页码抽象类
 * @author Huanghy
 */
@Data
@ApiModel(description="分页对象")
public class BasePageDto {

	/***当前页码
	 */
	@ApiModelProperty(value="当前页码",example="1")
	private Integer pageNo=1;
	/**每页显示记录数**/
	@ApiModelProperty(value="每页显示记录数",example="10")
	private Integer pageSize=10;
}
