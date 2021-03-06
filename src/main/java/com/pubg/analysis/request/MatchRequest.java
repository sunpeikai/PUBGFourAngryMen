package com.pubg.analysis.request;

import com.pubg.analysis.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sunpeikai
 * @version MongoTestRequest, v0.1 2020/7/10 10:52
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("mongo测试请求参数")
public class MatchRequest extends BaseRequest {

    @ApiModelProperty(value = "账户ID - findMatchPageByAccountId/findMatchPageByAccountIdRemote接口用")
    private String accountId;

    @ApiModelProperty(value = "账户昵称 - findMatchPageByPlayerName/findMatchPageByPlayerNameRemote接口用")
    private String playerName;

    @ApiModelProperty(value = "对局ID - findMatchDetailByMatchId接口用")
    private String matchId;
}
